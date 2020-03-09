package flight.wizzair.client;

import static flight.config.FlightConfiguration.WIZZAIR_BASE_URL;

import java.time.LocalDate;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.feature.LoggingFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import flight.client.AbstractWebClient;
import flight.entities.refdata.Station;
import flight.wizzair.entities.WizzairFlight;
import flight.wizzair.entities.WizzairSearchRequest;
import flight.wizzair.entities.WizzairSearchResponse;

@SuppressWarnings("deprecation")
public class WizzairWebClient extends AbstractWebClient{
    
    public WizzairWebClient(JacksonJsonProvider jsonProvider, LoggingFeature loggingFeature){
	super(WIZZAIR_BASE_URL, jsonProvider, loggingFeature);
    }
    
    public WizzairSearchResponse fetchTimetable(Station departureStation, Station arrivalStation, LocalDate startDate, LocalDate endDate){
	WizzairFlight flightTo = new WizzairFlight(departureStation.getCode(), arrivalStation.getCode(), startDate, endDate);
	WizzairFlight flightBack = new WizzairFlight(arrivalStation.getCode(), departureStation.getCode(), startDate, endDate);	
	WizzairSearchRequest searchRequest = new WizzairSearchRequest(flightTo, flightBack);

	return sendRequest(client -> {
	    client.accept(MediaType.APPLICATION_JSON);
	    client.type(MediaType.APPLICATION_JSON);
	    
	    return client
	    	.path("search/timetable")
	    	.post(searchRequest)
	    	.readEntity(WizzairSearchResponse.class);
	});
    }
}
