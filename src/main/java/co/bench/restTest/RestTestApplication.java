package co.bench.restTest;

import co.bench.restTest.datasource.network.NetworkDataSource;
import co.bench.restTest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestTestApplication implements CommandLineRunner {
	@Autowired TransactionService transactionService;
	public static void main(String[] args) {
		SpringApplication.run(RestTestApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(transactionService.getRunningBalances());
	}

}
