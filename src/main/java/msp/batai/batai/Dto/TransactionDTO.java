package msp.batai.batai.Dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import msp.batai.batai.Enum.TransactionType;

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
}
