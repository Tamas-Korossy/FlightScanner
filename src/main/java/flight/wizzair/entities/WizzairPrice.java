package flight.wizzair.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import flight.entities.refdata.Currency;

public class WizzairPrice {

    private double amount;
    private Currency currencyCode;
    
    @JsonCreator
    public WizzairPrice(@JsonProperty("amount") double amount, @JsonProperty("currencyCode") Currency currencyCode) {
	this.amount = amount;
	this.currencyCode = currencyCode;
    }
   
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
	return "Price [amount=" + amount + ", currencyCode=" + currencyCode + "]";
    } 
}
