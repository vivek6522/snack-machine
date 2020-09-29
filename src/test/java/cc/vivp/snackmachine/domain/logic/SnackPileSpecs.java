package cc.vivp.snackmachine.domain.logic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Snack pile specification")
public class SnackPileSpecs {

  private static final Snack DUMMY_SNACK = new Snack("Cookie");

  @Test
  @DisplayName("should fail when negative quantity provided")
  void givenNegativeQuantityWhenCreateSnackPileThenFail() {
    assertThrows(IllegalArgumentException.class, () -> new SnackPile(DUMMY_SNACK, -1, BigDecimal.ONE));
  }

  @Test
  @DisplayName("should fail when negative price provided")
  void givenNegativePriceWhenCreateSnackPileThenFail() {
    BigDecimal negativePrice = new BigDecimal("-1");
    assertThrows(IllegalArgumentException.class, () -> new SnackPile(DUMMY_SNACK, 1, negativePrice));
  }

  @Test
  @DisplayName("should fail when price is not a multiple of one cent")
  void givenPriceNonMultipleOfOneCentWhenCreateSnackPileThenFail() {
    BigDecimal nonMultipleOfOneCent = new BigDecimal("0.012");
    assertThrows(IllegalArgumentException.class, () -> new SnackPile(DUMMY_SNACK, 1, nonMultipleOfOneCent));
  }
}
