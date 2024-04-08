package ru.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    Long id;
    Long contractorId;
    String description;
    String activityTypeName;
    LocalDate targetDate;
}
