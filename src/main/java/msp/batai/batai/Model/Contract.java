package msp.batai.batai.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    Long contractId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    User tenant;

    @Column(name = "owner_account")
    Long ownerAccount = 0L;
    @Column(name = "owner_due")
    Long ownerDue = 0L;
    @Column(name = "tenant_account")
    Long tenantAccount = 0L;
    @Column(name = "tenant_due")
    Long tenantDue = 0L;

    @OneToMany(mappedBy = "contract")
    private List<Transaction> transactions;
}


//NOTE: DUE POSITIVE => to pay and vice-versa
//NOTE: ACCOUNT POSITIVE => profit ?? (unless i messed up somewhere)