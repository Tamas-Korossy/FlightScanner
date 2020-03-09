package flight.services;

import java.time.LocalDate;

import flight.entities.FlightBundle;
import flight.entities.refdata.Station;

public interface FlightService {
    
    FlightBundle getFlights(Station departureStation, Station arrivalStation, LocalDate startDate, LocalDate endDate);
    
}
