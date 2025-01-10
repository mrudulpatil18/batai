package msp.batai.batai;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDTO {
    private String description;
    private Long cropId;
    private String paidBy;
    private Long contract_id;
    private int amount;
    private int sharingPercent;

    Transaction converToTransaction(){
        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setSharingPercent(sharingPercent);
        t.setDescription(description);
        t.setPaidBy(paidBy);
        t.setCropID(cropId);
        return t;
    }
}
