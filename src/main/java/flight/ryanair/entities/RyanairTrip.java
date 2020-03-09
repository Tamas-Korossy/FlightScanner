package flight.ryanair.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import flight.entities.Flight;
import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;
import flight.entities.refdata.Station;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairTrip {

    private final String origin;
    private final String destination;
    private final List<RyanairDate> dates;
    
    @JsonCreator
    public RyanairTrip(@JsonProperty("origin") String origin, @JsonProperty("destination") String destination,
	    @JsonProperty("dates") List<RyanairDate> dates) {
	this.origin = origin;
	this.destination = destination;
	this.dates = dates != null ? dates : Lists.newArrayList();
    }

    public List<Flight> toFlights(Currency currency){
	return dates.stream()
   	    .map(d -> d.getFlights().stream()
   	        .map(f -> new Flight(f.getDepartureDateTime(), f.getPrice(), currency, Station.getStationByCode(origin), Station.getStationByCode(destination), Airline.RYANAIR, false))
   	        .collect(Collectors.toList())
   	    )
   	    .flatMap(List::stream)
   	    .collect(Collectors.toList());
    }
  
    public List<RyanairDate> getDates() {
        return dates;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
	return "RyanairTrip [origin=" + origin + ", destination=" + destination+ ", dates=" + dates + "]";
    }
}
