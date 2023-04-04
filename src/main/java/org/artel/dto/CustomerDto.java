package org.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {

    Long id;
    Long userId;
    LegalPersonDto legalPerson;
    NaturalPersonDto naturalPerson;
    Set<PortfolioDto> portfolios;
}
