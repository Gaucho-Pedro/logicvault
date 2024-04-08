package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.entity.Currency;
import ru.artel.repository.CurrencyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyService {
    CurrencyRepository currencyRepository;

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findByAbbreviation(String name) {
        return currencyRepository.findByAbbreviation(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + name + " not found"));
    }
}
