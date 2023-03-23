package org.artel.dto;

import lombok.Data;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;

@Data
public class ContractorDto {

    private Long id;
    private Long userId;
    private LegalPerson legalPerson;
    private NaturalPerson naturalPerson;
}
