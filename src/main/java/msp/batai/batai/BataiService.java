package msp.batai.batai;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BataiService {
    private TransactionRepository tr;

    BataiService(TransactionRepository tr){
        this.tr = tr;
    }

    public List<Transaction> findAll(){
        return tr.findAll();
    }

    public Optional<Transaction> findById(Long id){
        return tr.findById(id);
    }

    public Transaction save(Transaction t){
        return tr.save(t);
    }

    public void deleteById(Long t){
        tr.deleteById(t);
    }
}