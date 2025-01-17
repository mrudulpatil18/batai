package msp.batai.batai.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import msp.batai.batai.Model.Contract;
import msp.batai.batai.Model.Transaction;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByContract(Contract contract);
    
}
