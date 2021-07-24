package co.bench.restTest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class TransactionAmount {
    @JsonProperty("Date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private final LocalDate date;
    @JsonProperty("Amount")
    private final Double amount;

    public TransactionAmount(LocalDate date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionAmount that = (TransactionAmount) o;
        return date.equals(that.date) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount);
    }

    @Override
    public String toString() {
        return "TransactionAmount{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
