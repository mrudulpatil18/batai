package msp.batai.batai;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static List<TransactionDTO> convertToDTOTransactions(List<Transaction> l) {
        return l.stream().map(transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(transaction.getAmount());
            dto.setSharingPercent(transaction.getSharingPercent());
            dto.setDescription(transaction.getDescription());
            dto.setPaidBy(transaction.getPaidBy());
            dto.setCropId(transaction.getCropID());
            dto.setContract_id(transaction.getContract() != null ? transaction.getContract().getContractId() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    public static TransactionDTO convertToDTOTransaction(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();
            dto.setAmount(transaction.getAmount());
            dto.setSharingPercent(transaction.getSharingPercent());
            dto.setDescription(transaction.getDescription());
            dto.setPaidBy(transaction.getPaidBy());
            dto.setCropId(transaction.getCropID());
            dto.setContract_id(transaction.getContract() != null ? transaction.getContract().getContractId() : null);
            return dto;
    }

}
