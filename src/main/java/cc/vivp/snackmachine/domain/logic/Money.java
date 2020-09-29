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

  public Money allocate(BigDecimal amount) {

    Preconditions.checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "money.allocate.negativeOrZero");

    int twentyDollarCountToAllocate = Math.min(amount.divide(BigDecimal.valueOf(20L)).intValue(),
        this.twentyDollarCount);
    amount = amount.subtract(BigDecimal.valueOf(twentyDollarCountToAllocate * 20L));

    int fiveDollarCountToAllocate = Math.min(amount.divide(BigDecimal.valueOf(5L)).intValue(), this.fiveDollarCount);
    amount = amount.subtract(BigDecimal.valueOf(fiveDollarCountToAllocate * 5L));

    int oneDollarCountToAllocate = Math.min(amount.intValue(), this.oneDollarCount);
    amount = amount.subtract(BigDecimal.valueOf(oneDollarCountToAllocate * 1L));

    int quarterCountToAllocate = Math.min(amount.divide(new BigDecimal("0.25")).intValue(), this.quarterCount);
    amount = amount.subtract(new BigDecimal(String.valueOf(quarterCountToAllocate * 0.25)));

    int tenCentCountToAllocate = Math.min(amount.divide(new BigDecimal("0.10")).intValue(), this.tenCentCount);
    amount = amount.subtract(new BigDecimal(String.valueOf(tenCentCountToAllocate * 0.10)));

    int oneCentCountToAllocate = Math.min(amount.divide(new BigDecimal("0.01")).intValue(), this.oneCentCount);

    return new Money(oneCentCountToAllocate, tenCentCountToAllocate, quarterCountToAllocate, oneDollarCountToAllocate,
        fiveDollarCountToAllocate, twentyDollarCountToAllocate);
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

  private static BigDecimal convertFromCents(int cents) {
    return new BigDecimal(cents).divide(BigDecimal.valueOf(100L));
  }
}
