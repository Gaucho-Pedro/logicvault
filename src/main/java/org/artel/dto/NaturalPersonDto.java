package org.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NaturalPersonDto {

    Long id;
    String fullName;
    String citizenship;
}
