package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.ActivityType;
import org.artel.repository.ActivityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ActivityTypeService {

    ActivityTypeRepository activityTypeRepository;

    public List<ActivityType> getActivityTypes() {
        return activityTypeRepository.findAll();
    }
}
