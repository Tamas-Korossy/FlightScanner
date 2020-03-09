package flight.entities.refdata;

public enum Currency {
    HUF(1.0),
    EUR(336.85);
    
    private final double hufExchange;
    
    private Currency(double hufExchange){
	this.hufExchange = hufExchange;
    }

    public double getHufExchange() {
        return hufExchange;
    }
}
