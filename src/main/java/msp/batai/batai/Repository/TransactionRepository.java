package msp.batai.batai.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import msp.batai.batai.Model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
