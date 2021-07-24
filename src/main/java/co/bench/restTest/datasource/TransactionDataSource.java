package co.bench.restTest.datasource;

import co.bench.restTest.datasource.dto.TransactionAmountWrapper;


public interface TransactionDataSource {
    TransactionAmountWrapper getTransactionsByPage(int pageNum);
}
