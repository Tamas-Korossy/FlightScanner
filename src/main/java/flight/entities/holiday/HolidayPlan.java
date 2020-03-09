package flight.entities.holiday;

import static flight.entities.FlightOption.fromBudapestTo;
import static flight.entities.FlightOption.fromViennaTo;
import static flight.entities.refdata.Airline.RYANAIR;
import static flight.entities.refdata.Airline.WIZZAIR;
import static flight.entities.refdata.Station.BAKU;
import static flight.entities.refdata.Station.BARCELONA;
import static flight.entities.refdata.Station.BRUSSEL;
import static flight.entities.refdata.Station.BRUSSEL_CHARLEROI;
import static flight.entities.refdata.Station.COPENHAGEN;
import static flight.entities.refdata.Station.GLASGOW;
import static flight.entities.refdata.Station.LISBON;
import static flight.entities.refdata.Station.MALMO;
import static flight.entities.refdata.Station.MALTA;
import static flight.entities.refdata.Station.PORTO;
import static flight.entities.refdata.Station.STOCKHOLM;
import static flight.entities.refdata.Station.TIRANA;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import flight.entities.FlightOption;
import flight.entities.refdata.Station;

public enum HolidayPlan {

    BAKU_TRIP(fromBudapestTo(BAKU, WIZZAIR)),
    BARCELONA_TRIP(fromBudapestTo(BARCELONA, WIZZAIR), fromBudapestTo(BARCELONA, RYANAIR)),
    GLASGOW_TRIP(fromBudapestTo(GLASGOW, WIZZAIR)),
    TIRANA_TRIP(fromBudapestTo(TIRANA, WIZZAIR)),

    BRUSSEL_TRIP(fromBudapestTo(BRUSSEL, WIZZAIR)),
    BRUSSEL_CHARLEROI_TRIP(
	BRUSSEL_TRIP,
	fromBudapestTo(BRUSSEL_CHARLEROI, WIZZAIR), fromBudapestTo(BRUSSEL_CHARLEROI, RYANAIR)
    ),
    
    MALTA_TRIP(fromBudapestTo(MALTA, WIZZAIR), fromBudapestTo(MALTA, RYANAIR)),
    MALTA_BY_VIENNA_TRIP(
	MALTA_TRIP,
	fromViennaTo(MALTA, WIZZAIR),  fromViennaTo(MALTA, RYANAIR)
    ),
              
    LISBON_TRIP(fromBudapestTo(LISBON, WIZZAIR), fromBudapestTo(LISBON, RYANAIR)),
    LISBON_BY_VIENNA_TRIP(
	LISBON_TRIP,
	fromViennaTo(LISBON, WIZZAIR),  fromViennaTo(LISBON, RYANAIR)
    ),
    
    PORTO_TRIP(fromBudapestTo(PORTO, WIZZAIR), fromBudapestTo(PORTO, RYANAIR)),
    PORTO_BY_VIENNA_TRIP(
	PORTO_TRIP,
	fromViennaTo(PORTO, WIZZAIR),  fromViennaTo(PORTO, RYANAIR)
    ),
    
    STOCKHOLM_TRIP(fromBudapestTo(STOCKHOLM, WIZZAIR)),
    STOCKHOLM_BY_VIENNA_TRIP(
       STOCKHOLM_TRIP,
       fromViennaTo(STOCKHOLM, WIZZAIR),  fromViennaTo(STOCKHOLM, RYANAIR)
    ),
    
    MALMO_TRIP(fromBudapestTo(MALMO, WIZZAIR)),
    
    COPENHANGEN_TRIP(fromBudapestTo(COPENHAGEN, RYANAIR)),
    COPENHANGEN_BY_VIENNA_TRIP(
	COPENHANGEN_TRIP,
	fromViennaTo(COPENHAGEN, RYANAIR)
    ),
    
    MALMO_COPENHAGEN_TRIP(
       MALMO_TRIP,
       COPENHANGEN_TRIP
    );
     
    private final List<FlightOption> flightOptions;

    private HolidayPlan(HolidayPlan... holidayPlans) {
	this(Stream.of(holidayPlans).flatMap(h -> h.flightOptions.stream()).toArray(FlightOption[]::new));
    }
    
    private HolidayPlan(HolidayPlan holidayPlan, FlightOption... flightOptions) {
	this(Stream.concat(holidayPlan.flightOptions.stream(), Stream.of(flightOptions)).toArray(FlightOption[]::new));
    }
    
    private HolidayPlan(FlightOption... flightOptions) {
	this.flightOptions = Arrays.asList(flightOptions);
    }
 
    public List<FlightOption> getFlightOptions() {
        return flightOptions;
    }
    
    public Set<Station> getDepartureStations(){
	return flightOptions.stream().map(FlightOption::getDepartureStation).collect(Collectors.toSet());
    }
}
