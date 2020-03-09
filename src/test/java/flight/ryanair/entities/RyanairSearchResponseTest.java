package flight.ryanair.entities;

import static flight.entities.refdata.Station.BUDAPEST;
import static flight.entities.refdata.Station.LONDON_STANSTED;
import static flight.entities.refdata.Station.MALTA;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import flight.entities.AbstractTest;
import flight.entities.Flight;
import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;

public class RyanairSearchResponseTest extends AbstractTest{

    @Test
    public void testSearchResponseJsonConversion() throws Exception {
	RyanairSearchResponse searchResponse = objectMapper.readValue(readFromFile("searchResponse.json"), RyanairSearchResponse.class);
	List<Flight> flights = searchResponse.toFlights();
	
	Assert.assertNotNull(flights);
	Assert.assertEquals(flights.size(), 2);
	
	Flight outboundFlight = flights.get(0);
	Assert.assertEquals(outboundFlight.getDepartureDate(), LocalDate.of(2020, 2, 4));
	Assert.assertEquals(outboundFlight.getDepartureDateTime(), LocalDateTime.of(2020, 2, 4, 18, 40));
	Assert.assertEquals(outboundFlight.getPrice().getAmount(), 26386.0, 0);
	Assert.assertEquals(outboundFlight.getPrice().getCurrency(), Currency.HUF);
	Assert.assertEquals(outboundFlight.getDepartureStation(), BUDAPEST);
	Assert.assertEquals(outboundFlight.getArrivalStation(), MALTA);
	Assert.assertEquals(outboundFlight.getAirline(), Airline.RYANAIR);
	
	Flight returnFlight = flights.get(1);
	Assert.assertEquals(returnFlight.getDepartureDate(), LocalDate.of(2020, 2, 4));
	Assert.assertEquals(returnFlight.getDepartureDateTime(), LocalDateTime.of(2020, 2, 4, 21, 25));
	Assert.assertEquals(returnFlight.getPrice().getAmount(), 15152.0, 0);
	Assert.assertEquals(returnFlight.getPrice().getCurrency(), Currency.HUF);
	Assert.assertEquals(returnFlight.getDepartureStation(), MALTA);
	Assert.assertEquals(returnFlight.getArrivalStation(), BUDAPEST);
	Assert.assertEquals(returnFlight.getAirline(), Airline.RYANAIR);
    }
    

    @Test
    public void testSearchResponseJsonConversion_multi() throws Exception {
	RyanairSearchResponse searchResponse = objectMapper.readValue(readFromFile("searchResponse_multi.json"), RyanairSearchResponse.class);

	List<Flight> londonFlights = searchResponse.toFlights().stream()
	   .filter(f -> f.getDepartureDate().equals(LocalDate.of(2020, 3, 13)))
	   .filter(f -> f.getDepartureStation().equals(LONDON_STANSTED))
	   .filter(f -> f.getArrivalStation().equals(BUDAPEST))
	   .collect(Collectors.toList());
	
	Assert.assertNotNull(londonFlights);
	Assert.assertEquals(londonFlights.size(), 4);
	
	assertFlight(londonFlights.get(0), LocalDateTime.of(2020, 3, 13, 8, 45), 35630.0, Currency.HUF, LONDON_STANSTED, BUDAPEST, Airline.RYANAIR, false);
	assertFlight(londonFlights.get(1), LocalDateTime.of(2020, 3, 13, 12, 0), 35630.0, Currency.HUF, LONDON_STANSTED, BUDAPEST, Airline.RYANAIR, false);
	assertFlight(londonFlights.get(2), LocalDateTime.of(2020, 3, 13, 17, 35), 25189.0, Currency.HUF, LONDON_STANSTED, BUDAPEST, Airline.RYANAIR, false);
	assertFlight(londonFlights.get(3), LocalDateTime.of(2020, 3, 13, 20, 0), 14189.0, Currency.HUF, LONDON_STANSTED, BUDAPEST, Airline.RYANAIR, false);
    }

}
