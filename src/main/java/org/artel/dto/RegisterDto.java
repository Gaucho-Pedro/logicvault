package org.artel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {

    @JsonProperty("username")
    String userUsername;
    @JsonProperty("password")
    String userPassword;
    @JsonProperty("email")
    String userEmail;
    @JsonProperty("phoneNumber")
    String userPhoneNumber;
    LegalPersonDto legalPerson;
    NaturalPersonDto naturalPerson;
}
