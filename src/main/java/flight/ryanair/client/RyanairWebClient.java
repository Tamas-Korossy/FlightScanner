package flight.ryanair.client;

import static flight.config.FlightConfiguration.RYANAIR_BASE_URL;
import static flight.config.FlightConfiguration.RYANAIR_DAYS_RADIX;

import java.time.LocalDate;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.feature.LoggingFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import flight.client.AbstractWebClient;
import flight.entities.refdata.Station;
import flight.ryanair.entities.RyanairSearchResponse;

@SuppressWarnings("deprecation")
public class RyanairWebClient extends AbstractWebClient{
    
    public RyanairWebClient(JacksonJsonProvider jsonProvider, LoggingFeature loggingFeature){
	super(RYANAIR_BASE_URL, jsonProvider, loggingFeature);
    }
    
    public RyanairSearchResponse getAvailableFlights(Station departureStation, Station arrivalStation, LocalDate flightDate){
	return sendRequest(client -> {
	    client.accept(MediaType.APPLICATION_JSON);
	    
	    return client
	    	.path("availability")
	    	   .query("ADT", 1)
	    	   .query("CHD", 0)
	    	   .query("DateIn", flightDate)
	    	   .query("DateOut", flightDate)
	    	   .query("Destination", arrivalStation.getCode())
	    	   .query("Disc", 0)
	    	   .query("INF", 0)
	    	   .query("Origin", departureStation.getCode())
	    	   .query("RoundTrip", true)
	    	   .query("TEEN", 0)
	    	   .query("FlexDaysIn", RYANAIR_DAYS_RADIX)
	    	   .query("FlexDaysBeforeIn", RYANAIR_DAYS_RADIX)
	    	   .query("FlexDaysOut", RYANAIR_DAYS_RADIX)
	    	   .query("FlexDaysBeforeOut", RYANAIR_DAYS_RADIX)
	    	   .query("ToUs", "AGREED")
	    	   .query("IncludeConnectingFlights", false)	
	    	.get()
	    	.readEntity(RyanairSearchResponse.class);
	});
    }
}
