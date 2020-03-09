package flight.wizzair.services;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import flight.entities.FlightBundle;
import flight.entities.refdata.Station;
import flight.services.FlightService;
import flight.util.ThreadSleeper;
import flight.wizzair.client.WizzairWebClient;
import flight.wizzair.entities.WizzairRoute;
import flight.wizzair.entities.WizzairSearchResponse;

public class WizzairFlightService implements FlightService{

    private static final Logger LOGGER = LoggerFactory.getLogger("WizzairFlightService");
    
    private final WizzairWebClient wizzairWebClient;
    private final ThreadSleeper threadSleeper;
    
    public WizzairFlightService(WizzairWebClient wizzairWebClient, ThreadSleeper threadSleeper){
	this.wizzairWebClient = wizzairWebClient;
	this.threadSleeper = threadSleeper;
    }
    
    @Override
    public FlightBundle getFlights(Station departureStation, Station arrivalStation, LocalDate startDate, LocalDate endDate) {
	List<WizzairRoute> outboundFlights = Lists.newArrayList();
	List<WizzairRoute> returnFlights = Lists.newArrayList();
	
	getWizzairFlights(departureStation, arrivalStation, startDate, endDate, outboundFlights, returnFlights);

	return new FlightBundle(
	    outboundFlights.stream().flatMap(r -> r.toFlights().stream()).collect(Collectors.toList()),
	    returnFlights.stream().flatMap(r -> r.toFlights().stream()).collect(Collectors.toList())
        );
    }
    
    private void getWizzairFlights(Station departureStation, Station arrivalStation, LocalDate startDate, LocalDate endDate, List<WizzairRoute> outboundFlights, List<WizzairRoute> returnFlights){
	LocalDate firstDayOfNextMonth = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
	if(firstDayOfNextMonth.isEqual(endDate) || firstDayOfNextMonth.isAfter(endDate)){
	    LOGGER.info("Fetching Wizzair flights between {} and {} from {} to {}", departureStation, arrivalStation, startDate, endDate);
	    WizzairSearchResponse searchResponse = wizzairWebClient.fetchTimetable(departureStation, arrivalStation, startDate, endDate);
	    outboundFlights.addAll(searchResponse.getOutboundFlights());
	    returnFlights.addAll(searchResponse.getReturnFlights());
	}
	else{
	    LOGGER.info("Fetching Wizzair flights between {} and {} from {} to {}", departureStation, arrivalStation, startDate, firstDayOfNextMonth);
	    WizzairSearchResponse searchResponse = wizzairWebClient.fetchTimetable(departureStation, arrivalStation, startDate, firstDayOfNextMonth);
	    outboundFlights.addAll(searchResponse.getOutboundFlights());
	    returnFlights.addAll(searchResponse.getReturnFlights());
	    
	    threadSleeper.sleep();
	    
	    getWizzairFlights(departureStation, arrivalStation, firstDayOfNextMonth, endDate, outboundFlights, returnFlights);
	}
    }
    
}
