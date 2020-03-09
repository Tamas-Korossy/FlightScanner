package flight.ryanair.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import flight.entities.refdata.Currency;
import flight.entities.refdata.Station;
import flight.ryanair.client.RyanairWebClient;
import flight.ryanair.entities.RyanairSearchResponse;
import flight.ryanair.entities.RyanairTrip;
import flight.util.ThreadSleeper;

public class RyanairFlightServiceTest {

    private static final Station DEPARTURE_STATION = Station.VIENNA;
    private static final Station ARRIVAL_STATION = Station.MALTA;
    
    private RyanairFlightService ryanairFlightService;
    private RyanairWebClient ryanairWebClient;
    private ThreadSleeper threadSleeper;
    
    @Before
    public void setup(){
	ryanairWebClient = mock(RyanairWebClient.class);
	threadSleeper = mock(ThreadSleeper.class);
	
	when(ryanairWebClient.getAvailableFlights(any(Station.class), any(Station.class), any(LocalDate.class))).thenReturn(createEmptyResponse());
	ryanairFlightService = new RyanairFlightService(ryanairWebClient, threadSleeper);
    }

    @Test
    public void testGetRyanairFlights_startOfRadix(){
	ryanairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 3));
	
	verify(ryanairWebClient).getAvailableFlights(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 4)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetRyanairFlights_WithinRadix(){
	ryanairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 7));
	
	verify(ryanairWebClient).getAvailableFlights(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 4)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetRyanairFlights_startofNextRadix(){
	ryanairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 8));
	
	verify(ryanairWebClient).getAvailableFlights(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 4)));
	verify(ryanairWebClient).getAvailableFlights(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 11)));
	verify(threadSleeper, times(1)).sleep();
    }
    
    private RyanairSearchResponse createEmptyResponse(){
	List<RyanairTrip> trips = new ArrayList<>();
	trips.add(new RyanairTrip(DEPARTURE_STATION.getCode(), ARRIVAL_STATION.getCode(), Lists.newArrayList()));
	trips.add(new RyanairTrip(DEPARTURE_STATION.getCode(), ARRIVAL_STATION.getCode(), Lists.newArrayList()));
	return new RyanairSearchResponse(trips, Currency.HUF);
    }
}
