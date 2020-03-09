package flight;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.collect.Lists;

import flight.config.FlightConfiguration;
import flight.entities.holiday.HolidayPlan;
import flight.logic.FlightScanner;

public class MainScanHolidays {

    private static final LocalDate START_DATE = LocalDate.of(2020, 4, 1);
    private static final LocalDate END_DATE = LocalDate.of(2020, 5, 1);
    
    private static final int MINIMUM_DAYS = 4;
    private static final int MAXIMUM_DAYS = 6;
    
    private static final int HOLIDAYS_LIMIT = 10;
    private static final int FINAL_HOLIDAYS_LIMIT = 100;
    
    private static final List<HolidayPlan> HOLIDAY_PLANS = Lists.newArrayList(
	HolidayPlan.MALMO_COPENHAGEN_TRIP,
	HolidayPlan.STOCKHOLM_TRIP,
	HolidayPlan.TIRANA_TRIP
    );
    
    public static void main(String[] args) throws Exception{
	FlightScanner flightScanner;
	try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FlightConfiguration.class)){
	    flightScanner = ctx.getBean(FlightScanner.class);
	}
	
	flightScanner.scanHolidays(START_DATE, END_DATE, MINIMUM_DAYS, MAXIMUM_DAYS, HOLIDAYS_LIMIT, FINAL_HOLIDAYS_LIMIT, HOLIDAY_PLANS);
    }
    
}
