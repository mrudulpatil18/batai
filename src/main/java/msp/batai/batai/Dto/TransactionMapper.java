package msp.batai.batai.Dto;

import java.util.List;
import java.util.stream.Collectors;

import msp.batai.batai.Model.Transaction;
import msp.batai.batai.Model.User;

public class TransactionMapper {

    public static List<TransactionDTO> convertToDTOTransactions(List<Transaction> l) {
        return l.stream().map(transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(transaction.getAmount());
            dto.setSharingPercent(transaction.getSharingPercent());
            dto.setDescription(transaction.getDescription());
            dto.setPaidBy(transaction.getPaidBy().getUsername());
            dto.setCropId(transaction.getCropID());
            dto.setContract_id(transaction.getContract() != null ? transaction.getContract().getContractId() : null);
            dto.setTransactionType(transaction.getTransactionType());
            dto.setTimeCreated(transaction.getTimeCreated());
            dto.setTimeModified(transaction.getTimeModified());
            return dto;
        }).collect(Collectors.toList());
    }

    public static TransactionDTO convertToDTOTransaction(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();
            dto.setAmount(transaction.getAmount());
            dto.setSharingPercent(transaction.getSharingPercent());
            dto.setDescription(transaction.getDescription());
            dto.setPaidBy(transaction.getPaidBy().getUsername());
            dto.setCropId(transaction.getCropID());
            dto.setTransactionType(transaction.getTransactionType());
            dto.setContract_id(transaction.getContract() != null ? transaction.getContract().getContractId() : null);
            dto.setTimeCreated(transaction.getTimeCreated());
            dto.setTimeModified(transaction.getTimeModified());
            return dto;
    }

    public static Transaction convertDTOToTransaction(TransactionDTO transactionDTO, User paidBy){
        Transaction t = new Transaction();
        t.setAmount(transactionDTO.getAmount());
        t.setSharingPercent(transactionDTO.getSharingPercent());
        t.setDescription(transactionDTO.getDescription());
        t.setPaidBy(paidBy);
        t.setCropID(transactionDTO.getCropId());
        t.setTransactionType(transactionDTO.getTransactionType());
        t.setTimeCreated(transactionDTO.getTimeCreated());
        t.setTimeModified(transactionDTO.getTimeModified());
        return t;
    }

}
