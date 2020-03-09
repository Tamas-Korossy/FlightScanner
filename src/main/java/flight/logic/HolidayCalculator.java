package flight.logic;

import java.time.LocalDate;
import java.util.List;

import com.google.common.collect.Lists;

import flight.entities.Flight;
import flight.entities.holiday.Holiday;

public class HolidayCalculator {
     
    public List<Holiday> getHolidays(List<Flight> outboundFlights, List<Flight> returnFlights, int minimumDays, int maximumDays){
	List<Holiday> holidays = Lists.newArrayList();
	
	for(Flight outboundFlight : outboundFlights){
	    for(int days=minimumDays; days<=maximumDays; days++){
		LocalDate returnDate = outboundFlight.getDepartureDate().plusDays(days-1);
		returnFlights.stream().filter(r -> r.getDepartureDate().equals(returnDate)).forEach(returnFlight -> {
		    holidays.add(new Holiday(outboundFlight, returnFlight));
		});
	    }
	}
		
	return holidays;
    }
}
