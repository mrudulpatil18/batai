package msp.batai.batai.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import msp.batai.batai.Service.BataiService;
import msp.batai.batai.Dto.ContractDTO;
import msp.batai.batai.Dto.TransactionDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
public class BataiController {
    
    private final BataiService bs;

    BataiController(BataiService bs){
        this.bs = bs;
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> findAllTransactions() {
        return bs.findAllTransactions(); 
    }

    @GetMapping("/transactions/{id}")
    public Optional<TransactionDTO> findTransactionById(@PathVariable Long id){
        return bs.findTransactionById(id);
    }
    
    @PostMapping("/transactions")
    public ResponseEntity<?> create(@RequestBody TransactionDTO t) {
        try {
            return ResponseEntity.ok(bs.saveTransaction(t.convertToTransaction(), t.getContract_id()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTransactionById(@PathVariable Long id) {
        bs.deleteTransactionById(id);
    }
    
    @GetMapping("/contracts")
    public List<ContractDTO> findAllContracts() {
        return bs.findAllContracts();
    }

    @GetMapping("/contracts/{id}")
    public Optional<ContractDTO> findContractById(@PathVariable Long id){
        return bs.findContractById(id);
    }
    
    @PostMapping("/contracts")
    public ResponseEntity<?> create(@RequestBody ContractDTO t) {
        try {
            return ResponseEntity.ok(bs.saveContract(t));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
