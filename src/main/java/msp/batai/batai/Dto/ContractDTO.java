package msp.batai.batai.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}