package co.bench.restTest.datasource;

import co.bench.restTest.datasource.dto.TransactionAmountDto;
import org.springframework.web.client.RestClientException;

public interface TransactionDataSource {
  TransactionAmountDto getTransactionsByPage(int pageNum) throws RestClientException;
}
