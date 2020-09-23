package cc.vivp.snackmachine.domain.mapper;

import cc.vivp.snackmachine.domain.logic.Money;
import cc.vivp.snackmachine.domain.logic.SnackMachine;

public class SnackMachineMapper {

  private SnackMachineMapper() {
    // Utility class.
  }

  public static SnackMachine mapToDomainModel(cc.vivp.snackmachine.domain.orm.SnackMachine dbSnackMachine) {
    return new SnackMachine(dbSnackMachine.getId(),
        new Money(dbSnackMachine.getOneCentCount(), dbSnackMachine.getTenCentCount(), dbSnackMachine.getQuarterCount(),
            dbSnackMachine.getOneDollarCount(), dbSnackMachine.getFiveDollarCount(),
            dbSnackMachine.getTwentyDollarCount()));
  }

  public static cc.vivp.snackmachine.domain.orm.SnackMachine mapToObjectRelationalModel(String id,
      SnackMachine domainSnackMachine) {
    Money moneyInside = domainSnackMachine.getMoneyInside();
    return new cc.vivp.snackmachine.domain.orm.SnackMachine(id, moneyInside.getOneCentCount(),
        moneyInside.getTenCentCount(), moneyInside.getQuarterCount(), moneyInside.getOneDollarCount(),
        moneyInside.getFiveDollarCount(), moneyInside.getTwentyDollarCount());
  }
}
