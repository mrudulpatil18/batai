package msp.batai.batai.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import msp.batai.batai.Model.Contract;
import msp.batai.batai.Model.User;

public interface ContractRepository extends JpaRepository<Contract, Long>{
    
    @Query("SELECT c FROM Contract c WHERE c.owner = :u OR c.tenant = :u")
    List<Contract> findByUser(@Param("u") User u);

}
