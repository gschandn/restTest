package co.bench.restTest.datasource.network;

import co.bench.restTest.datasource.TransactionDataSource;
import co.bench.restTest.datasource.dto.TransactionAmountWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class NetworkDataSource implements TransactionDataSource {
    private final RestTemplate restTemplate;

    public NetworkDataSource(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TransactionAmountWrapper getTransactionsByPage(int pageNum) {
        // TODO: Clean this up, could just be a hardcoded string right now
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://resttest.bench.co/transactions/"+pageNum+".json");

        return restTemplate.getForObject(uriBuilder.toUriString(), TransactionAmountWrapper.class);
    }
}