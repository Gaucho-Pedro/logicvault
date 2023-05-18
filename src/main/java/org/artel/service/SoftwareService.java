package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Software;
import org.artel.repository.SoftwareRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SoftwareService {

    SoftwareRepository softwareRepository;

    public List<Software> findAll() {
        return softwareRepository.findAll();
    }

    public Software findByName(String name) {
        return softwareRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Software " + name + " not found"));
    }

    public Set<Software> findByNames(List<String> names) {
        return names.stream().map(this::findByName).collect(Collectors.toSet());
    }

    public Software create(Software newSoftware) {
        return softwareRepository.save(newSoftware);
    }

    public List<Software> create(List<Software> newSoftware) {
        return softwareRepository.saveAll(newSoftware);
    }
}
