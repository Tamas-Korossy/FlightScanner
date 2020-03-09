package flight.wizzair.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import flight.entities.Flight;
import flight.entities.refdata.Airline;
import flight.entities.refdata.Currency;
import flight.entities.refdata.Station;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WizzairRoute{

    private final String departureStation;
    private final String arrivalStation;
    private final double price;
    private final Currency currency;
    private final List<LocalDateTime> departureDateTimes;
    private final boolean isBuggy;

    @JsonCreator
    public WizzairRoute(
	    @JsonProperty("departureStation") String departureStation,
	    @JsonProperty("arrivalStation") String arrivalStation,
	    @JsonProperty("departureDate") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDate departureDate,
	    @JsonProperty("price") WizzairPrice price,
	    @JsonProperty("departureDates") List<LocalDateTime> departureDates) {
	this.departureStation = departureStation;
	this.arrivalStation = arrivalStation;
	this.price = price.getAmount();
	this.currency = price.getCurrencyCode();
	this.departureDateTimes = departureDates != null ? departureDates : Lists.newArrayList();
	this.isBuggy = this.departureDateTimes.size() > 1;
    }
    
    public String getDepartureStation() {
        return departureStation;
    }
    
    public String getArrivalStation() {
        return arrivalStation;
    }

    public double getPrice() {
        return price;
    }
  
    public Currency getCurrency() {
        return currency;
    }

    public List<LocalDateTime> getDepartureDateTimes() {
        return departureDateTimes;
    }
    
    public boolean isBuggy() {
        return isBuggy;
    }

    public List<Flight> toFlights(){
	return departureDateTimes
	   .stream()
	   .map(d -> new Flight(d, price, currency, Station.getStationByCode(departureStation), Station.getStationByCode(arrivalStation), Airline.WIZZAIR, isBuggy))
	   .collect(Collectors.toList());
    }
}
