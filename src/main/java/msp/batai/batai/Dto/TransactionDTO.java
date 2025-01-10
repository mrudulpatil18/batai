package msp.batai.batai.Dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import msp.batai.batai.Enum.TransactionType;
import msp.batai.batai.Model.Transaction;

@Setter
@Getter
public class TransactionDTO {
    private String description;
    private Long cropId;
    private String paidBy;
    private Long contract_id;
    private int amount;
    private int sharingPercent;
    private TransactionType transactionType;
    private Timestamp timeCreated;
    private Timestamp timeModified;

    public Transaction convertToTransaction(){
        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setSharingPercent(sharingPercent);
        t.setDescription(description);
        t.setPaidBy(paidBy);
        t.setCropID(cropId);
        t.setTransactionType(transactionType);
        t.setTimeCreated(timeCreated);
        t.setTimeModified(timeModified);
        return t;
    }
}
