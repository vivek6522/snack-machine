package cc.vivp.snackmachine.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.vivp.snackmachine.domain.logic.SnackMachine;
import cc.vivp.snackmachine.resource.mapper.MoneyMapper;
import cc.vivp.snackmachine.resource.request.CheckoutRequest;
import cc.vivp.snackmachine.resource.request.InsertMoneyRequest;
import cc.vivp.snackmachine.service.SnackMachineService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "snack-machines")
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class SnackMachineResource {

  private final SnackMachineService snackMachineService;

  @GetMapping("{id}")
  public Mono<SnackMachine> fetchById(@PathVariable String id) {
    return snackMachineService.fetchSnackMachine(id);
  }

  @PostMapping("{id}/transaction")
  public Mono<SnackMachine> insertMoney(@PathVariable String id, @RequestBody InsertMoneyRequest request) {
    return snackMachineService.insertMoney(id, MoneyMapper.mapToMoneyValueObject(request.getType()));
  }

  @DeleteMapping("{id}/transaction")
  public Mono<SnackMachine> returnMoney(@PathVariable String id) {
    return snackMachineService.returnMoney(id);
  }

  @PostMapping("{id}/checkout")
  public Mono<SnackMachine> checkout(@PathVariable String id, @RequestBody CheckoutRequest request) {
    return snackMachineService.checkout(id, request.getPosition());
  }

}
