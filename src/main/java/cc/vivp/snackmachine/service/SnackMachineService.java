package cc.vivp.snackmachine.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.vivp.snackmachine.domain.logic.Money;
import cc.vivp.snackmachine.domain.logic.SnackMachine;
import cc.vivp.snackmachine.domain.mapper.SnackMachineMapper;
import cc.vivp.snackmachine.repository.SnackMachineRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class SnackMachineService {

  private final SnackMachineRepository snackMachineRepository;
  private final Map<String, SnackMachine> snackMachineCache;

  public Mono<SnackMachine> fetchSnackMachine(String id) {
    if (snackMachineCache.containsKey(id)) {
      return Mono.just(snackMachineCache.get(id));
    }
    return snackMachineRepository.findById(id).map(SnackMachineMapper::mapToDomainModel)
        .doOnSuccess(snackMachine -> snackMachineCache.put(id, snackMachine));
  }

  public Mono<SnackMachine> insertMoney(String id, Money money) {
    return fetchSnackMachine(id).doOnNext(snackMachine -> snackMachine.insertMoney(money));
  }

  public Mono<SnackMachine> returnMoney(String id) {
    return fetchSnackMachine(id).doOnNext(SnackMachine::returnMoney);
  }

  public Mono<SnackMachine> checkout(String id) {
    return fetchSnackMachine(id).doOnNext(SnackMachine::buySnack);
  }
}
