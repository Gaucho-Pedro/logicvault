package org.artel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioDto {
    Long id;
    Long customerId;
    String description;
    String activityTypeName;
    int score1;
    int score2;
    int score3;
    String showreelTags;
}
