package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Portfolio;
import org.artel.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioController {

    PortfolioService portfolioService;

    @PostMapping("/customer/{id}")
    public ResponseEntity<Portfolio> createPortfolioForCustomerById(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolioForCustomerById(portfolio, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(portfolioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(portfolio, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolioById(@PathVariable("id") Long id) {
        portfolioService.deletePortfolioById(id);
        return ResponseEntity.noContent().build();
    }
}
