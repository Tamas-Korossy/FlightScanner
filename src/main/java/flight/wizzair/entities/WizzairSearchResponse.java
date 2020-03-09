package flight.wizzair.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WizzairSearchResponse {

    private final List<WizzairRoute> outboundFlights;
    private final List<WizzairRoute> returnFlights;
    
    @JsonCreator
    public WizzairSearchResponse(@JsonProperty("outboundFlights") List<WizzairRoute> outboundFlights, @JsonProperty("returnFlights") List<WizzairRoute> returnFlights) {
	this.outboundFlights = outboundFlights != null ? outboundFlights : Lists.newArrayList();
	this.returnFlights = returnFlights != null ? returnFlights : Lists.newArrayList();
    }

    public List<WizzairRoute> getOutboundFlights() {
        return outboundFlights;
    }

    public List<WizzairRoute> getReturnFlights() {
        return returnFlights;
    }

    @Override
    public String toString() {
	return "WizzairSearchResponse [outboundFlights=" + outboundFlights + ", returnFlights=" + returnFlights + "]";
    }  
}
