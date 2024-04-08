package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.entity.Grade;
import ru.artel.repository.GradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GradeService {

    GradeRepository gradeRepository;

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade findByDescription(String description) {
        return gradeRepository.findByDescription(description).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade " + description + " not found"));
    }
}
