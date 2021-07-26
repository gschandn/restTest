package co.bench.restTest.datasource.network;

import co.bench.restTest.datasource.TransactionDataSource;
import co.bench.restTest.datasource.dto.TransactionAmountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.*;

@Repository
public class BenchTransactionDataSource implements TransactionDataSource {
  private final RestTemplate restTemplate;

  public BenchTransactionDataSource(@Autowired RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public TransactionAmountDto getTransactionsByPage(int pageNum) throws RestClientException {
    String URL = "https://resttest.bench.co/transactions/" + pageNum + ".json";
    return restTemplate.getForObject(URL, TransactionAmountDto.class);
  }
}
