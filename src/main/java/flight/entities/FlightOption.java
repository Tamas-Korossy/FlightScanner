package flight.entities;

import flight.entities.refdata.Airline;
import flight.entities.refdata.Station;

public class FlightOption {

    private final Station departureStation;
    private final Station arrivalStation;
    private final Airline airline;
    
    public static FlightOption fromBudapestTo(Station arrivalStation, Airline airline){
	return new FlightOption(Station.BUDAPEST, arrivalStation, airline);
    }
    
    public static FlightOption fromViennaTo(Station arrivalStation, Airline airline){
	return new FlightOption(Station.VIENNA, arrivalStation, airline);
    }
    
    private FlightOption(Station departureStation, Station arrivalStation, Airline airline) {
	this.departureStation = departureStation;
	this.arrivalStation = arrivalStation;
	this.airline = airline;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }
    
    public Airline getAirline() {
        return airline;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((airline == null) ? 0 : airline.hashCode());
	result = prime * result
		+ ((arrivalStation == null) ? 0 : arrivalStation.hashCode());
	result = prime
		* result
		+ ((departureStation == null) ? 0 : departureStation.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FlightOption other = (FlightOption) obj;
	if (airline != other.airline)
	    return false;
	if (arrivalStation != other.arrivalStation)
	    return false;
	if (departureStation != other.departureStation)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "[" + departureStation + "->" + arrivalStation + " by " + airline + "]";
    }
    
    
    
    
}
