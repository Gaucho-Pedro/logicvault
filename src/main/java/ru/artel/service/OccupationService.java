package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.entity.Occupation;
import ru.artel.repository.OccupationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OccupationService {

    OccupationRepository occupationRepository;

    public List<Occupation> findAll() {
        return occupationRepository.findAll();
    }

    public Occupation findByName(String name) {
        return occupationRepository.findByDescription(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Occupation " + name + " not found"));
    }

    public Set<Occupation> findByNames(List<String> names) {
        return names.stream().map(this::findByName).collect(Collectors.toSet());
    }
}
