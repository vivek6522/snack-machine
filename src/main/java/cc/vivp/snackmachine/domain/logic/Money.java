package cc.vivp.snackmachine.domain.logic;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import lombok.Value;

@Value
public class Money {

  int oneCentCount;
  int tenCentCount;
  int quarterCount;
  int oneDollarCount;
  int fiveDollarCount;
  int twentyDollarCount;

  public Money(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount,
      int twentyDollarCount) {
    Preconditions.checkArgument(oneCentCount >= 0, "money.oneCentCount.negative");
    this.oneCentCount = oneCentCount;
    Preconditions.checkArgument(tenCentCount >= 0, "money.tenCentCount.negative");
    this.tenCentCount = tenCentCount;
    Preconditions.checkArgument(quarterCount >= 0, "money.quarterCount.negative");
    this.quarterCount = quarterCount;
    Preconditions.checkArgument(oneDollarCount >= 0, "money.oneDollarCount.negative");
    this.oneDollarCount = oneDollarCount;
    Preconditions.checkArgument(fiveDollarCount >= 0, "money.fiveDollarCount.negative");
    this.fiveDollarCount = fiveDollarCount;
    Preconditions.checkArgument(twentyDollarCount >= 0, "money.twentyDollarCount.negative");
    this.twentyDollarCount = twentyDollarCount;
  }

  public Money add(Money money) {
    return new Money(this.oneCentCount + money.oneCentCount, this.tenCentCount + money.tenCentCount,
        this.quarterCount + money.quarterCount, this.oneDollarCount + money.oneDollarCount,
        this.fiveDollarCount + money.fiveDollarCount, this.twentyDollarCount + money.twentyDollarCount);
  }

  public Money subtract(Money money) {
    return new Money(this.oneCentCount - money.oneCentCount, this.tenCentCount - money.tenCentCount,
        this.quarterCount - money.quarterCount, this.oneDollarCount - money.oneDollarCount,
        this.fiveDollarCount - money.fiveDollarCount, this.twentyDollarCount - money.twentyDollarCount);
  }

  public BigDecimal amount() {
    return convertFromCents(oneCentCount + tenCentCount * 10 + quarterCount * 25 + oneDollarCount * 100
        + fiveDollarCount * 500 + twentyDollarCount * 2000);
  }

  public static Money zero() {
    return new Money(0, 0, 0, 0, 0, 0);
  }

  public static Money cent() {
    return new Money(1, 0, 0, 0, 0, 0);
  }

  public static Money tenCent() {
    return new Money(0, 1, 0, 0, 0, 0);
  }

  public static Money quarter() {
    return new Money(0, 0, 1, 0, 0, 0);
  }

  public static Money dollar() {
    return new Money(0, 0, 0, 1, 0, 0);
  }

  public static Money fiveDollar() {
    return new Money(0, 0, 0, 0, 1, 0);
  }

  public static Money twentyDollar() {
    return new Money(0, 0, 0, 0, 0, 1);
  }

  // public static Money add(Money money1, Money money2) {
  //   return new Money(money1.oneCentCount + money2.oneCentCount, money1.tenCentCount + money2.tenCentCount,
  //       money1.quarterCount + money2.quarterCount, money1.oneDollarCount + money2.oneDollarCount,
  //       money1.fiveDollarCount + money2.fiveDollarCount, money1.twentyDollarCount + money2.twentyDollarCount);
  // }

  // public static Money subtract(Money money1, Money money2) {
  //   return new Money(money1.oneCentCount - money2.oneCentCount, money1.tenCentCount - money2.tenCentCount,
  //       money1.quarterCount - money2.quarterCount, money1.oneDollarCount - money2.oneDollarCount,
  //       money1.fiveDollarCount - money2.fiveDollarCount, money1.twentyDollarCount - money2.twentyDollarCount);
  // }

  private static BigDecimal convertFromCents(int cents) {
    return new BigDecimal(cents).divide(BigDecimal.valueOf(100L));
  }
}
