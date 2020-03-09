package flight.wizzair.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import flight.entities.refdata.Station;
import flight.util.ThreadSleeper;
import flight.wizzair.client.WizzairWebClient;
import flight.wizzair.entities.WizzairSearchResponse;

public class WizzairFlightServiceTest {

    private static final Station DEPARTURE_STATION = Station.VIENNA;
    private static final Station ARRIVAL_STATION = Station.MALTA;
    
    private WizzairFlightService wizzairFlightService;
    private WizzairWebClient wizzairWebClient;
    private ThreadSleeper threadSleeper;
    
    @Before
    public void setup(){
	wizzairWebClient = mock(WizzairWebClient.class);
	threadSleeper = mock(ThreadSleeper.class);
	
	when(wizzairWebClient.fetchTimetable(any(Station.class), any(Station.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(createEmptyResponse());
	wizzairFlightService = new WizzairFlightService(wizzairWebClient, threadSleeper);
    }

    @Test
    public void testGetWizzairFlights_firstOfThisMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 13));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 1)), eq(LocalDate.of(2020, 4, 13)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_thisWholeMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 30));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 1)), eq(LocalDate.of(2020, 4, 30)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_thisWholeMonth_2(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 1), LocalDate.of(2020, 5, 1));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 1)), eq(LocalDate.of(2020, 5, 1)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_inTheSameMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 4, 13));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 4, 13)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_firstOfNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 5, 1));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(threadSleeper, never()).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_secondOfNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 5, 2));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 5, 2)));
	verify(threadSleeper, times(1)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_middleOfNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 5, 18));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 5, 18)));
	verify(threadSleeper, times(1)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_endOfNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 5, 31));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 5, 31)));
	verify(threadSleeper, times(1)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_firstOfNextNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 6, 1));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 6, 1)));
	verify(threadSleeper, times(1)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_secondOfNextNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 6, 2));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 6, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 6, 1)), eq(LocalDate.of(2020, 6, 2)));
	verify(threadSleeper, times(2)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_middleOfNextNextMonth(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 6, 17));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 6, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 6, 1)), eq(LocalDate.of(2020, 6, 17)));
	verify(threadSleeper, times(2)).sleep();
    }
    
    @Test
    public void testGetWizzairFlights_nextFewMonths(){
	wizzairFlightService.getFlights(DEPARTURE_STATION, ARRIVAL_STATION, LocalDate.of(2020, 4, 5), LocalDate.of(2020, 8, 8));
	
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 4, 5)), eq(LocalDate.of(2020, 5, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 5, 1)), eq(LocalDate.of(2020, 6, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 6, 1)), eq(LocalDate.of(2020, 7, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 7, 1)), eq(LocalDate.of(2020, 8, 1)));
	verify(wizzairWebClient).fetchTimetable(eq(DEPARTURE_STATION), eq(ARRIVAL_STATION), eq(LocalDate.of(2020, 8, 1)), eq(LocalDate.of(2020, 8, 8)));
	verify(threadSleeper, times(4)).sleep();
    }
    
    private WizzairSearchResponse createEmptyResponse(){
	return new WizzairSearchResponse(Lists.newArrayList(), Lists.newArrayList());
    }
}
