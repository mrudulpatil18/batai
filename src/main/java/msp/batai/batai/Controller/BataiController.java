package msp.batai.batai.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import msp.batai.batai.Service.BataiService;
import msp.batai.batai.Dto.ContractDTO;
import msp.batai.batai.Dto.TransactionDTO;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping
public class BataiController {

    private final BataiService bs;

    BataiController(BataiService bs) {
        this.bs = bs;
    }

    // @GetMapping("/transactions")
    // public List<TransactionDTO> findAllTransactions(Authentication authentication) {
    //     System.out.println(authentication.getName());
    //     return bs.findAllTransactions();
    // }

    @GetMapping("/transactions/{contractId}")
    public ResponseEntity<?> findTransactionByContract(@PathVariable Long contractId, Authentication authentication) {
        try {
            if (bs.findContractById(contractId).get().getOwner().equals(authentication.getName())
                    || bs.findContractById(contractId).get().getTenant().equals(authentication.getName())) {
                return ResponseEntity.ok(Map.of("transactions", bs.findTransactionByContract(contractId), "message",
                        "loaded transactions"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "You are not part of the contract"));

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    // @GetMapping("/transactions/{id}")
    // public Optional<TransactionDTO> findTransactionById(@PathVariable Long id) {
    //     return bs.findTransactionById(id);
    // }

    @PostMapping("/transactions/{contractId}")
    public ResponseEntity<?> create(@RequestBody TransactionDTO t, Authentication authentication,
            @PathVariable Long contractId) {
        try {

            if (bs.findContractById(contractId).get().getOwner().equals(authentication.getName())
                    || bs.findContractById(contractId).get().getTenant().equals(authentication.getName())) {
                return ResponseEntity.ok(Map.of("transaction", bs.saveTransaction(t, contractId), "message", "transaction created succesfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "You are not part of the contract"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // @DeleteMapping("/{id}")
    // public void deleteTransactionById(@PathVariable Long id) {
    //     bs.deleteTransactionById(id);
    // }

    // @GetMapping("/contracts")
    // public List<ContractDTO> findAllContracts() {
    //     return bs.findAllContracts();
    // }

    @GetMapping("/contracts")
    public ResponseEntity<?> findContractByUserId( Authentication authentication){
        String userName = authentication.getName();
        return ResponseEntity.ok(Map.of("contracts",bs.findContractsByUserName(userName)));
    }

    @GetMapping("/contracts/{id}")
    public ResponseEntity<?> findContractById(@PathVariable Long id, Authentication authentication) {
        if (bs.findContractById(id).get().getOwner().equals(authentication.getName())
                    || bs.findContractById(id).get().getTenant().equals(authentication.getName())) {
                return ResponseEntity.ok(Map.of("contract", bs.findContractById(id), "message", "Contract Found succesfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "You are not part of the contract"));
            }
    }

    @PostMapping("/contracts")
    public ResponseEntity<?> create(@RequestBody ContractDTO t, Authentication authentication) {
        try {

            if (t.getOwner().equals(authentication.getName())
                    || t.getTenant().equals(authentication.getName())) {
                return ResponseEntity.ok(Map.of("transaction", bs.saveContract(t), "message", "Contract created succesfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "You are not part of the contract"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/users/{contractId}")
    public ResponseEntity<?> getUser(@PathVariable Long contractId) {
        return ResponseEntity.ok(Map.of("owner", bs.findOwnerByContractId(contractId), "tenant", bs.findTenantByContractId(contractId)));
    }

}
