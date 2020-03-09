package flight.wizzair.entities;

import java.time.LocalDate;

import org.junit.Test;

import flight.entities.AbstractTest;

public class WizzairSearchRequestTest extends AbstractTest{

    @Test
    public void testSearchRequestJsonConversion() throws Exception{
	WizzairFlight flightTo = new WizzairFlight("BUD", "MLA", LocalDate.of(2020,2,1), LocalDate.of(2020,3,1));
	WizzairFlight flightBack = new WizzairFlight("MLA", "BUD", LocalDate.of(2020,2,1), LocalDate.of(2020,3,1));	
	WizzairSearchRequest searchRequest = new WizzairSearchRequest(flightTo, flightBack);
	
	String searchRequestJson = objectMapper.writeValueAsString(searchRequest);

	assertContent(searchRequestJson, readFromFile("searchRequest.json"));
    }

}
