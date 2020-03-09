package flight.ryanair.services;

import static flight.config.FlightConfiguration.RYANAIR_DAYS_RADIX;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flight.entities.Flight;
import flight.entities.FlightBundle;
import flight.entities.refdata.Station;
import flight.ryanair.client.RyanairWebClient;
import flight.services.FlightService;
import flight.util.ThreadSleeper;

public class RyanairFlightService implements FlightService{

    private static final Logger LOGGER = LoggerFactory.getLogger("RyanairFlightService");
    
    private final RyanairWebClient ryanarWebClient;
    private final ThreadSleeper threadSleeper;
    
    public RyanairFlightService(RyanairWebClient ryanarWebClient, ThreadSleeper threadSleeper){
	this.ryanarWebClient = ryanarWebClient;
	this.threadSleeper = threadSleeper;
    }
    
    @Override
    public FlightBundle getFlights(Station departureStation, Station arrivalStation, LocalDate startDate, LocalDate endDate) {
	List<Flight> flights = new ArrayList<>();
	
	startDate = startDate.plusDays(RYANAIR_DAYS_RADIX);
	boolean firstRun = true;
	while(startDate.minusDays(RYANAIR_DAYS_RADIX).isBefore(endDate) || startDate.minusDays(RYANAIR_DAYS_RADIX).isEqual(endDate)){
	    if(firstRun){
		firstRun = false;
	    }
	    else{
		threadSleeper.sleep();
	    }
	    
	    LOGGER.info("Fetching Ryanair flights between {} and {} around {} within {} days", departureStation, arrivalStation, startDate, RYANAIR_DAYS_RADIX);
	    flights.addAll(ryanarWebClient.getAvailableFlights(departureStation, arrivalStation, startDate).toFlights());
	    startDate = startDate.plusDays((2 * RYANAIR_DAYS_RADIX) + 1);
	}
	
	return new FlightBundle(
	    flights.stream().filter(f -> f.isTravelBetween(departureStation, arrivalStation)).collect(Collectors.toList()),
	    flights.stream().filter(f -> f.isTravelBetween(arrivalStation, departureStation)).collect(Collectors.toList())
        );
    }    
}
