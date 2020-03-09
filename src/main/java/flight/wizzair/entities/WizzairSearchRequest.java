package flight.wizzair.entities;

import java.util.List;

import com.google.common.collect.Lists;

public class WizzairSearchRequest {

    private final List<WizzairFlight> flightList;
    private final String priceType;
    private final int adultCount;
    private final int childCount;
    private final int infantCount;
    
    public WizzairSearchRequest(WizzairFlight flightTo, WizzairFlight flightback) {
	this.flightList = Lists.newArrayList(flightTo, flightback);
	this.priceType = "regular";
	this.adultCount = 1;
	this.childCount = 0;
	this.infantCount = 0;
    }

    public List<WizzairFlight> getFlightList() {
        return flightList;
    }

    public String getPriceType() {
        return priceType;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public int getInfantCount() {
        return infantCount;
    }

    @Override
    public String toString() {
	return "SearchRequest [flightList=" + flightList + ", priceType="
		+ priceType + ", adultCount=" + adultCount + ", childCount="
		+ childCount + ", infantCount=" + infantCount + "]";
    }
}
