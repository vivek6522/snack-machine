package cc.vivp.snackmachine.domain.orm;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnackMachine {
  @Id
  String id = UUID.randomUUID().toString();
  int oneCentCount;
  int tenCentCount;
  int quarterCount;
  int oneDollarCount;
  int fiveDollarCount;
  int twentyDollarCount;
}
