package org.artel.controller;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Contractor;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.ContractorService;
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

    @PostMapping("/{id}/legal")
    public Contractor registerAsLegalPersonByContractorId(@PathVariable("id") Long id,
                                                          @Valid @RequestBody LegalPerson legalPerson) {
        return contractorService.registerAsLegalPersonByContractorId(id, legalPerson);
    }

    @PostMapping("/{id}/natural")
    public Contractor registerAsNaturalPersonByContractorId(@PathVariable("id") Long id,
                                                            @Valid @RequestBody NaturalPerson naturalPerson) {
        return contractorService.registerAsNaturalPersonByContractorId(id, naturalPerson);
    }

    @PostMapping("/auth/register")
    public User createContractor(@Valid @RequestBody User user) {
        return contractorService.createContractor(user);
    }
}
