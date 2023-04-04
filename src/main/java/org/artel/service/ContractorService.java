package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.SignInDto;
import org.artel.entity.Contractor;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.repository.ContractorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractorService {

    UserService userService;
    ContractorRepository contractorRepository;

    public List<Contractor> getContractors() {
        return contractorRepository.findAll();
    }

    public List<Contractor> getContractorsWhichAreLegalPerson() {
        return contractorRepository.findByLegalPersonNotNullAndNaturalPersonNull();
    }

    public List<Contractor> getContractorsWhichAreNaturalPerson() {
        return contractorRepository.findByNaturalPersonNotNullAndLegalPersonNull();
    }

    public Contractor findById(Long id) {
        return contractorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contractor with id " + id + " not found"));
    }

    public Contractor findByUserId(Long userId) {
        return contractorRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contractor with userId " + userId + " not found"));
    }

    public Contractor createContractor(User newUser) {
        if (userService.findByUsername(newUser.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username: " + newUser.getUsername() + " is already exist");
        }
        var user = userService.addUser(newUser);
        Contractor contractor = new Contractor();
        contractor.setUser(user);
        return contractorRepository.save(contractor);
    }

    public boolean signInContractor(SignInDto signInDto) {
        User byUsername = userService.findByUsername(signInDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + signInDto.getUsername() + " not found"));
        findByUserId(byUsername.getId());
        return userService.signIn(signInDto.getPassword(), byUsername.getPassword());
    }

    public Contractor setLegalStatus(Long id, LegalPerson legalPerson) {
        Contractor contractor = findById(id);
        if (contractor.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a natural person");
        }
        if (contractor.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a legal person");
        }
//        legalPerson.setContractor(contractor);
        contractor.setLegalPerson(legalPerson);
        return contractorRepository.save(contractor);
    }

    public Contractor setNaturalStatus(Long id, NaturalPerson naturalPerson) {
        Contractor contractor = findById(id);
        if (contractor.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a legal person");
        }
        if (contractor.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a natural person");
        }
//        naturalPerson.setContractor(contractor);
        contractor.setNaturalPerson(naturalPerson);
        return contractorRepository.save(contractor);
    }

    public void deleteContractorById(Long id) {
        contractorRepository.deleteById(id);
    }
}
