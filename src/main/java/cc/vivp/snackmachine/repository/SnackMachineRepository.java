package cc.vivp.snackmachine.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import cc.vivp.snackmachine.domain.orm.SnackMachine;

public interface SnackMachineRepository extends ReactiveCrudRepository<SnackMachine, String> {
  
}
