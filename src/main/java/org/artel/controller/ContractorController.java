package org.artel.controller;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Contractor;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.ContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    @GetMapping
    public List<Contractor> getContractors() {
        return contractorService.getContractors();
    }

    @GetMapping("/{id}")
    public Contractor getContractorById(@PathVariable("id") Long id) {
        return contractorService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Contractor getContractorByUserId(@PathVariable("userId") Long userId) {
        return contractorService.findByUserId(userId);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> createContractor(@Valid @RequestBody User user) {
        return new ResponseEntity<>(contractorService.createContractor(user), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/legal")
    public Contractor setLegalStatus(@PathVariable("id") Long id,
                                     @Valid @RequestBody LegalPerson legalPerson) {
        return contractorService.setLegalStatus(id, legalPerson);
    }

    @PostMapping("/{id}/natural")
    public Contractor setNaturalStatus(@PathVariable("id") Long id,
                                       @Valid @RequestBody NaturalPerson naturalPerson) {
        return contractorService.setNaturalStatus(id, naturalPerson);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return contractorService.signInContractor(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
