package flight.config;

import org.apache.cxf.feature.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import flight.util.HolidayFormatter;
import flight.util.JavaDataObjectMapper;
import flight.util.ThreadSleeper;

@SuppressWarnings("deprecation")
@Configuration
public class UtilConfiuration {

    @Bean
    public JavaDataObjectMapper javaDataObjectMapper(){
	return new JavaDataObjectMapper();
    }
    
    @Bean
    public JacksonJsonProvider jsonProvider(JavaDataObjectMapper javaDataObjectMapper){
	JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
	jsonProvider.setMapper(javaDataObjectMapper);
	return jsonProvider;
    }
	
    @Bean
    public LoggingFeature loggingFeature(){
	return new LoggingFeature();
    }
        
    @Bean
    public ThreadSleeper threadSleeper(){
	return new ThreadSleeper();
    }
    
    @Bean
    public HolidayFormatter holidayFormatter(){
	return new HolidayFormatter();
    }
    
}
