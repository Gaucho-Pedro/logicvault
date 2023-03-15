package org.artel.service;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Contractor;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.repository.ContractorRepository;
import org.artel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractorService {

    private final ContractorRepository contractorRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Contractor> getContractors() {
        return contractorRepository.findAll();
    }

    public List<Contractor> getContractorsWhichAreLegalPerson() {
        return contractorRepository.findByLegalPersonNotNullAndNaturalPersonNull();
    }

    public List<Contractor> getContractorsWhichAreNaturalPerson() {
        return contractorRepository.findByNaturalPersonNotNullAndLegalPersonNull();
    }

    public User createContractor(User user) {
//        Optional<User> byUsernameOrEmail = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        Optional<User> byUsernameOrEmail = userRepository.findByUsername(user.getUsername());
        if (byUsernameOrEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username: " + user.getUsername() + " is already exist");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            Contractor contractor = new Contractor();
            contractor.setUserId(user.getId());
            contractorRepository.save(contractor);
            return user;
        }
    }

    public Contractor registerAsLegalPersonByContractorId(Long id, LegalPerson legalPerson) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow();
        if (contractor.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a natural entity");
        }
        legalPerson.setContractor(contractor);
        contractor.setLegalPerson(legalPerson);
        return contractorRepository.saveAndFlush(contractor);
    }

    public Contractor registerAsNaturalPersonByContractorId(Long id, NaturalPerson naturalPerson) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow();
        if (contractor.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contractor with id: " + id + " is already a legal entity");
        }
        naturalPerson.setContractor(contractor);
        contractor.setNaturalPerson(naturalPerson);
        return contractorRepository.saveAndFlush(contractor);
    }
}
