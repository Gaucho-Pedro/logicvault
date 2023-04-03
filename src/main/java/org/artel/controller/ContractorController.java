package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.ContractorDto;
import org.artel.entity.Contractor;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.ContractorService;
import org.artel.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractorController {

    ContractorService contractorService;
    MappingUtil mappingUtil;

    @GetMapping
    public ResponseEntity<List<ContractorDto>> getContractors() {
        return ResponseEntity.ok(mappingUtil.toDtoList(contractorService.getContractors(), ContractorDto.class));
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
                                        @Valid @RequestBody LegalPerson legalPerson) {
        return mappingUtil.toDto(contractorService.setLegalStatus(id, legalPerson), ContractorDto.class);
    }

    @PostMapping("/{id}/natural")
    public ContractorDto setNaturalStatus(@PathVariable("id") Long id,
                                          @Valid @RequestBody NaturalPerson naturalPerson) {
        return mappingUtil.toDto(contractorService.setNaturalStatus(id, naturalPerson), ContractorDto.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ContractorDto> createContractor(@Valid @RequestBody User user) {
        return new ResponseEntity<>(mappingUtil.toDto(contractorService.createContractor(user), ContractorDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return contractorService.signInContractor(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        contractorService.deleteContractorById(id);
    }
}
