package cc.vivp.snackmachine.domain.logic;

import static cc.vivp.snackmachine.domain.logic.Money.*;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class SnackMachine extends Entity {

  private static final List<Money> POSSIBLE_INSERTIONS = Arrays.asList(cent(), tenCent(), quarter(), dollar(),
      fiveDollar(), twentyDollar());

  Money moneyInside;
  Money moneyInTransaction;

  public SnackMachine(String id) {
    this(id, zero(), zero());
  }

  public SnackMachine(String id, Money moneyInside) {
    this(id, moneyInside, zero());
  }

  public SnackMachine(String id, Money moneyInside, Money moneyInTransaction) {
    super(id);
    this.moneyInside = moneyInside;
    this.moneyInTransaction = moneyInTransaction;
  }

  public void insertMoney(Money money) {
    if (!POSSIBLE_INSERTIONS.contains(money)) {
      throw new UnsupportedOperationException();
    }
    moneyInTransaction = moneyInTransaction.add(money);
  }

  public void returnMoney() {
    moneyInTransaction = zero();
  }

  public void buySnack() {
    moneyInside = moneyInside.add(moneyInTransaction);
    moneyInTransaction = zero();
  }

}
