package ru.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artel.dto.ContractorDto;
import ru.artel.dto.RegisterDto;
import ru.artel.dto.SignInDto;
import ru.artel.entity.Contractor;
import ru.artel.entity.LegalPerson;
import ru.artel.entity.NaturalPerson;
import ru.artel.service.ContractorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractorController {

    ContractorService contractorService;

    @GetMapping
    public ResponseEntity<List<ContractorDto>> getContractors() {
        return ResponseEntity.ok(mappingUtil.toDtoList(contractorService.findAll(), ContractorDto.class));
    }

    @GetMapping("/{id}")
    public Contractor getContractorById(@PathVariable("id") Long id) {
        return contractorService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Contractor getContractorByUserId(@PathVariable("userId") Long userId) {
        return contractorService.findByUserId(userId);
    }

    @PostMapping("/{id}/legal")
    public ContractorDto setLegalStatus(@PathVariable("id") Long id,
                                        @RequestBody LegalPerson legalPerson) {
        return mappingUtil.toDto(contractorService.setLegalStatus(id, legalPerson), ContractorDto.class);
    }

    @PostMapping("/{id}/natural")
    public ContractorDto setNaturalStatus(@PathVariable("id") Long id,
                                          @Valid @RequestBody NaturalPerson naturalPerson) {
        return mappingUtil.toDto(contractorService.setNaturalStatus(id, naturalPerson), ContractorDto.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ContractorDto> createContractor(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(mappingUtil.toDto(contractorService.create(
                mappingUtil.toEntity(registerDto, Contractor.class)), ContractorDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {
        return contractorService.signIn(signInDto) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        contractorService.delete(id);
    }
}
