package flight.entities;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class FlightBundle {

    private List<Flight> outboundFlights;
    private List<Flight> returnFlights;
    
    public FlightBundle(){
	this(Lists.newArrayList(), Lists.newArrayList());
    }
    
    public FlightBundle(List<Flight> outboundFlights, List<Flight> returnFlights){
	this.outboundFlights = outboundFlights;
	this.returnFlights = returnFlights;
    }
    
    public FlightBundle addFlights(FlightBundle bundle){
	this.outboundFlights.addAll(bundle.outboundFlights);
	this.returnFlights.addAll(bundle.returnFlights);
	return this;
    }
           
    public void filterFlights(Predicate<Flight> filter){
	outboundFlights = outboundFlights.stream().filter(filter).collect(Collectors.toList());
	returnFlights = returnFlights.stream().filter(filter).collect(Collectors.toList());
    }
    
    public List<Flight> getOutboundFlights() {
        return outboundFlights;
    }

    public List<Flight> getReturnFlights() {
        return returnFlights;
    }
}
