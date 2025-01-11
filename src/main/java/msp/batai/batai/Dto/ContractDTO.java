package msp.batai.batai.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msp.batai.batai.Model.Contract;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO{
    Long contractId;
    String owner;
    String tenant;
    Long ownerAccount = 0L;
    Long ownerDue = 0L;
    Long tenantAccount = 0L;
    Long tenantDue = 0L;

    public Contract convertToContract(){
        Contract c = new Contract();
        c.setContractId(contractId);
        c.setOwner(owner);
        c.setTenant(tenant);
        c.setOwnerAccount(ownerAccount);
        c.setOwnerDue(ownerDue);
        c.setTenantAccount(tenantAccount);
        c.setTenantDue(tenantDue);

        return c;
    }
}