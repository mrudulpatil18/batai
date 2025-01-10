package msp.batai.batai.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import msp.batai.batai.Dto.TransactionDTO;
import msp.batai.batai.Dto.TransactionMapper;
import msp.batai.batai.Model.Contract;
import msp.batai.batai.Model.Transaction;
import msp.batai.batai.Repository.ContractRepository;
import msp.batai.batai.Repository.TransactionRepository;

@Service
public class BataiService {
    private final TransactionRepository transactionRepository;
    private final ContractRepository contractRepository;

    public BataiService(TransactionRepository transactionRepository,
            ContractRepository contractRepository) {
        this.transactionRepository = transactionRepository;
        this.contractRepository = contractRepository;
    }

    // Transaction methods
    public List<TransactionDTO> findAllTransactions() {
        return TransactionMapper.convertToDTOTransactions(transactionRepository.findAll());
    }

    public Optional<TransactionDTO> findTransactionById(Long id) {
        return transactionRepository.findById(id).map(TransactionMapper::convertToDTOTransaction);
    }

    public TransactionDTO saveTransaction(Transaction transaction, Long contract_id) {
        Contract c = contractRepository.findById(contract_id).get();
        transaction.setContract(c);
        return TransactionMapper.convertToDTOTransaction(transactionRepository.save(transaction));
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    // Contract methods
    public List<Contract> findAllContracts() {
        return contractRepository.findAll();
    }

    public Optional<Contract> findContractById(Long id) {
        return contractRepository.findById(id);
    }

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }
}