package cc.vivp.snackmachine.resource.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMoneyRequest {
  MoneyType type;
}
