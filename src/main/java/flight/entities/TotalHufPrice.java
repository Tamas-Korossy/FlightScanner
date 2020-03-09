package flight.entities;

import java.util.stream.Stream;

public class TotalHufPrice implements Comparable<TotalHufPrice>{

    private final int amount;
    
    public TotalHufPrice(Price... prices) {
	this.amount = Stream.of(prices)
	   .map(Price::getHufAmount)
	   .reduce(0.0, (a, b) -> a + b)
	   .intValue();
    }
       
    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(TotalHufPrice s) {
	return this.amount - s.amount;
    }
    
    @Override
    public String toString() {
	return amount + " HUF";
    }
}
