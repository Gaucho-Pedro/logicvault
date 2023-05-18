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

    @PostMapping/*("/customer/{id}")*/
    public ResponseEntity<PortfolioDto> createPortfolio(/*@PathVariable("id") Long id,*/ @RequestBody PortfolioDto portfolio) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mappingUtil.toDto(portfolioService.create(
                        mappingUtil.toEntity(portfolio, Portfolio.class)), PortfolioDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mappingUtil.toDto(portfolioService.findById(id), PortfolioDto.class));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioDto>> getPortfolios() {
        return ResponseEntity.ok(
                mappingUtil.toDtoList(portfolioService.findAll(), PortfolioDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDto> updateOrCreatePortfolio(@RequestBody PortfolioDto portfolio, @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                mappingUtil.toDto(portfolioService.update(mappingUtil.toEntity(portfolio, Portfolio.class), id), PortfolioDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PortfolioDto> updatePortfolio(@RequestBody PortfolioDto portfolio, @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                mappingUtil.toDto(portfolioService.updatePartial(mappingUtil.toEntity(portfolio, Portfolio.class), id), PortfolioDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolioById(@PathVariable("id") Long id) {
        portfolioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
