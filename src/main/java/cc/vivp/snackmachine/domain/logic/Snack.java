package cc.vivp.snackmachine.domain.logic;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Snack extends Entity {

  private final String name;

  Snack(String name) {
    this(null, name);
  }

  public Snack(String id, String name) {
    super(id);
    this.name = name;
  }

}
