package ru.artel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioDto {

    Long id;
    Long customerId;
    String description;
    String activityTypeName;
    Integer score1;
    Integer score2;
    Integer score3;
    String showreelTags;
    @JsonProperty("grade")
    String gradeDescription;
    Long paymentPerHour;
    Long paymentPerDay;
    Long paymentPerMonth;
    Long paymentPerProject;
    String currencyAbbreviation;
    Set<SoftwareDto> software;
    Set<OccupationDto> occupation;
    Set<MediaDataDto> mediaData;
}
