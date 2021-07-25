package co.bench.restTest;

import co.bench.restTest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Profile("!test")
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private final TransactionService transactionService;

    public CommandLineTaskExecutor(@Autowired TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) {
        try {
            Map<LocalDate, BigDecimal> runningBalances = transactionService.getRunningBalances();
            List<LocalDate> dates = new ArrayList<>(runningBalances.keySet());
            Collections.sort(dates);
            for(LocalDate key : dates) {
                System.out.println(key + " " + runningBalances.get(key));
            }
        } catch(RestClientException e) {
            System.out.println("There was an error retrieving the balances. Please call Bench - their product is more reliable.");
        }
    }
}