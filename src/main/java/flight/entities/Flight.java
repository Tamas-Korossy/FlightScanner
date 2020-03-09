package flight.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;
import flight.entities.refdata.Station;

public class Flight implements Comparable<Flight>{
    
    private final LocalDateTime departureDateTime;
    private final Price price;
    private final Station departureStation;
    private final Station arrivalStation;
    private final Airline airline;
    private final boolean isBuggy;

    public Flight(LocalDateTime departureDateTime, double priceAmount, Currency priceCurrency, Station departureStation, Station arrivalStation, Airline airLine, 
	    boolean isBuggy) {
	this.departureDateTime = departureDateTime;
	this.price = new Price(priceAmount, priceCurrency);
	this.departureStation = departureStation;
	this.arrivalStation = arrivalStation;
	this.airline = airLine;
	this.isBuggy = isBuggy;
    }
 
    public boolean isStartIn(Station station){
	return getDepartureStation() == station;
    }
    
    public boolean isEndIn(Station station){
	return getArrivalStation() == station;
    }
    
    public boolean isTravelBetween(Station departureStation, Station arrivalStation){
	return isStartIn(departureStation) && isEndIn(arrivalStation);
    }
    
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public LocalDate getDepartureDate() {
        return departureDateTime.toLocalDate();
    }

    public Price getPrice() {
        return price;
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
     
    public boolean isBuggy() {
        return isBuggy;
    }

    @Override
    public int compareTo(Flight f) {
	return this.price.compareTo(f.price);
    }
    
    @Override
    public String toString() {
	return (isBuggy ? "*" : "") + departureDateTime + 
	   "[" + departureDateTime.getDayOfWeek() + "] (" + price + ") [" + departureStation + "->" + arrivalStation + " by " + airline + "]";
    }
}
