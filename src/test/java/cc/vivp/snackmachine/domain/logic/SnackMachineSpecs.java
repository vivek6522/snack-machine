package cc.vivp.snackmachine.domain.logic;

import static cc.vivp.snackmachine.domain.logic.Money.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Snack machine specification")
public class SnackMachineSpecs {

  private static final String ID = "9371a2dc-f5b4-47b6-b7bd-b920d9a49d4b";
  
  @Test
  @DisplayName("should empty money in transaction when money is returned")
  void givenMoneyInTransactionWhenMoneyIsReturnedThenEmptyMoneyInTransaction() {

    SnackMachine snackMachine = new SnackMachine(ID);
    snackMachine.insertMoney(dollar());

    snackMachine.returnMoney();

    assertEquals(zero(), snackMachine.getMoneyInTransaction());
  }

  @Test
  @DisplayName("should add money in transaction when money is inserted")
  void givenSnackMachineWhenMoneyIsInsertedThenMoneyInTransactionIsUpdated() {

    SnackMachine snackMachine = new SnackMachine(ID);

    snackMachine.insertMoney(dollar());
    snackMachine.insertMoney(cent());

    Money expectedMoneyInTransaction = new Money(1, 0, 0, 1, 0, 0);
    assertEquals(expectedMoneyInTransaction, snackMachine.getMoneyInTransaction());
  }

  @Test
  @DisplayName("should not insert more than one coin or note at a time")
  void givenSnackMachineWhenMultipleCoinsOrNotesInsertedThenFail() {
    SnackMachine snackMachine = new SnackMachine(ID);

    Money multipleCoins = new Money(1, 1, 0, 0, 0, 0);

    assertThrows(UnsupportedOperationException.class, () -> snackMachine.insertMoney(multipleCoins));
  }

  @Test
  @DisplayName("should add to money inside when snack is bought")
  void givenMoneyInTransactionWhenSnackIsBoughtThenMoneyInsideIsUpdated() {
    SnackMachine snackMachine = new SnackMachine(ID);
    snackMachine.insertMoney(dollar());

    snackMachine.buySnack();

    assertEquals(zero(), snackMachine.getMoneyInTransaction());
    assertEquals(dollar(), snackMachine.getMoneyInside());
  }
}
