package co.bench.restTest.service;

import co.bench.restTest.datasource.TransactionDataSource;
import co.bench.restTest.datasource.dto.TransactionAmountWrapper;
import co.bench.restTest.model.TransactionAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class TransactionServiceTest {
    @Autowired
    TransactionService transactionService;

    @MockBean
    TransactionDataSource mockDataSource;

    @Test
    @DisplayName("Should make # pages data source calls and return running balances")
    void makeMultipleDataSourceCalls() {
        assertNotNull(mockDataSource);
        // given
        List<TransactionAmount> givenTransactions1 = new ArrayList<>();
        List<TransactionAmount> givenTransactions2 = new ArrayList<>();
        TransactionAmount givenTransaction1 = new TransactionAmount(LocalDate.parse("2013-12-21"), BigDecimal.valueOf(1000));
        TransactionAmount givenTransaction2 = new TransactionAmount(LocalDate.parse("2013-12-21"), BigDecimal.valueOf(2000.51));
        TransactionAmount givenTransaction3 = new TransactionAmount(LocalDate.parse("2013-12-22"), BigDecimal.valueOf(10));
        TransactionAmount givenTransaction4 = new TransactionAmount(LocalDate.parse("2013-12-23"), BigDecimal.valueOf(1.49));
        TransactionAmount givenTransaction5 = new TransactionAmount(LocalDate.parse("2013-12-23"), BigDecimal.valueOf(0));
        givenTransactions1.add(givenTransaction1);
        givenTransactions1.add(givenTransaction2);
        givenTransactions1.add(givenTransaction3);
        givenTransactions2.add(givenTransaction4);
        givenTransactions2.add(givenTransaction5);
        TransactionAmountWrapper givenTransactionWrapper1 = new TransactionAmountWrapper(5, 1, givenTransactions1);
        TransactionAmountWrapper givenTransactionWrapper2 = new TransactionAmountWrapper(5, 2, givenTransactions2);

        // when
        Mockito.when(mockDataSource.getTransactionsByPage(1))
                .thenReturn(givenTransactionWrapper1);
        Mockito.when(mockDataSource.getTransactionsByPage(2))
                .thenReturn(givenTransactionWrapper2);

        Map<LocalDate, BigDecimal> runningBalances = transactionService.getRunningBalances();

        // then
        verify(mockDataSource, times(1)).getTransactionsByPage(1);
        verify(mockDataSource, times(1)).getTransactionsByPage(2);
        Map<LocalDate, BigDecimal> expectedMap = new HashMap<>();
        expectedMap.put(LocalDate.parse("2013-12-21"), BigDecimal.valueOf(3000.51));
        expectedMap.put(LocalDate.parse("2013-12-22"), BigDecimal.valueOf(3010.51));
        expectedMap.put(LocalDate.parse("2013-12-23"), BigDecimal.valueOf(3012).setScale(2));
        Assertions.assertEquals(expectedMap, runningBalances);
    }

    @Test
    @DisplayName("Should propagate datasource RestClientException")
    void propagateRestClientException() {
        assertNotNull(mockDataSource);
        // when
        Mockito.when(mockDataSource.getTransactionsByPage(1))
                .thenThrow(RestClientException.class);
        // then
        Assertions.assertThrows(RestClientException.class, () -> transactionService.getRunningBalances());
    }
}