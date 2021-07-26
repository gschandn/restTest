package co.bench.restTest.datasource.network;

import co.bench.restTest.datasource.dto.TransactionAmountDto;
import co.bench.restTest.model.GivenTransactionAmount;
import co.bench.restTest.model.GivenTransactionAmountDto;
import co.bench.restTest.model.TransactionAmount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ActiveProfiles("test")
@RestClientTest(BenchTransactionDataSource.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class BenchTransactionDataSourceTest {
  @Autowired private BenchTransactionDataSource dataSource;

  @Autowired private MockRestServiceServer mockServer;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName(
      "Should calculate correct # of pages and convert strings to LocalDate/BigDecimal with proper data")
  void receivedCorrectData() throws Exception {
    List<GivenTransactionAmount> givenTransactions = new ArrayList<>();
    GivenTransactionAmount givenTransaction1 = new GivenTransactionAmount("2013-12-21", "1000");
    GivenTransactionAmount givenTransaction2 = new GivenTransactionAmount("2013-12-21", "2000.51");
    givenTransactions.add(givenTransaction1);
    givenTransactions.add(givenTransaction2);
    GivenTransactionAmountDto givenTransactionWrapper =
        new GivenTransactionAmountDto(3, 1, givenTransactions);

    List<TransactionAmount> expectedTransactions = new ArrayList<>();
    TransactionAmount expectedTransaction1 =
        new TransactionAmount(LocalDate.parse("2013-12-21"), BigDecimal.valueOf(1000));
    TransactionAmount expectedTransaction2 =
        new TransactionAmount(LocalDate.parse("2013-12-21"), BigDecimal.valueOf(2000.51));
    expectedTransactions.add(expectedTransaction1);
    expectedTransactions.add(expectedTransaction2);
    TransactionAmountDto expectedTransactionWrapper =
        new TransactionAmountDto(3, 1, expectedTransactions);

    String givenTransaction = objectMapper.writeValueAsString(givenTransactionWrapper);

    mockServer
        .expect(requestTo("https://resttest.bench.co/transactions/1.json"))
        .andRespond(withSuccess(givenTransaction, MediaType.APPLICATION_JSON));

    TransactionAmountDto transactionAmountDto = dataSource.getTransactionsByPage(1);
    Assertions.assertEquals(transactionAmountDto.getPage(), 1);
    Assertions.assertEquals(transactionAmountDto.getTotalPages(), 2);
    Assertions.assertEquals(transactionAmountDto, expectedTransactionWrapper);
  }

  @Test
  @DisplayName("Should throw RestClientException on bad request")
  void receivedBadRequest() {
    mockServer
        .expect(requestTo("https://resttest.bench.co/transactions/1.json"))
        .andRespond(withBadRequest());

    Assertions.assertThrows(RestClientException.class, () -> dataSource.getTransactionsByPage(1));
  }

  @Test
  @DisplayName("Should throw RestClientException on 404")
  void receivedPageNotFound() {
    mockServer
        .expect(requestTo("https://resttest.bench.co/transactions/1.json"))
        .andRespond(withStatus(HttpStatus.NOT_FOUND));

    Assertions.assertThrows(RestClientException.class, () -> dataSource.getTransactionsByPage(1));
  }

  @Test
  @DisplayName("Should throw RestClientException on corrupt date")
  void receivedCorruptDate() throws Exception {
    List<GivenTransactionAmount> givenTransactions = new ArrayList<>();
    GivenTransactionAmount givenTransaction1 = new GivenTransactionAmount("20a13-12-21", "1000");
    givenTransactions.add(givenTransaction1);
    GivenTransactionAmountDto givenTransactionWrapper =
        new GivenTransactionAmountDto(3, 1, givenTransactions);

    String givenTransaction = objectMapper.writeValueAsString(givenTransactionWrapper);

    mockServer
        .expect(requestTo("https://resttest.bench.co/transactions/1.json"))
        .andRespond(withSuccess(givenTransaction, MediaType.APPLICATION_JSON));

    Assertions.assertThrows(RestClientException.class, () -> dataSource.getTransactionsByPage(1));
  }

  @Test
  @DisplayName("Should throw RestClientException on corrupt amount")
  void receivedCorruptData() throws Exception {
    List<GivenTransactionAmount> givenTransactions = new ArrayList<>();
    GivenTransactionAmount givenTransaction1 = new GivenTransactionAmount("2013-12-21", "10X0");
    givenTransactions.add(givenTransaction1);
    GivenTransactionAmountDto givenTransactionWrapper =
        new GivenTransactionAmountDto(3, 1, givenTransactions);

    String givenTransaction = objectMapper.writeValueAsString(givenTransactionWrapper);

    mockServer
        .expect(requestTo("https://resttest.bench.co/transactions/1.json"))
        .andRespond(withSuccess(givenTransaction, MediaType.APPLICATION_JSON));

    Assertions.assertThrows(RestClientException.class, () -> dataSource.getTransactionsByPage(1));
  }
}
