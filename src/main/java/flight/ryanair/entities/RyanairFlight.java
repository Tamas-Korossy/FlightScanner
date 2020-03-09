package flight.ryanair.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairFlight {

    private final double price;
    private final LocalDateTime departureDateTime;
    
    @JsonCreator
    public RyanairFlight(@JsonProperty("regularFare") RyanairRegularFare regularFare, @JsonProperty("time") List<LocalDateTime> times) {
	this.price = regularFare.getFare().getAmount();
	this.departureDateTime = times.get(0);
    }
   
    public double getPrice() {
        return price;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }
}
