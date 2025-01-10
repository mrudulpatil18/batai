package msp.batai.batai;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/transactions")
public class BataiController {
    
    private final BataiService bs;

    BataiController(BataiService bs){
        this.bs = bs;
    }

    @GetMapping
    public List<Transaction> findAll() {
        return bs.findAll(); 
    }

    @GetMapping("/{id}")
    public Optional<Transaction> findById(@PathVariable Long id){
        return bs.findById(id);
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Transaction t) {
        try {
            return ResponseEntity.ok(bs.save(t));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // @DeleteMapping("/{id}")
    // public void deleteById(@PathVariable Long id) {
    //     bs.deleteById(id);
    // }
    


}
