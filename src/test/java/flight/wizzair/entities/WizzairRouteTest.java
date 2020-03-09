package flight.wizzair.entities;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import flight.entities.AbstractTest;

public class WizzairRouteTest extends AbstractTest{

    @Test
    public void testRouteJsonConversion() throws Exception{
	WizzairRoute route = objectMapper.readValue(readFromFile("route.json"), WizzairRoute.class);
	
	Assert.assertEquals(route.getDepartureDateTimes().size(), 1);
	Assert.assertEquals(route.getDepartureDateTimes().get(0), LocalDateTime.of(2020, 2, 1, 12, 45));
	Assert.assertEquals(route.getPrice(), 7990.0, 0);
	Assert.assertEquals(route.getDepartureStation(), "BUD");
	Assert.assertEquals(route.getArrivalStation(), "MLA");
	Assert.assertEquals(route.isBuggy(), false);
    }

}

