package flight.wizzair.entities;

import java.time.LocalDate;

import org.junit.Test;

import flight.entities.AbstractTest;

public class WizzairFlightTest extends AbstractTest{

    @Test
    public void testFlightJsonConversion() throws Exception{
	WizzairFlight flight = new WizzairFlight("BUD", "MLA", LocalDate.of(2020,2,1), LocalDate.of(2020,3,1));
	
	String flightJson = objectMapper.writeValueAsString(flight);

	assertContent(flightJson, readFromFile("flight.json"));
    }

}
