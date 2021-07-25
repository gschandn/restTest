package co.bench.restTest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GivenTransactionAmount {
    @JsonProperty("Date")
    private final String date;
    @JsonProperty("Amount")
    private final String amount;

    public GivenTransactionAmount(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }
}
