package ru.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegalPersonDto {

    Long id;
    String name;
    BigInteger inn;
    BigInteger registrationNumber;
    String legalAddress;
}
