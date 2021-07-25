package co.bench.restTest.datasource.dto;

import co.bench.restTest.model.TransactionAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionAmountDto {
  public TransactionAmountDto() {
    this.transactions = new ArrayList<>();
  }

  public TransactionAmountDto(int totalCount, int page, List<TransactionAmount> transactions) {
    this.totalCount = totalCount;
    this.page = page;
    this.transactions = transactions;
  }

  int totalCount;
  int page;
  List<TransactionAmount> transactions;

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<TransactionAmount> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<TransactionAmount> transactions) {
    this.transactions = transactions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransactionAmountDto that = (TransactionAmountDto) o;
    return totalCount == that.totalCount
        && page == that.page
        && transactions.equals(that.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, page, transactions);
  }

  @Override
  public String toString() {
    return "TransactionAmountWrapper{"
        + "totalCount="
        + totalCount
        + ", page="
        + page
        + ", transactions="
        + transactions
        + '}';
  }

  public int getTotalPages() throws IllegalArgumentException {
    if (page != 1) {
      throw new IllegalArgumentException("Can only call getTotalPages on the first page");
    }
    return (int) Math.ceil((double) totalCount / transactions.size());
  }
}
