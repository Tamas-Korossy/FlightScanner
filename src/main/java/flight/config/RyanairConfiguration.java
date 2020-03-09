package flight.config;

import org.apache.cxf.feature.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import flight.ryanair.client.RyanairWebClient;
import flight.ryanair.services.RyanairFlightService;
import flight.util.ThreadSleeper;

@SuppressWarnings("deprecation")
@Configuration
public class RyanairConfiguration {
    
    @Bean
    public RyanairWebClient ryanairWebClient(JacksonJsonProvider jsonProvider, LoggingFeature loggingFeature){
	return new RyanairWebClient(jsonProvider, loggingFeature);
    }
    
    @Bean
    public RyanairFlightService ryanairFlightService(RyanairWebClient ryanairWebClient, ThreadSleeper threadSleeper){
	return new RyanairFlightService(ryanairWebClient, threadSleeper);
    }
}
