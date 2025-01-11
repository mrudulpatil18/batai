package msp.batai.batai.Repository;

import org.springframework.data.repository.CrudRepository;

import msp.batai.batai.Model.Balance;
import msp.batai.batai.Model.Contract;

public interface BalanceRepository extends CrudRepository<Balance,Contract> {
    
}
