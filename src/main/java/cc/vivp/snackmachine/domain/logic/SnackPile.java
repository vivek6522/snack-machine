package cc.vivp.snackmachine.domain.logic;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import lombok.Getter;

@Getter
public class SnackPile {

  private final Snack snack;
  private final int quantity;
  private final BigDecimal price;

  public SnackPile(Snack snack, int quantity, BigDecimal price) {

    Preconditions.checkArgument(quantity >= 0, "snackPile.quantity.negative");
    Preconditions.checkArgument(price.compareTo(BigDecimal.ZERO) >= 0, "snackPile.price.negative");
    Preconditions.checkArgument(price.remainder(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) == 0,
        "snackPile.price.cent");

    this.snack = snack;
    this.quantity = quantity;
    this.price = price;
  }

  public SnackPile dispatchOneSnack() {
    return new SnackPile(snack, quantity - 1, price);
  }

}
