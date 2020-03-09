package flight.config;

import org.apache.cxf.feature.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import flight.util.ThreadSleeper;
import flight.wizzair.client.WizzairWebClient;
import flight.wizzair.services.WizzairFlightService;

@SuppressWarnings("deprecation")
@Configuration
public class WizzairConfiguration {

    @Bean
    public WizzairWebClient wizzairWebClient(JacksonJsonProvider jsonProvider, LoggingFeature loggingFeature){
	return new WizzairWebClient(jsonProvider, loggingFeature);
    }
    
    @Bean
    public WizzairFlightService wizzairFlightService(WizzairWebClient wizzairWebClient, ThreadSleeper threadSleeper){
	return new WizzairFlightService(wizzairWebClient, threadSleeper);
    }
    
}
