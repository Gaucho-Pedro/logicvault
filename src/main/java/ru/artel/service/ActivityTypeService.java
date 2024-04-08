package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.entity.ActivityType;
import ru.artel.repository.ActivityTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ActivityTypeService {

    ActivityTypeRepository activityTypeRepository;

    public List<ActivityType> findAll() {
        return activityTypeRepository.findAll();
    }

    public ActivityType findByName(String name) {
        return activityTypeRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivityType with name " + name + " not found"));
    }
}
