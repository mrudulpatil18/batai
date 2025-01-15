package msp.batai.batai.Dto;

import java.util.List;
import java.util.stream.Collectors; 

import msp.batai.batai.Model.Contract;
import msp.batai.batai.Model.User;

public class ContractMapper {
    public static ContractDTO convertToDTOContract(Contract c) {
        return new ContractDTO(
            c.getContractId(),
            c.getOwner().getUsername(),
            c.getTenant().getUsername(),
            c.getOwnerAccount(),
            c.getOwnerDue(),
            c.getTenantAccount(),
            c.getTenantDue()
        );
    }

    public static Contract convertDTOtoContract(ContractDTO contractDTO, User owner, User tenant){
        Contract c = new Contract();
        c.setContractId(contractDTO.contractId);
        c.setOwner(owner);
        c.setTenant(tenant);
        c.setOwnerAccount(contractDTO.ownerAccount);
        c.setOwnerDue(contractDTO.ownerDue);
        c.setTenantAccount(contractDTO.tenantAccount);
        c.setTenantDue(contractDTO.tenantDue);

        return c;
    }

    public static List<ContractDTO> convertToDTOContractList(List<Contract> l) {
        return l.stream()
                .map(c -> new ContractDTO(
                    c.getContractId(),
                    c.getOwner().getUsername(),
                    c.getTenant().getUsername(),
                    c.getOwnerAccount(),
                    c.getOwnerDue(),
                    c.getTenantAccount(),
                    c.getTenantDue()
                ))
                .collect(Collectors.toList());
    }
}
