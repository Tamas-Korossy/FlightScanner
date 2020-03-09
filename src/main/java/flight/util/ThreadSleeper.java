package flight.util;

import static flight.config.FlightConfiguration.SLEEP_BASE_MS;
import static flight.config.FlightConfiguration.SLEEP_RANDOM_INCR_MS;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadSleeper {

    private static final Logger LOGGER = LoggerFactory.getLogger("ThreadSleeper");
    
    private Random randomGenerator = new Random();
    
    public void sleep(){
	int sleepMs = SLEEP_BASE_MS + randomGenerator.nextInt(SLEEP_RANDOM_INCR_MS);
	LOGGER.info("We will sleep {} milliseconds", sleepMs);
	
	try {
	    Thread.sleep(sleepMs);
	}
	catch (Exception ex) {
	    throw new RuntimeException("Error during thread sleeping", ex);
	}
    }
    
}
