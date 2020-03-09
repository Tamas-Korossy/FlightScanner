package flight.util;

import java.util.List;
import java.util.stream.Collectors;

import flight.entities.holiday.Holiday;
import flight.entities.refdata.Station;

public class HolidayFormatter {

    public void format(List<Holiday> holidays, int limit) {
	printHolidays("[LIST]", holidays.stream()
	   .sorted()
	   .limit(limit)
	   .collect(Collectors.toList()));
    }
    
    public void format(List<Holiday> holidays, int limit, Station firstStation, Station secondStation) {
	printHolidays("[TOP LIST]", holidays.stream()
	   .sorted()
	   .limit(limit)
	   .collect(Collectors.toList()));
	
	printHolidays("[" + firstStation + "-" + firstStation + "]", holidays.stream()
	   .filter(h -> h.isTravelBetween(firstStation, firstStation))
	   .sorted()
	   .limit(limit)
	   .collect(Collectors.toList()));

	printHolidays("[" + firstStation + "-" + secondStation + "]", holidays.stream()
	   .filter(h -> h.isTravelBetween(firstStation, secondStation) || h.isTravelBetween(secondStation, firstStation))
	   .sorted()
	   .limit(limit)
	   .collect(Collectors.toList()));
	
	printHolidays("[" + secondStation + "-" + secondStation + "]", holidays.stream()
	   .filter(h -> h.isTravelBetween(secondStation, secondStation))
	   .sorted()
	   .limit(limit)
	   .collect(Collectors.toList()));
    }
    
    private void printHolidays(String title, List<Holiday> holidays){
	System.out.println(title);
	String holidaysData = holidays.stream()
	    .map(h -> h.toString())
	    .collect(Collectors.joining(System.lineSeparator()));
	System.out.println(holidaysData);
	System.out.println("----------------------------");
    }
}
