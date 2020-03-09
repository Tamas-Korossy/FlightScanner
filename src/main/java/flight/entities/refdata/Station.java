package flight.entities.refdata;

import java.util.stream.Stream;

public enum Station {
    BAKU("GYD"),
    BUDAPEST("BUD"),
    BARCELONA("BCN"),
    BRUSSEL("BRU"),
    BRUSSEL_CHARLEROI("CRL"),
    COPENHAGEN("CPH"),
    GLASGOW("GLA"),
    GOTEBORG("GOT"),
    LISBON("LIS"),
    LONDON_STANSTED("STN"),
    MALTA("MLA"),
    MALMO("MMX"),
    PORTO("OPO"),
    STOCKHOLM("NYO"),
    TIRANA("TIA"),
    VIENNA("VIE");

    public static Station getStationByCode(String code){
	return Stream.of(Station.values())
	   .filter(s -> s.code.equals(code))
	   .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown station code: " + code));
    }
    
    private final String code;

    private Station(String code) {
	this.code = code;
    }

    public String getCode() {
        return code;
    }
}
