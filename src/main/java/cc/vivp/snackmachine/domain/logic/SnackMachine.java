package cc.vivp.snackmachine.domain.logic;

import static cc.vivp.snackmachine.domain.logic.Money.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class SnackMachine extends AggregateRoot {

  private static final List<Money> POSSIBLE_INSERTIONS = Arrays.asList(cent(), tenCent(), quarter(), dollar(),
      fiveDollar(), twentyDollar());

  @Getter
  @Setter
  private Money moneyInside;
  @Getter
  @Setter
  private Money moneyInTransaction;
  private List<Slot> slots;

  public SnackMachine() {
    this(null, zero(), zero());
  }

  public SnackMachine(String id) {
    this(id, zero(), zero());
  }

  public SnackMachine(Money moneyInside) {
    this(null, moneyInside, zero());
  }

  public SnackMachine(String id, Money moneyInside) {
    this(id, moneyInside, zero());
  }

  public SnackMachine(String id, Money moneyInside, Money moneyInTransaction) {
    this(id, moneyInside, moneyInTransaction, new ArrayList<>());
    slots.add(new Slot(new SnackPile(new Snack(StringUtils.EMPTY), 0, BigDecimal.ZERO), this, 1));
    slots.add(new Slot(new SnackPile(new Snack(StringUtils.EMPTY), 0, BigDecimal.ZERO), this, 2));
    slots.add(new Slot(new SnackPile(new Snack(StringUtils.EMPTY), 0, BigDecimal.ZERO), this, 3));
  }

  public SnackMachine(String id, Money moneyInside, Money moneyInTransaction, List<Slot> slots) {
    super(id);
    this.moneyInside = moneyInside;
    this.moneyInTransaction = moneyInTransaction;
    this.slots = slots;
  }

  public void insertMoney(Money money) {
    if (!POSSIBLE_INSERTIONS.contains(money)) {
      throw new UnsupportedOperationException();
    }
    moneyInTransaction = moneyInTransaction.add(money);
  }

  private void confirmTransaction(Slot slot) {
    slot.setSnackPile(slot.getSnackPile().dispatchOneSnack());
    moneyInside = moneyInside.add(moneyInTransaction);
  }

  public void returnMoney() {
    clearTransaction();
  }

  private void clearTransaction() {
    moneyInTransaction = zero();
  }

  private void returnChange(BigDecimal change) {
    if (change.compareTo(BigDecimal.ZERO) > 0) {
      moneyInside = moneyInside.subtract(moneyInside.allocate(change));
    }
  }

  public void buySnack(int position) {
    Slot slot = getSlot(position);
    Preconditions.checkArgument(slot.getSnackPile().getPrice().compareTo(moneyInTransaction.amount()) <= 0,
        "snackMachine.transaction.insufficientMoney");
    confirmTransaction(slot);
    returnChange(moneyInTransaction.amount().subtract(slot.getSnackPile().getPrice()));
    clearTransaction();
  }

  public void loadSnacks(int position, SnackPile snackPile) {
    getSlot(position).setSnackPile(snackPile);
  }

  private Slot getSlot(int position) {
    return slots.get(mapToIndex(position));
  }

  public SnackPile getSnackPile(int position) {
    return getSlot(position).getSnackPile();
  }

  private static int mapToIndex(int position) {
    return position - 1;
  }
}
