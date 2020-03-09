package flight.entities.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import flight.entities.Flight;
import flight.entities.TotalHufPrice;
import flight.entities.refdata.Station;

public class Holiday implements Comparable<Holiday>{
    
    private final Flight outboundFlight;
    private final Flight returnFlight;
    private final TotalHufPrice totalHufPrice;
    private final int days;
    private final int weekdays;
    
    public Holiday(Flight outboundFlight, Flight returnFlight) {
	this.outboundFlight = outboundFlight;
	this.returnFlight = returnFlight;	
	this.totalHufPrice = new TotalHufPrice(this.outboundFlight.getPrice(), this.returnFlight.getPrice());
	this.days = (int)ChronoUnit.DAYS.between(outboundFlight.getDepartureDate(), returnFlight.getDepartureDate()) + 1;
	this.weekdays = weekdaysBetweenDates(outboundFlight.getDepartureDate(), returnFlight.getDepartureDate());
    }
   
    public boolean isStartIn(Station station){
	return outboundFlight.isStartIn(station);
    }
    
    public boolean isEndIn(Station station){
	return returnFlight.isEndIn(station);
    }
    
    public boolean isTravelBetween(Station departureStation, Station arrivalStation){
	return isStartIn(departureStation) && isEndIn(arrivalStation);
    }
    
    public Flight getOutboundFlight() {
        return outboundFlight;
    }

    public Flight getReturnFlight() {
        return returnFlight;
    }

    public TotalHufPrice getTotalHufPrice() {
        return totalHufPrice;
    }

    public int getDays() {
        return days;
    }
    
    public int getWeekdays() {
        return weekdays;
    }

    public boolean isBuggy() {
        return outboundFlight.isBuggy() || returnFlight.isBuggy();
    }
   
    private int weekdaysBetweenDates(LocalDate startDate, LocalDate endDate){
	int weekdays = 0;
	
	while(startDate.isBefore(endDate) || startDate.isEqual(endDate)){
	    if(startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY){
		weekdays++;
	    }
	    startDate = startDate.plusDays(1);
	}
	
	return weekdays;
    }  
    
    @Override
    public int compareTo(Holiday o) {
	return this.totalHufPrice.compareTo(o.totalHufPrice);
    }
    
    @Override
    public String toString() {
	return (isBuggy() ? "*" : "") + totalHufPrice + " [" + days + "(" + weekdays + ")days] = " + outboundFlight + " -> " + returnFlight;
    }
}
