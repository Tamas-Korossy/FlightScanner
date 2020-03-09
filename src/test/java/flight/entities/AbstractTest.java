package flight.entities;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;

import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;
import flight.entities.refdata.Station;
import flight.util.JavaDataObjectMapper;

@Ignore
public abstract class AbstractTest {

    protected final JavaDataObjectMapper objectMapper = new JavaDataObjectMapper();
    
    protected String readFromFile(String fileName){
	try {
	    return IOUtils.toString(getClass().getResourceAsStream(fileName), "UTF-8");
	}
	catch (IOException ex) {
	    throw new RuntimeException("Error during reading file " + fileName, ex);
	}
    }
    
    protected void assertContent(String str, String str2){
	Assert.assertEquals(str.replaceAll("\\s+", ""), str2.replaceAll("\\s+", ""));
    }
    
    protected void assertFlight(Flight flight, LocalDateTime departureDateTime, double priceAmount, Currency priceCurrency, Station departureStation,
	    Station arrivalStation, Airline airline, boolean isBuggy){
	Assert.assertEquals(flight.getDepartureDateTime(), departureDateTime);
	Assert.assertEquals(flight.getPrice().getAmount(), priceAmount, 0);
	Assert.assertEquals(flight.getPrice().getCurrency(), priceCurrency);
	Assert.assertEquals(flight.getDepartureStation(), departureStation);
	Assert.assertEquals(flight.getArrivalStation(), arrivalStation);
	Assert.assertEquals(flight.getAirline(), airline);
	Assert.assertEquals(flight.isBuggy(), isBuggy);
    }
    
}
