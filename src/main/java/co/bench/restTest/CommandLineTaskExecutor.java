package co.bench.restTest;

import co.bench.restTest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private final TransactionService transactionService;

    public CommandLineTaskExecutor(@Autowired TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) {
        long startTime = System.currentTimeMillis();
        transactionService.printRunningBalances();
        System.out.println("Run Time: " + (System.currentTimeMillis() - startTime));
    }
}