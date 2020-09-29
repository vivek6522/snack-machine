package cc.vivp.snackmachine.domain.logic;

import static cc.vivp.snackmachine.domain.logic.Money.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Snack machine specification")
public class SnackMachineSpecs {

  @Test
  @DisplayName("should empty money in transaction when money is returned")
  void givenMoneyInTransactionWhenMoneyIsReturnedThenEmptyMoneyInTransaction() {

    SnackMachine snackMachine = new SnackMachine();
    snackMachine.insertMoney(dollar());

    snackMachine.returnMoney();

    assertEquals(zero(), snackMachine.getMoneyInTransaction());
  }

  @Test
  @DisplayName("should add money in transaction when money is inserted")
  void givenSnackMachineWhenMoneyIsInsertedThenMoneyInTransactionIsUpdated() {

    SnackMachine snackMachine = new SnackMachine();

    snackMachine.insertMoney(dollar());
    snackMachine.insertMoney(cent());

    Money expectedMoneyInTransaction = new Money(1, 0, 0, 1, 0, 0);
    assertEquals(expectedMoneyInTransaction, snackMachine.getMoneyInTransaction());
  }

  @Test
  @DisplayName("should not insert more than one coin or note at a time")
  void givenSnackMachineWhenMultipleCoinsOrNotesInsertedThenFail() {
    SnackMachine snackMachine = new SnackMachine();

    Money multipleCoins = new Money(1, 1, 0, 0, 0, 0);

    assertThrows(UnsupportedOperationException.class, () -> snackMachine.insertMoney(multipleCoins));
  }

  @Test
  @DisplayName("should add to money inside when snack is bought")
  void givenMoneyInTransactionWhenSnackIsBoughtThenMoneyInsideIsUpdated() {
    SnackMachine snackMachine = new SnackMachine();
    snackMachine.loadSnacks(1, new SnackPile(new Snack("Cookie"), 10, BigDecimal.ONE));
    snackMachine.insertMoney(dollar());

    snackMachine.buySnack(1);

    assertEquals(zero(), snackMachine.getMoneyInTransaction());
    assertEquals(dollar(), snackMachine.getMoneyInside());
  }

  @Test
  @DisplayName("should trade inserted money for a snack when snack is bought")
  void givenMoneyInTransactionWhenSnackIsBoughtThenSnackCountDecreases() {
    SnackMachine snackMachine = new SnackMachine();
    snackMachine.loadSnacks(1, new SnackPile(new Snack("Cookie"), 10, BigDecimal.ONE));
    snackMachine.insertMoney(dollar());
    snackMachine.buySnack(1);

    assertEquals(zero(), snackMachine.getMoneyInTransaction());
    assertEquals(dollar(), snackMachine.getMoneyInside());
    assertEquals(9, snackMachine.getSnackPile(1).getQuantity());
  }

  @Test
  @DisplayName("should not make purchase when there are no snacks")
  void givenNoSnacksWhenSnackIsBoughtThenFail() {
    SnackMachine snackMachine = new SnackMachine();
    assertThrows(IllegalArgumentException.class, () -> snackMachine.buySnack(1));
  }

  @Test
  @DisplayName("should not make purchase when not enough money inserted")
  void givenInsufficientMoneyInsertedWhenSnackIsBoughtThenFail() {
    SnackMachine snackMachine = new SnackMachine();
    snackMachine.loadSnacks(1, new SnackPile(new Snack("Cookie"), 10, BigDecimal.ONE));
    snackMachine.insertMoney(cent());
    assertThrows(IllegalArgumentException.class, () -> snackMachine.buySnack(1));
  }

  @Test
  @DisplayName("should return change after purchase")
  void givenEnoughChangeInsideWhenSnackIsBoughtThenReturnChange() {
    SnackMachine snackMachine = new SnackMachine(new Money(10, 10, 10, 10, 10, 10));
    snackMachine.loadSnacks(1, new SnackPile(new Snack("Cookie"), 10, new BigDecimal("0.25")));
    snackMachine.insertMoney(dollar());

    snackMachine.buySnack(1);

    Money expectedMoneyInside = new Money(10, 10, 7, 11, 10, 10);
    assertEquals(0, expectedMoneyInside.amount().compareTo(snackMachine.getMoneyInside().amount()));
  }

}
