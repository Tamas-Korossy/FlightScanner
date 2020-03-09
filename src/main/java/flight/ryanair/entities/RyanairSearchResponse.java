package flight.ryanair.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import flight.entities.Flight;
import flight.entities.refdata.Currency;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairSearchResponse {

    private static final Logger LOGGER = LoggerFactory.getLogger("RyanairSearchResponse");
    
    private final List<RyanairTrip> trips;
    private final Currency currency;

    @JsonCreator
    public RyanairSearchResponse(@JsonProperty("trips") List<RyanairTrip> trips, @JsonProperty("currency") Currency currency) {
	if(trips.size() > 2){
	    LOGGER.warn("More than two trips: {}", trips);
	}
	
	this.trips = trips;
	this.currency = currency;
    }

    public List<Flight> toFlights(){
	LOGGER.info("Outbund dates [{}]", trips.get(0).getDates().stream().map(d -> d.getDateOut().toString()).collect(Collectors.joining(", ")));
	LOGGER.info("Return dates [{}]", trips.get(1).getDates().stream().map(d -> d.getDateOut().toString()).collect(Collectors.joining(", ")));
	
	return trips.stream().flatMap(t -> t.toFlights(currency).stream()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
	return "RyanairSearchResponse [trips=" + trips + ", currency=" + currency + "]";
    }
}
