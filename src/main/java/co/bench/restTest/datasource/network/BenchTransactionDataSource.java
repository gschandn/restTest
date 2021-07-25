package co.bench.restTest.datasource.network;

import co.bench.restTest.datasource.TransactionDataSource;
import co.bench.restTest.datasource.dto.TransactionAmountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class BenchTransactionDataSource implements TransactionDataSource {
  private final RestTemplate restTemplate;

  public BenchTransactionDataSource(@Autowired RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public TransactionAmountDto getTransactionsByPage(int pageNum) throws RestClientException {
    // TODO: Clean this up, could just be a hardcoded string right now
    UriComponentsBuilder uriBuilder =
        UriComponentsBuilder.fromHttpUrl(
            "https://resttest.bench.co/transactions/" + pageNum + ".json");
    return restTemplate.getForObject(uriBuilder.toUriString(), TransactionAmountDto.class);
  }
}
