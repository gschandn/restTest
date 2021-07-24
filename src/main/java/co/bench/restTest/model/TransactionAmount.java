package co.bench.restTest.model;

import java.util.Date;

public class TransactionAmount {
    private final Date date;
    private final Double amount;

    public TransactionAmount(Date date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }
    public Double getAmount() {
        return amount;
    }
}
