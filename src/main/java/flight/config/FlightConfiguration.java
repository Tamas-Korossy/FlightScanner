package flight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import flight.logic.FlightScanner;
import flight.logic.HolidayCalculator;
import flight.ryanair.services.RyanairFlightService;
import flight.services.FlightOptionProcessor;
import flight.util.HolidayFormatter;
import flight.wizzair.services.WizzairFlightService;

@Import({UtilConfiuration.class, WizzairConfiguration.class, RyanairConfiguration.class}) 
@Configuration
public class FlightConfiguration {   
    public static final String WIZZAIR_BASE_URL = "https://be.wizzair.com/10.15.0/Api/";
    
    public static final String RYANAIR_BASE_URL = "https://www.ryanair.com/api/booking/v4/hu-hu/";
    public static final int RYANAIR_DAYS_RADIX = 3;
    
    public static final int SLEEP_BASE_MS = 15000;
    public static final int SLEEP_RANDOM_INCR_MS = 15000;
       
    @Bean
    public HolidayCalculator holidayCalculator(){
	return new HolidayCalculator();
    }
            
    @Bean
    public FlightOptionProcessor flightOptionProcessor(WizzairFlightService wizzairFlightService, RyanairFlightService ryanairFlightService){
	return new FlightOptionProcessor(wizzairFlightService, ryanairFlightService);
    }
            
    @Bean
    public FlightScanner flightScanner(FlightOptionProcessor flightOptionProcessor, HolidayCalculator holidayCalculator, HolidayFormatter holidayFormatter){
	return new FlightScanner(flightOptionProcessor, holidayCalculator, holidayFormatter);
    }
}
