package msp.batai.batai.Model;

import java.sql.Timestamp;

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

    @ManyToOne
    @JoinColumn(name = "paid_by")
    User paidBy;

    int amount;

    @Column(name = "sharing_percent")
    int sharingPercent;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    TransactionType transactionType;

    @Column(name = "time_created")
    Timestamp timeCreated;

    @Column(name = "time_modified")
    Timestamp timeModified;

    @ManyToOne
    @JoinColumn(name = "contract_id") 
    Contract contract;

    @Column(name = "crop_id")
    Long cropID;
}
