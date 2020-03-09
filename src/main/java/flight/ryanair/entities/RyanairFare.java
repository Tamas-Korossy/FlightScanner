package flight.ryanair.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairFare {

    private final double amount;

    @JsonCreator
    public RyanairFare(@JsonProperty("amount") double amount) {
	this.amount = amount;
    }
  
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
	return "RyanairFare [amount=" + amount + "]";
    }
}
