package flight.wizzair.entities;

import static flight.entities.refdata.Station.BARCELONA;
import static flight.entities.refdata.Station.BUDAPEST;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import flight.entities.AbstractTest;
import flight.entities.Flight;
import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;

public class WizzairSearchResponseTest extends AbstractTest{

    @Test
    public void testSearchResponseJsonConversion() throws Exception {
	WizzairSearchResponse searchResponse = objectMapper.readValue(readFromFile("searchResponse.json"), WizzairSearchResponse.class);
	Assert.assertFalse(searchResponse.getOutboundFlights().isEmpty());
	Assert.assertFalse(searchResponse.getReturnFlights().isEmpty());
    }
    
    @Test
    public void testSearchResponseJsonConversion_multi() throws Exception {
	WizzairSearchResponse searchResponse = objectMapper.readValue(readFromFile("searchResponse_multi.json"), WizzairSearchResponse.class);
	
	Assert.assertEquals(searchResponse.getOutboundFlights().size(), 2);	
	List<Flight> outboundFlights = searchResponse.getOutboundFlights()
	   .stream()
	   .flatMap(r -> r.toFlights().stream())
	   .collect(Collectors.toList());
	Assert.assertEquals(outboundFlights.size(), 3);
	
	assertFlight(outboundFlights.get(0), LocalDateTime.of(2020, 5, 23, 6, 0), 9690.0, Currency.HUF, BUDAPEST, BARCELONA, Airline.WIZZAIR, true);
	assertFlight(outboundFlights.get(1), LocalDateTime.of(2020, 5, 23, 17, 10), 9690.0, Currency.HUF, BUDAPEST, BARCELONA, Airline.WIZZAIR, true);
	assertFlight(outboundFlights.get(2), LocalDateTime.of(2020, 5, 24, 6, 0), 21090.0, Currency.HUF, BUDAPEST, BARCELONA, Airline.WIZZAIR, false);
	
	Assert.assertEquals(searchResponse.getReturnFlights().size(), 2);	
	List<Flight> returnFlights = searchResponse.getReturnFlights()
	   .stream()
	   .flatMap(r -> r.toFlights().stream())
	   .collect(Collectors.toList());
	Assert.assertEquals(returnFlights.size(), 3);
	
	assertFlight(returnFlights.get(0), LocalDateTime.of(2020, 5, 22, 9, 15), 21090.0, Currency.HUF, BARCELONA, BUDAPEST, Airline.WIZZAIR, false);
	assertFlight(returnFlights.get(1), LocalDateTime.of(2020, 5, 23, 9, 15), 19490.0, Currency.HUF, BARCELONA, BUDAPEST, Airline.WIZZAIR, true);
	assertFlight(returnFlights.get(2), LocalDateTime.of(2020, 5, 23, 20, 25), 19490.0, Currency.HUF, BARCELONA, BUDAPEST, Airline.WIZZAIR, true);
    }

}


