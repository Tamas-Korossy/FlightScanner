package flight.entities;

import flight.entities.refdata.Currency;

public class Price implements Comparable<Price>{
    
    private final double amount;
    private final Currency currency;
    
    public Price(double amount, Currency currency) {
	this.amount = amount;
	this.currency = currency;
    }

    public double getHufAmount(){
	return amount * currency.getHufExchange();
    }
    
    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    private Number getPrintableAmount(){
	if(currency == Currency.HUF){
	    return (int)amount;
	}
	else if(currency == Currency.EUR){
	    return amount;
	}
	else{
	    throw new IllegalArgumentException("Unsupported currency: " + currency);
	}
    }
    
    @Override
    public String toString() {
	return getPrintableAmount() + " " + currency;
    }

    @Override
    public int compareTo(Price p) {
	return (int)(this.amount - p.amount);
    }
}
