package msp.batai.batai.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

    @Column(name = "owner_account")
    Long ownerAccount;
    @Column(name = "owner_due")
    Long ownerDue;
    @Column(name = "tenant_account")
    Long tenantAccount;
    @Column(name = "tenant_due")
    Long tenantDue;

    @ManyToOne
    @JoinColumn(name = "contract_id") 
    @Id
    Contract contract;
}
