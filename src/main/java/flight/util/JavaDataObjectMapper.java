package flight.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JavaDataObjectMapper extends ObjectMapper{
    
    private static final long serialVersionUID = -2909553604733788739L;

    public JavaDataObjectMapper(){
	super();
	this.registerModule(new JavaTimeModule());
    }
}
