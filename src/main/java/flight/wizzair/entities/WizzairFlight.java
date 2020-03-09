package flight.wizzair.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WizzairFlight {    
    private final String departureStation;
    private final String arrivalStation;
    private final LocalDate from;
    private final LocalDate to;
    
    public WizzairFlight(String departureStation, String arrivalStation, LocalDate from, LocalDate to) {
	this.departureStation = departureStation;
	this.arrivalStation = arrivalStation;
	this.from = from;
	this.to = to;
    }
    
    public String getDepartureStation() {
        return departureStation;
    }
    
    public String getArrivalStation() {
        return arrivalStation;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFrom() {
        return from;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getTo() {
        return to;
    }

    @Override
    public String toString() {
	return "Flight [departureStation=" + departureStation
		+ ", arrivalStation=" + arrivalStation + ", from=" + from
		+ ", to=" + to + "]";
    }   
}
