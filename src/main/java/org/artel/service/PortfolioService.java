package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Occupation;
import org.artel.entity.Portfolio;
import org.artel.entity.Software;
import org.artel.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioService {

    CustomerService customerService;
    CurrencyService currencyService;
    ActivityTypeService activityTypeService;
    GradeService gradeService;
    OccupationService occupationService;
    SoftwareService softwareService;
    PortfolioRepository portfolioRepository;

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Portfolio findById(Long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio with id " + id + " not found"));
    }

    public Portfolio create(Portfolio portfolio) {
        if (portfolio.getCustomer() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer id is required");
        }
        portfolio.setCustomer(customerService.findById(portfolio.getCustomer().getId()));
        portfolio.setActivityType(activityTypeService.findByName(portfolio.getActivityType().getName()));
        portfolio.setCurrency(currencyService.findByAbbreviation(portfolio.getCurrency().getAbbreviation()));
        portfolio.setGrade(gradeService.findByDescription(portfolio.getGrade().getDescription()));
        portfolio.setSoftware(softwareService.findByNames(portfolio.getSoftware().stream().map(Software::getName).collect(Collectors.toList())));
        portfolio.setOccupation(occupationService.findByNames(portfolio.getOccupation().stream().map(Occupation::getDescription).collect(Collectors.toList())));
        return portfolioRepository.save(portfolio);
    }

    public Portfolio update(Portfolio newPortfolio, Long id) {
        Portfolio portfolio;
        try {
            portfolio = findById(id);
            portfolio.setDescription(newPortfolio.getDescription());
            portfolio.setScore1(newPortfolio.getScore1());
            portfolio.setScore2(newPortfolio.getScore2());
            portfolio.setScore3(newPortfolio.getScore3());
            portfolio.setShowreelTags(newPortfolio.getShowreelTags());
            portfolio.setGrade(gradeService.findByDescription(newPortfolio.getGrade().getDescription()));
            portfolio.setPaymentPerHour(newPortfolio.getPaymentPerHour());
            portfolio.setPaymentPerDay(newPortfolio.getPaymentPerDay());
            portfolio.setPaymentPerMonth(newPortfolio.getPaymentPerMonth());
            portfolio.setPaymentPerProject(newPortfolio.getPaymentPerProject());
            portfolio.setCurrency(currencyService.findByAbbreviation(newPortfolio.getCurrency().getAbbreviation()));
            portfolio.setActivityType(activityTypeService.findByName(newPortfolio.getActivityType().getName()));
            portfolio.setSoftware(softwareService.findByNames(newPortfolio.getSoftware().stream().map(Software::getName).collect(Collectors.toList())));
            portfolio.setOccupation(occupationService.findByNames(newPortfolio.getOccupation().stream().map(Occupation::getDescription).collect(Collectors.toList())));
            portfolio.setMediaData(newPortfolio.getMediaData());
            portfolio = portfolioRepository.save(portfolio);
        } catch (ResponseStatusException e) {
            portfolio = create(newPortfolio);
        }
        return portfolio;
    }

    public Portfolio updatePartial(Portfolio newPortfolio, Long id) {

        Portfolio portfolio = findById(id);

        if (newPortfolio.getDescription() != null) {
            portfolio.setDescription(newPortfolio.getDescription());
        }
        if (newPortfolio.getScore1() != null) {
            portfolio.setScore1(newPortfolio.getScore1());
        }
        if (newPortfolio.getScore2() != null) {
            portfolio.setScore2(newPortfolio.getScore2());
        }
        if (newPortfolio.getScore3() != null) {
            portfolio.setScore3(newPortfolio.getScore3());
        }
        if (newPortfolio.getShowreelTags() != null) {
            portfolio.setShowreelTags(newPortfolio.getShowreelTags());
        }
        if (newPortfolio.getGrade() != null) {
            portfolio.setGrade(gradeService.findByDescription(newPortfolio.getGrade().getDescription()));
        }
        if (newPortfolio.getPaymentPerHour() != null) {
            portfolio.setPaymentPerHour(newPortfolio.getPaymentPerHour());
        }
        if (newPortfolio.getPaymentPerDay() != null) {
            portfolio.setPaymentPerDay(newPortfolio.getPaymentPerDay());
        }
        if (newPortfolio.getPaymentPerMonth() != null) {
            portfolio.setPaymentPerMonth(newPortfolio.getPaymentPerMonth());
        }
        if (newPortfolio.getPaymentPerProject() != null) {
            portfolio.setPaymentPerProject(newPortfolio.getPaymentPerProject());
        }
        if (newPortfolio.getCurrency() != null) {
            portfolio.setCurrency(currencyService.findByAbbreviation(newPortfolio.getCurrency().getAbbreviation()));
        }
        if (newPortfolio.getActivityType() != null) {
            portfolio.setActivityType(activityTypeService.findByName(newPortfolio.getActivityType().getName()));
        }
        if (newPortfolio.getSoftware() != null) {
            portfolio.setSoftware(softwareService.findByNames(newPortfolio.getSoftware().stream().map(Software::getName).collect(Collectors.toList())));
        }
        if (newPortfolio.getOccupation() != null) {
            portfolio.setOccupation(occupationService.findByNames(newPortfolio.getOccupation().stream().map(Occupation::getDescription).collect(Collectors.toList())));
        }
        if (newPortfolio.getMediaData() != null) {
            portfolio.setMediaData(newPortfolio.getMediaData());
        }
        return portfolioRepository.save(portfolio);
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }
}
