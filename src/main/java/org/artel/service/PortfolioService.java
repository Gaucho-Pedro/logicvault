package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Customer;
import org.artel.entity.Portfolio;
import org.artel.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioService {

    CustomerService customerService;
    PortfolioRepository portfolioRepository;

    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio findById(Long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio with id " + id + " not found"));
    }

    public Portfolio createPortfolioForCustomerById(Portfolio portfolio, Long customerId) {
        Customer customer = customerService.findById(customerId);
        portfolio.setCustomer(customer);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Portfolio newPortfolio, Long id) {
        Portfolio portfolio = findById(id);

        portfolio.setDescription(newPortfolio.getDescription());
        portfolio.setScore1(newPortfolio.getScore1());
        portfolio.setScore2(newPortfolio.getScore2());
        portfolio.setScore3(newPortfolio.getScore3());
        portfolio.setShowreelTags(newPortfolio.getShowreelTags());
        //TODO Выставление софта и типа активности

        return portfolioRepository.save(portfolio);
    }

    public void deletePortfolioById(Long id) {
        portfolioRepository.deleteById(id);
    }
}
