package cc.vivp.snackmachine.domain.logic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
public class Slot extends Entity {

  @Getter
  @Setter
  private SnackPile snackPile;
  @Getter
  private final SnackMachine snackMachine;
  @Getter
  private final int position;

  Slot(SnackPile snackPile, SnackMachine snackMachine, int position) {
    this(null, snackPile, snackMachine, position);
  }

  public Slot(String id, SnackPile snackPile, SnackMachine snackMachine, int position) {
    super(id);
    this.snackPile = snackPile;
    this.snackMachine = snackMachine;
    this.position = position;
  }

}
