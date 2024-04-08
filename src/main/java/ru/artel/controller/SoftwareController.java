package ru.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artel.dto.SoftwareDto;
import ru.artel.entity.Software;
import ru.artel.service.SoftwareService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/software")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SoftwareController {
    SoftwareService softwareService;
    MappingUtil mappingUtil;

    @GetMapping
    public ResponseEntity<List<SoftwareDto>> getSoftware() {
        return ResponseEntity.ok(
                mappingUtil.toDtoList(softwareService.findAll(), SoftwareDto.class));
    }

    @PostMapping
    public ResponseEntity<SoftwareDto> addSoftware(@RequestBody SoftwareDto software) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mappingUtil.toDto(softwareService.create(
                        mappingUtil.toEntity(software, Software.class)), SoftwareDto.class));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SoftwareDto>> addSoftware(@RequestBody List<SoftwareDto> software) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mappingUtil.toDtoList(softwareService.create(
                        mappingUtil.toEntityList(software, Software.class)), SoftwareDto.class));
    }
}
