package cc.vivp.snackmachine.domain.logic;

import static cc.vivp.snackmachine.domain.logic.Money.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Money specification")
public class MoneySpecs {

  @Test
  @DisplayName("should produce correct result when money instances are summed up")
  void givenMoneyInstancesWhenSummedThenProduceCorrectResult() {

    Money money1 = new Money(1, 2, 3, 4, 5, 6);
    Money money2 = new Money(1, 2, 3, 4, 5, 6);

    Money actualSum = money1.add(money2);

    Money expectedSum = new Money(2, 4, 6, 8, 10, 12);

    assertEquals(expectedSum, actualSum);
    assertEquals(money1, money2);
  }

  @Test
  @DisplayName("should produce correct result when money instances are subtracted")
  void givenMoneyInstancesWhenSubtractedThenProduceCorrectResult() {

    Money money1 = new Money(1, 2, 3, 4, 5, 6);
    Money money2 = new Money(1, 2, 3, 4, 5, 6);

    Money actualSubtract = money1.subtract(money2);

    Money expectedSubtract = zero();

    assertEquals(expectedSubtract, actualSubtract);
  }

  @ParameterizedTest
  @CsvSource({ "-1, 0, 0, 0, 0, 0", "0, -2, 0, 0, 0, 0", "0, 0, -3, 0, 0, 0", "0, 0, 0, -4, 0, 0", "0, 0, 0, 0, -5, 0",
      "0, 0, 0, 0, 0, -6" })
  @DisplayName("should fail on creation of money with negative values")
  void givenNegativeValuesWhenCreateMoneyThenFails(int oneCentCount, int tenCentCount, int quarterCount,
      int oneDollarCount, int fiveDollarCount, int twentyDollarCount) {

    assertThrows(IllegalArgumentException.class,
        () -> new Money(oneCentCount, tenCentCount, quarterCount, oneDollarCount, fiveDollarCount, twentyDollarCount));
  }

  @ParameterizedTest
  @CsvSource({ "0, 0, 0, 0, 0, 0, 0", "1, 0, 0, 0, 0, 0, 0.01", "1, 2, 0, 0, 0, 0, 0.21", "1, 2, 3, 0, 0, 0, 0.96",
      "1, 2, 3, 4, 0, 0, 4.96", "1, 2, 3, 4, 5, 0, 29.96", "1, 2, 3, 4, 5, 6, 149.96", "11, 0, 0, 0, 0, 0, 0.11",
      "110, 0, 0, 0, 100, 0, 501.1" })
  @DisplayName("should calculate amount correctly")
  void givenMoneyInstanceWhenCalculateAmountThenAmountIsCorrect(int oneCentCount, int tenCentCount, int quarterCount,
      int oneDollarCount, int fiveDollarCount, int twentyDollarCount, BigDecimal expectedAmount) {

    Money money = new Money(oneCentCount, tenCentCount, quarterCount, oneDollarCount, fiveDollarCount,
        twentyDollarCount);
    assertEquals(expectedAmount, money.amount());
  }

}
