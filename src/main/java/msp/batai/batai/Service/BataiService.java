package msp.batai.batai.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import msp.batai.batai.Dto.ContractDTO;
import msp.batai.batai.Dto.ContractMapper;
import msp.batai.batai.Dto.TransactionDTO;
import msp.batai.batai.Dto.TransactionMapper;
import msp.batai.batai.Enum.TransactionType;
import msp.batai.batai.Model.Contract;
import msp.batai.batai.Model.Transaction;
import msp.batai.batai.Model.User;
import msp.batai.batai.Repository.ContractRepository;
import msp.batai.batai.Repository.TransactionRepository;
import msp.batai.batai.Repository.UserRepository;

@Service
public class BataiService {
    private final TransactionRepository transactionRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public BataiService(TransactionRepository transactionRepository,
            ContractRepository contractRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }

    // Transaction methods
    public List<TransactionDTO> findAllTransactions() {
        return TransactionMapper.convertToDTOTransactions(transactionRepository.findAll());
    }

    public Optional<TransactionDTO> findTransactionById(Long id) {
        return transactionRepository.findById(id).map(TransactionMapper::convertToDTOTransaction);
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO, Long contract_id) {
        Contract c = contractRepository.findById(contract_id)
            .orElseThrow(() -> new IllegalArgumentException("Contract with id " + contract_id + " not found"));
        User paidBy = userRepository.findByUsername(transactionDTO.getPaidBy());
        if (paidBy == null) {
            throw new IllegalArgumentException("Payer with username " + transactionDTO.getPaidBy() + " not found");
        }
        Transaction transaction = TransactionMapper.convertDTOToTransaction(transactionDTO, paidBy);
        transaction.setContract(c);
        Long ownerAccount = c.getOwnerAccount(), tenantAccount = c.getTenantAccount();
        Long ownerDue = c.getOwnerDue(), tenantDue = c.getTenantDue();

        if (transaction.getTransactionType().equals(TransactionType.INCOME)) {
            if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                ownerAccount += transaction.getAmount();
                double due = (transaction.getAmount() * (100 - transaction.getSharingPercent())) / 100.0;
                ownerDue += Math.round(due);
                tenantDue -= Math.round(due);
            } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                tenantAccount += transaction.getAmount();
                double due = (transaction.getAmount() * (transaction.getSharingPercent())) / 100.0;
                ownerDue -= Math.round(due);
                tenantDue += Math.round(due);
            } else {
                double amtby2 = transaction.getAmount() / 2.0;
                tenantAccount += Math.round(amtby2);
                ownerAccount += Math.round(amtby2);
            }
        } else if (transaction.getTransactionType().equals(TransactionType.EXPENDITURE)) {
            if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                ownerAccount -= transaction.getAmount();
                double due = (transaction.getAmount() * (100 - transaction.getSharingPercent())) / 100.0;
                ownerDue -= Math.round(due);
                tenantDue += Math.round(due);
            } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                tenantAccount -= transaction.getAmount();
                double due = (transaction.getAmount() * (transaction.getSharingPercent())) / 100.0;
                ownerDue += Math.round(due);
                tenantDue -= Math.round(due);
            } else {
                double amtby2 = transaction.getAmount() / 2.0;
                tenantAccount -= Math.round(amtby2);
                ownerAccount -= Math.round(amtby2);
            }
        } else if (transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
            if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                int amt = transaction.getAmount();
                ownerAccount -= amt;
                tenantAccount += amt;
                ownerDue -= amt;
                tenantDue += amt;
            } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                int amt = transaction.getAmount();
                ownerAccount += amt;
                tenantAccount -= amt;
                ownerDue += amt;
                tenantDue -= amt;
            }
        }
        c.setOwnerAccount(ownerAccount);
        c.setTenantAccount(tenantAccount);
        c.setOwnerDue(ownerDue);
        c.setTenantDue(tenantDue);
        contractRepository.save(c);

        return TransactionMapper.convertToDTOTransaction(transactionRepository.save(transaction));
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }

    // Contract methods
    public List<ContractDTO> findAllContracts() {
        return ContractMapper.convertToDTOContractList(contractRepository.findAll());
    }

    public Optional<ContractDTO> findContractById(Long id) {
        return contractRepository.findById(id).map(ContractMapper::convertToDTOContract);
    }

    public Contract saveContract(ContractDTO contractDto) {
        User owner = userRepository.findByUsername(contractDto.getOwner());
        User tenant = userRepository.findByUsername(contractDto.getTenant());
        if (owner == null) {
            throw new IllegalArgumentException("Owner with username " + contractDto.getOwner() + " not found");
        }
        
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant with username " + contractDto.getTenant() + " not found");
        }
        
        // Validate that owner and tenant are different users
        if (owner.getUsername().equals(tenant.getUsername())) {
            throw new IllegalArgumentException("Owner and tenant cannot be the same user");
        }
        Contract c = ContractMapper.convertDTOtoContract(contractDto, owner, tenant);
        return contractRepository.save(c);
    }

    public Contract recalculateBalance(Contract c) {
        Long ownerAccount = 0L, tenantAccount = 0L;
        Long ownerDue = 0L, tenantDue = 0L;
        for (Transaction transaction : c.getTransactions()) {
            if (transaction.getTransactionType().equals(TransactionType.INCOME)) {
                if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                    ownerAccount += transaction.getAmount();
                    double due = (transaction.getAmount() * (100 - transaction.getSharingPercent())) / 100.0;
                    ownerDue += Math.round(due);
                    tenantDue -= Math.round(due);
                } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                    tenantAccount += transaction.getAmount();
                    double due = (transaction.getAmount() * (transaction.getSharingPercent())) / 100.0;
                    ownerDue -= Math.round(due);
                    tenantDue += Math.round(due);
                } else {
                    double amtby2 = transaction.getAmount() / 2.0;
                    tenantAccount += Math.round(amtby2);
                    ownerAccount += Math.round(amtby2);
                }
            } else if (transaction.getTransactionType().equals(TransactionType.EXPENDITURE)) {
                if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                    ownerAccount -= transaction.getAmount();
                    double due = (transaction.getAmount() * (100 - transaction.getSharingPercent())) / 100.0;
                    ownerDue -= Math.round(due);
                    tenantDue += Math.round(due);
                } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                    tenantAccount -= transaction.getAmount();
                    double due = (transaction.getAmount() * (transaction.getSharingPercent())) / 100.0;
                    ownerDue += Math.round(due);
                    tenantDue -= Math.round(due);
                } else {
                    double amtby2 = transaction.getAmount() / 2.0;
                    tenantAccount -= Math.round(amtby2);
                    ownerAccount -= Math.round(amtby2);
                }
            } else if (transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
                if (transaction.getPaidBy().equals(c.getOwner().getUsername())) {
                    int amt = transaction.getAmount();
                    ownerAccount -= amt;
                    tenantAccount += amt;
                    ownerDue -= amt;
                    tenantDue += amt;
                } else if (transaction.getPaidBy().equals(c.getTenant().getUsername())) {
                    int amt = transaction.getAmount();
                    ownerAccount += amt;
                    tenantAccount -= amt;
                    ownerDue += amt;
                    tenantDue -= amt;
                }
            }
        }
        Contract contract = new Contract(tenantDue, c.getOwner(), c.getTenant(), ownerAccount, ownerDue,
                tenantAccount, tenantDue, c.getTransactions());
        return contract;
    }
}