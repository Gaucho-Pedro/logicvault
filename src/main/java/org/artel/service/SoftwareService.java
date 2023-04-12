package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Software;
import org.artel.repository.SoftwareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SoftwareService {

    SoftwareRepository softwareRepository;

    public List<Software> getSoftware() {
        return softwareRepository.findAll();
    }
}
