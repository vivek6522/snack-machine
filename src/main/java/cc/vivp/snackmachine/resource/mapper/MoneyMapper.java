package cc.vivp.snackmachine.resource.mapper;

import static cc.vivp.snackmachine.domain.logic.Money.*;

import java.util.EnumMap;
import java.util.Map;

import cc.vivp.snackmachine.domain.logic.Money;
import cc.vivp.snackmachine.resource.request.MoneyType;

public class MoneyMapper {

  public static final Map<MoneyType, Money> MONEY_MAP = new EnumMap<>(MoneyType.class);
  static {
    MONEY_MAP.put(MoneyType.ONE_CENT, cent());
    MONEY_MAP.put(MoneyType.TEN_CENT, tenCent());
    MONEY_MAP.put(MoneyType.QUARTER, quarter());
    MONEY_MAP.put(MoneyType.DOLLAR, dollar());
    MONEY_MAP.put(MoneyType.FIVE_DOLLAR, fiveDollar());
    MONEY_MAP.put(MoneyType.TWENTY_DOLLAR, twentyDollar());
  }

  public static Money mapToMoneyValueObject(MoneyType type) {
    return MONEY_MAP.get(type);
  }

  private MoneyMapper() {
    // Utility class.
  }
}
