package flight.logic;

import static flight.entities.refdata.Station.BUDAPEST;
import static flight.entities.refdata.Station.VIENNA;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import flight.entities.FlightBundle;
import flight.entities.FlightOption;
import flight.entities.holiday.Holiday;
import flight.entities.holiday.HolidayPlan;
import flight.services.FlightOptionProcessor;
import flight.util.HolidayFormatter;

public class FlightScanner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger("FlightScanner");
    
    private final FlightOptionProcessor flightOptionProcessor;
    private final HolidayCalculator holidayCalculator;
    private final HolidayFormatter holidayFormatter;
       
    public FlightScanner(FlightOptionProcessor flightOptionProcessor, HolidayCalculator holidayCalculator, HolidayFormatter holidayFormatter) {
	this.flightOptionProcessor = flightOptionProcessor;
	this.holidayCalculator = holidayCalculator;
	this.holidayFormatter = holidayFormatter;
    }

    public void scanFlights(LocalDate startDate, LocalDate endDate, int minimumDays, int maximumDays, int holidaysLimit, int allHolidaysLimit,
	    Set<FlightOption> flightOptions){
 	FlightBundle flights = new FlightBundle();
 	
 	try{
 	    flights.addFlights(flightOptionProcessor.getFlights(startDate, endDate, flightOptions));      	    
 	}
 	catch(Exception ex){
 	    LOGGER.error("Error during scanning holidayPlans", ex);
 	}
 	
	List<Holiday> allHolidays = holidayCalculator.getHolidays(flights.getOutboundFlights(), flights.getReturnFlights(), minimumDays, maximumDays);
 	
 	System.out.println();
 	System.out.println("---FINAL LIST---");
 	holidayFormatter.format(allHolidays, allHolidaysLimit, BUDAPEST, VIENNA);
 	System.out.println("----------------------------");
     }
    
    public void scanHolidays(LocalDate startDate, LocalDate endDate, int minimumDays, int maximumDays, int holidaysLimit, int allHolidaysLimit, List<HolidayPlan> holidayPlans){
	List<Holiday> allHolidays = Lists.newArrayList();
	
	for(HolidayPlan holidayPlan : holidayPlans){
	    List<Holiday> holidays = Lists.newArrayList();
	    
	    try{
        	FlightBundle flights = flightOptionProcessor.getFlights(startDate, endDate, holidayPlan.getFlightOptions());      	    
        	holidays = holidayCalculator.getHolidays(flights.getOutboundFlights(), flights.getReturnFlights(), minimumDays, maximumDays);
	    }
	    catch(Exception ex){
		LOGGER.error("Error during scanning holidayPlan {}", holidayPlan, ex);
	    }
	    
    	    System.out.println("----" + holidayPlan + "----");
    	    if(holidays != null && !holidays.isEmpty()){
    		if(holidayPlan.getDepartureStations().size() == 1){
    		    holidayFormatter.format(holidays, holidaysLimit);
    		}
    		else if(holidayPlan.getDepartureStations().size() == 2){
    		    holidayFormatter.format(holidays, holidaysLimit, BUDAPEST, VIENNA);
    		}
    		else if(holidayPlan.getDepartureStations().size() > 2){
    		    throw new IllegalStateException("Too many departure stations + holidayPlan.getDepartureStations()");
    		}
    	    }
    	    System.out.println("----------------------------");
    	    
    	    allHolidays.addAll(holidays);
	}
	
	System.out.println();
	System.out.println("---FINAL LIST---");
	holidayFormatter.format(allHolidays, allHolidaysLimit, BUDAPEST, VIENNA);
	System.out.println("----------------------------");
    }
}
