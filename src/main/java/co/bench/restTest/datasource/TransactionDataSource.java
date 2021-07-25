package co.bench.restTest.datasource;

import co.bench.restTest.datasource.dto.TransactionAmountWrapper;
import org.springframework.web.client.RestClientException;

public interface TransactionDataSource {
  TransactionAmountWrapper getTransactionsByPage(int pageNum) throws RestClientException;
}
