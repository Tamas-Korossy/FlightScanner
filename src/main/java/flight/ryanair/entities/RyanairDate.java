package flight.ryanair.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairDate {

    private final LocalDate dateOut;
    private final List<RyanairFlight> flights;
    
    @JsonCreator
    public RyanairDate(@JsonProperty("dateOut") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDate dateOut,
	    @JsonProperty("flights") List<RyanairFlight> flights) {
	this.dateOut = dateOut;
	this.flights = flights != null ? flights : Lists.newArrayList();
    }
 
    public LocalDate getDateOut() {
        return dateOut;
    }

    public List<RyanairFlight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
	return "RyanairDate [dateOut=" + dateOut + ", flights=" + flights + "]";
    }
}
