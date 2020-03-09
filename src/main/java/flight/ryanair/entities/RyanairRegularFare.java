package flight.ryanair.entities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RyanairRegularFare {

    private static final Logger LOGGER = LoggerFactory.getLogger("RyanairRegularFare");
    
    private final RyanairFare fare;
    
    @JsonCreator
    public RyanairRegularFare(@JsonProperty("fares") List<RyanairFare> fares) {
	Assert.isTrue(!fares.isEmpty(), "Fares must not be empty");
	
	if(fares.size() > 1){
	    LOGGER.warn("We have multple fares: {}", fares);
	}
	
	this.fare = fares.get(0);
    }

    public RyanairFare getFare() {
        return fare;
    }

    @Override
    public String toString() {
	return "RyanairRegularFare [fare=" + fare + "]";
    }
}
