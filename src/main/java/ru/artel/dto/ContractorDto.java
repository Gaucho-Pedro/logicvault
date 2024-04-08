package ru.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractorDto {

    Long id;
    Long userId;
    LegalPersonDto legalPerson;
    NaturalPersonDto naturalPerson;
}
