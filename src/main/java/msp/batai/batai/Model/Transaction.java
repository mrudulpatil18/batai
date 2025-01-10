package msp.batai.batai.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import msp.batai.batai.Enum.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    Long transactionId;

    String description;

    @Column(name = "paid_by")
    String paidBy;
    int amount;

    @Column(name = "sharing_percent")
    int sharingPercent;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "contract_id") 
    Contract contract;

    @Column(name = "crop_id")
    Long cropID;
}
