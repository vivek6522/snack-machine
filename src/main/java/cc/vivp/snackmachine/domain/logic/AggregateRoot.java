package cc.vivp.snackmachine.domain.logic;

public abstract class AggregateRoot extends Entity {

  public AggregateRoot() {
    super();
  }

  public AggregateRoot(String id) {
    super(id);
  }
}