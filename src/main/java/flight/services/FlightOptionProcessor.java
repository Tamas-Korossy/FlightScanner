package flight.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import flight.entities.FlightBundle;
import flight.entities.FlightOption;
import flight.entities.refdata.Airline;
import flight.ryanair.services.RyanairFlightService;
import flight.wizzair.services.WizzairFlightService;

public class FlightOptionProcessor {

    private final ImmutableMap<Airline,FlightService> flightServiceMap;
    
    public FlightOptionProcessor(WizzairFlightService wizzairFlightService, RyanairFlightService ryanairFlightService){
	flightServiceMap = ImmutableMap.of(
	   Airline.WIZZAIR, wizzairFlightService,
	   Airline.RYANAIR, ryanairFlightService
	);
    }
    
    public FlightBundle getFlights(LocalDate startDate, LocalDate endDate, Collection<FlightOption> flightOptions) throws Exception{
	Map<Airline, List<FlightOption>> flightsByAirlines = flightOptions.stream().collect(Collectors.groupingBy(FlightOption::getAirline));
	
	FlightBundle flightBundle = new FlightBundle();
	
	CompletableFuture.allOf(
	   flightsByAirlines.entrySet().stream().map(e -> {
	       FlightService flightService = flightServiceMap.get(e.getKey());
	       if(flightService == null){
		   throw new IllegalArgumentException("Unsupported airline " + e.getKey());
	       }
	       return CompletableFuture.runAsync(() -> {
		   e.getValue().forEach(f -> flightBundle.addFlights(flightService.getFlights(f.getDepartureStation(), f.getArrivalStation(), startDate, endDate)));
	       });
	   })
	   .toArray(CompletableFuture[]::new)
	).get();
	
	return flightBundle;
    }
    
}
