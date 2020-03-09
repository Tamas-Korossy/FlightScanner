package flight.client;

import java.util.function.Function;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
public abstract class AbstractWebClient {

    private final String baseUrl;
    private final JacksonJsonProvider jsonProvider;
    private final LoggingFeature loggingFeature;
    
    public AbstractWebClient(String baseUrl, JacksonJsonProvider jsonProvider, LoggingFeature loggingFeature){
	this.baseUrl = baseUrl;
	this.jsonProvider = jsonProvider;
	this.loggingFeature = loggingFeature;
    }
        
    protected <T> T sendRequest(Function<WebClient, T> request){
	WebClient client = null;
	
	try{
	    client = createClient();
	    return request.apply(client);
	}
	catch(Exception ex){
	    throw new RuntimeException("Error during sending POST request", ex);
	}
	finally{
	    if(client != null){
		client.close();
	    }
	}
    }
    
    private WebClient createClient(){
	return WebClient.create(
	    baseUrl,
	    Lists.newArrayList(jsonProvider),
	    Lists.newArrayList(loggingFeature),
	    null
	);
    }
    
}
