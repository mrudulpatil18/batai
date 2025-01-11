package msp.batai.batai.Dto;

import java.util.List;
import java.util.stream.Collectors; 

import msp.batai.batai.Model.Contract;

public class ContractMapper {
    public static ContractDTO convertToDTOContract(Contract c) {
        return new ContractDTO(
            c.getContractId(),
            c.getOwner(),
            c.getTenant(),
            c.getOwnerAccount(),
            c.getOwnerDue(),
            c.getTenantAccount(),
            c.getTenantDue()
        );
    }

    public static List<ContractDTO> convertToDTOContractList(List<Contract> l) {
        return l.stream()
                .map(c -> new ContractDTO(
                    c.getContractId(),
                    c.getOwner(),
                    c.getTenant(),
                    c.getOwnerAccount(),
                    c.getOwnerDue(),
                    c.getTenantAccount(),
                    c.getTenantDue()
                ))
                .collect(Collectors.toList());
    }
}
