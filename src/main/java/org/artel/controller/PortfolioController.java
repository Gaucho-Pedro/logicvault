package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.PortfolioDto;
import org.artel.entity.Portfolio;
import org.artel.service.PortfolioService;
import org.artel.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioController {

    PortfolioService portfolioService;
    MappingUtil mappingUtil;

    @PostMapping("/customer/{id}")
    public ResponseEntity<Portfolio> createPortfolioForCustomerById(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolioForCustomerById(portfolio, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mappingUtil.toDto(portfolioService.findById(id), PortfolioDto.class));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioDto>> getPortfolios() {
        return ResponseEntity.ok(mappingUtil.toDtoList(portfolioService.getPortfolios(), PortfolioDto.class));
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
