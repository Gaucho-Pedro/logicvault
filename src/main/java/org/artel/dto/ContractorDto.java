package org.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractorDto {

    Long id;
    Long userId;
    LegalPerson legalPerson;
    NaturalPerson naturalPerson;
}
