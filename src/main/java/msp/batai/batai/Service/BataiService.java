package msp.batai.batai.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import msp.batai.batai.Dto.TransactionDTO;
import msp.batai.batai.Dto.TransactionMapper;
import msp.batai.batai.Enum.TransactionType;
import msp.batai.batai.Model.Balance;
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

    public Balance balance(Contract contract){
        Long ownerAccount = 0L, tenantAccount = 0L;
        Long ownerDue = 0L, tenantDue = 0L;
        for(Transaction t : contract.getTransactions()){
            if(t.getTransactionType() == TransactionType.INCOME){
                if(t.getPaidBy() == contract.getOwner()){
                    ownerAccount += t.getAmount();
                    double due = (t.getAmount() * (100-t.getSharingPercent()))/100.0;
                    ownerDue += Math.round(due);
                    tenantDue -= Math.round(due);
                }else if(t.getPaidBy() == contract.getTenant()){
                    tenantAccount += t.getAmount();
                    double due = (t.getAmount() * (t.getSharingPercent()))/100.0;
                    ownerDue += Math.round(due);
                    tenantDue -= Math.round(due);
                }else{
                    double amtby2 = t.getAmount()/2.0;
                    tenantAccount += Math.round(amtby2);
                    ownerAccount += Math.round(amtby2);
                }
            }else if(t.getTransactionType() == TransactionType.EXPENDITURE){
                if(t.getPaidBy() == contract.getOwner()){
                    ownerAccount -= t.getAmount();
                    double due = (t.getAmount() * (100-t.getSharingPercent()))/100.0;
                    ownerDue -= Math.round(due);
                    tenantDue += Math.round(due);
                }else if(t.getPaidBy() == contract.getTenant()){
                    tenantAccount -= t.getAmount();
                    double due = (t.getAmount() * (t.getSharingPercent()))/100.0;
                    ownerDue -= Math.round(due);
                    tenantDue += Math.round(due);
                }else{
                    double amtby2 = t.getAmount()/2.0;
                    tenantAccount -= Math.round(amtby2);
                    ownerAccount -= Math.round(amtby2);
                }
            }else if(t.getTransactionType() == TransactionType.TRANSFER){
                if(t.getPaidBy() == contract.getOwner()){
                    int amt = t.getAmount();
                    ownerAccount -= amt;
                    tenantAccount += amt;
                    ownerDue -= amt;
                    tenantDue += amt;
                }else if(t.getPaidBy() == contract.getTenant()){
                    int amt = t.getAmount();
                    ownerAccount += amt;
                    tenantAccount -= amt;
                    ownerDue += amt;
                    tenantDue -= amt;
                }
            }
        }
        Balance b = new Balance(ownerAccount, ownerDue, tenantAccount, tenantDue, contract);
        return b;
    }
}