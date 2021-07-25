package co.bench.restTest.service;

import co.bench.restTest.datasource.TransactionDataSource;
import co.bench.restTest.datasource.dto.TransactionAmountWrapper;
import co.bench.restTest.model.TransactionAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {
  private final TransactionDataSource dataSource;

  public TransactionService(@Autowired TransactionDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Map<LocalDate, BigDecimal> getRunningBalances() throws RestClientException {
    Map<LocalDate, BigDecimal> dailyTransactionAmounts = _getDailyTransactionAmounts();

    List<LocalDate> dates = new ArrayList<>(dailyTransactionAmounts.keySet());
    Collections.sort(dates);

    // update dailyTransactionAmounts in-place to contain running balance
    for (int i = 0; i < dates.size(); i++) {
      LocalDate currentKey = dates.get(i);
      BigDecimal currentBalance = dailyTransactionAmounts.get(currentKey);
      if (i > 0) {
        LocalDate previousKey = dates.get(i - 1);
        BigDecimal runningBalance = currentBalance.add(dailyTransactionAmounts.get(previousKey));
        dailyTransactionAmounts.replace(currentKey, runningBalance);
      }
    }

    return dailyTransactionAmounts;
  }

  private Map<LocalDate, BigDecimal> _getDailyTransactionAmounts() throws RestClientException {
    Map<LocalDate, BigDecimal> dailyTransactionAmounts = new HashMap<>();
    TransactionAmountWrapper transactionAmountWrapper;
    int currentPageNum = 1;

    transactionAmountWrapper = dataSource.getTransactionsByPage(currentPageNum);
    _populateDailyTransactionAmounts(
        dailyTransactionAmounts, transactionAmountWrapper.getTransactions());
    currentPageNum++;

    int numPages = transactionAmountWrapper.getTotalPages();

    while (currentPageNum <= numPages) {
      transactionAmountWrapper = dataSource.getTransactionsByPage(currentPageNum);
      _populateDailyTransactionAmounts(
          dailyTransactionAmounts, transactionAmountWrapper.getTransactions());
      currentPageNum++;
    }

    return dailyTransactionAmounts;
  }

  private void _populateDailyTransactionAmounts(
      Map<LocalDate, BigDecimal> dailyTransactionAmounts,
      List<TransactionAmount> transactionAmounts) {
    for (TransactionAmount transactionAmount : transactionAmounts) {
      LocalDate key = transactionAmount.getDate();
      if (dailyTransactionAmounts.containsKey(key)) {
        dailyTransactionAmounts.replace(
            key, dailyTransactionAmounts.get(key).add(transactionAmount.getAmount()));
      } else {
        dailyTransactionAmounts.put(key, transactionAmount.getAmount());
      }
    }
  }
}
