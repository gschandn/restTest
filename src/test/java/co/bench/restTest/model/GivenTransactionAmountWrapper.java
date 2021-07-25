package co.bench.restTest.model;

import java.util.List;

/**
 * We require custom objects for tests because we want to
 * test conversion of strings to LocalDate and BigDecimal
 */
public class GivenTransactionAmountWrapper {
    private final int totalCount;
    private final int page;
    private final List<GivenTransactionAmount> transactions;

    public GivenTransactionAmountWrapper(int totalCount, int page, List<GivenTransactionAmount> transactions) {
        this.totalCount = totalCount;
        this.page = page;
        this.transactions = transactions;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPage() {
        return page;
    }

    public List<GivenTransactionAmount> getTransactions() {
        return transactions;
    }
}
