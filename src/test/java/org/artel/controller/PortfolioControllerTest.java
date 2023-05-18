package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.MediaDataDto;
import org.artel.dto.OccupationDto;
import org.artel.dto.PortfolioDto;
import org.artel.dto.SoftwareDto;
import org.artel.entity.*;
import org.artel.repository.*;
import org.artel.util.MappingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    MappingUtil mappingUtil;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ActivityTypeRepository activityTypeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    SoftwareRepository softwareRepository;
    @Autowired
    OccupationRepository occupationRepository;
    @Autowired
    MediaDataRepository mediaDataRepository;

    @BeforeEach
    void cleanDb() {
        portfolioRepository.deleteAll();
        customerRepository.deleteAll();
        activityTypeRepository.deleteAll();
        currencyRepository.deleteAll();
        gradeRepository.deleteAll();
        softwareRepository.deleteAll();
        occupationRepository.deleteAll();
        mediaDataRepository.deleteAll();
    }

    @Test
    void getPortfoliosTest() {

        List<PortfolioDto> responseBody = webTestClient.get().uri("/portfolio")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PortfolioDto.class)
                .returnResult().getResponseBody();
        Assertions.assertTrue(responseBody.isEmpty());
    }

    @Test
    void createPortfolioWhenCustomerIdIsEmptyTest() {

        PortfolioDto requestBody = new PortfolioDto();
        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message").isEqualTo("Customer id is required");
    }

    @Test
    void createPortfolioWhenCustomerIdNotExistInDBTest() {

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(0L);

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Customer with id 0 not found");
    }

    @Test
    void createPortfolioWhenActivityTypeDoesNotExistInDBTest() {

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("ActivityType with name activityTypeName not found");
    }

    @Test
    void createPortfolioWhenCurrencyDoesNotExistInDBTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("ABC");

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Currency ABC not found");
    }

    @Test
    void createPortfolioWhenGradeDoesNotExistInDBTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);


        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setGradeDescription("Master");

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Grade Master not found");
    }

    @Test
    void createPortfolioWhenSoftwareDoesNotExistInDBTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);

        Grade grade = new Grade();
        grade.setDescription("Master");
        gradeRepository.save(grade);

        SoftwareDto softwareDto = new SoftwareDto();
        softwareDto.setName("Figma");
        Set<SoftwareDto> softwareDtos = new HashSet<>();
        softwareDtos.add(softwareDto);

        OccupationDto occupationDto = new OccupationDto();
        occupationDto.setDescription("Full");
        Set<OccupationDto> occupationDtos = new HashSet<>();
        occupationDtos.add(occupationDto);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setGradeDescription("Master");
        requestBody.setSoftware(softwareDtos);
        requestBody.setOccupation(occupationDtos);

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Software Figma not found");
    }

    @Test
    void createPortfolioWhenOccupationDoesNotExistInDBTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);

        Grade grade = new Grade();
        grade.setDescription("Master");
        gradeRepository.save(grade);

        Software software = new Software();
        software.setName("Figma");
        softwareRepository.save(software);
        SoftwareDto softwareDto = mappingUtil.toDto(software, SoftwareDto.class);
        Set<SoftwareDto> softwareDtos = new HashSet<>();
        softwareDtos.add(softwareDto);

        OccupationDto occupationDto = new OccupationDto();
        occupationDto.setDescription("Full");
        Set<OccupationDto> occupationDtos = new HashSet<>();
        occupationDtos.add(occupationDto);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setGradeDescription("Master");
        requestBody.setSoftware(softwareDtos);
        requestBody.setOccupation(occupationDtos);

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo("Occupation Full not found");
    }

    @Test
    void createAndReplacePortfolioTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);

        Grade grade = new Grade();
        grade.setDescription("Master");
        gradeRepository.save(grade);

        Software software = new Software();
        software.setName("Figma");
        softwareRepository.save(software);
        SoftwareDto softwareDto = mappingUtil.toDto(software, SoftwareDto.class);
        Set<SoftwareDto> softwareDtos = new HashSet<>();
        softwareDtos.add(softwareDto);

        Occupation occupation = new Occupation();
        occupation.setDescription("Полный рабочий день");
        occupationRepository.save(occupation);
        OccupationDto occupationDto = mappingUtil.toDto(occupation, OccupationDto.class);
        Set<OccupationDto> occupationDtos = new HashSet<>();
        occupationDtos.add(occupationDto);

        MediaDataDto mediaDataDto = new MediaDataDto();
        mediaDataDto.setUrl("http://localhost:80/photo");
        mediaDataDto.setTags("tag1,tag2,tag3");
        Set<MediaDataDto> mediaDataDtos = new HashSet<>();
        mediaDataDtos.add(mediaDataDto);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setGradeDescription("Master");
        requestBody.setSoftware(softwareDtos);
        requestBody.setOccupation(occupationDtos);
        requestBody.setMediaData(mediaDataDtos);

        webTestClient.post().uri("/portfolio")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.activityTypeName").isNotEmpty()
                .jsonPath("$.software").isNotEmpty()
                .jsonPath("$.grade").isNotEmpty()
                .jsonPath("$.occupation").isNotEmpty()
                .jsonPath("$.mediaData").isNotEmpty();

        Assertions.assertEquals(mediaDataRepository.findAll().size(), 1);
        Assertions.assertEquals(mediaDataRepository.findAll().get(0).getTags(), mediaDataDto.getTags());

        Assertions.assertEquals(portfolioRepository.findAll().size(), 1);

        requestBody.setDescription("Test Portfolio");

        webTestClient.put().uri("/portfolio/{id}", portfolioRepository.findAll().get(0).getId())
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.description").isEqualTo("Test Portfolio")
                .jsonPath("$.activityTypeName").isNotEmpty()
                .jsonPath("$.software").isNotEmpty()
                .jsonPath("$.grade").isNotEmpty()
                .jsonPath("$.occupation").isNotEmpty()
                .jsonPath("$.mediaData").isNotEmpty();
        Assertions.assertEquals(portfolioRepository.findAll().size(), 1);
    }

    @Test
    void createPortfolioWhenPutMethodTest() {

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);

        Grade grade = new Grade();
        grade.setDescription("Master");
        gradeRepository.save(grade);

        Software software = new Software();
        software.setName("Figma");
        softwareRepository.save(software);
        SoftwareDto softwareDto = mappingUtil.toDto(software, SoftwareDto.class);
        Set<SoftwareDto> softwareDtos = new HashSet<>();
        softwareDtos.add(softwareDto);

        Occupation occupation = new Occupation();
        occupation.setDescription("Полный рабочий день");
        occupationRepository.save(occupation);
        OccupationDto occupationDto = mappingUtil.toDto(occupation, OccupationDto.class);
        Set<OccupationDto> occupationDtos = new HashSet<>();
        occupationDtos.add(occupationDto);

        MediaDataDto mediaDataDto = new MediaDataDto();
        mediaDataDto.setUrl("http://localhost:80/photo");
        mediaDataDto.setTags("tag1,tag2,tag3");
        Set<MediaDataDto> mediaDataDtos = new HashSet<>();
        mediaDataDtos.add(mediaDataDto);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setGradeDescription("Master");
        requestBody.setSoftware(softwareDtos);
        requestBody.setOccupation(occupationDtos);
        requestBody.setMediaData(mediaDataDtos);

        webTestClient.put().uri("/portfolio/{id}", 1)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.activityTypeName").isNotEmpty()
                .jsonPath("$.software").isNotEmpty()
                .jsonPath("$.grade").isNotEmpty()
                .jsonPath("$.occupation").isNotEmpty()
                .jsonPath("$.mediaData").isNotEmpty();
        Assertions.assertEquals(portfolioRepository.findAll().size(), 1);
    }

    @Test
    void updatePartialPortfolioTest() {

        Long portfolioId = portfolioRepository.save(new Portfolio()).getId();

        ActivityType activityType = new ActivityType();
        activityType.setName("activityTypeName");
        activityTypeRepository.save(activityType);

        Currency currency = new Currency();
        currency.setAbbreviation("RUB");
        currencyRepository.save(currency);

        Grade grade = new Grade();
        grade.setDescription("Master");
        gradeRepository.save(grade);

        Software software = new Software();
        software.setName("Figma");
        softwareRepository.save(software);
        SoftwareDto softwareDto = mappingUtil.toDto(software, SoftwareDto.class);
        Set<SoftwareDto> softwareDtos = new HashSet<>();
        softwareDtos.add(softwareDto);

        Occupation occupation = new Occupation();
        occupation.setDescription("Полный рабочий день");
        occupationRepository.save(occupation);
        OccupationDto occupationDto = mappingUtil.toDto(occupation, OccupationDto.class);
        Set<OccupationDto> occupationDtos = new HashSet<>();
        occupationDtos.add(occupationDto);

        MediaDataDto mediaDataDto = new MediaDataDto();
        mediaDataDto.setUrl("http://localhost:80/photo");
        mediaDataDto.setTags("tag1,tag2,tag3");
        Set<MediaDataDto> mediaDataDtos = new HashSet<>();
        mediaDataDtos.add(mediaDataDto);

        PortfolioDto requestBody = new PortfolioDto();
        requestBody.setCustomerId(customerRepository.save(new Customer()).getId());
        requestBody.setDescription("Description");
        requestBody.setActivityTypeName("activityTypeName");
        requestBody.setCurrencyAbbreviation("RUB");
        requestBody.setScore1(1);
        requestBody.setScore3(2);
        requestBody.setScore3(3);
        requestBody.setShowreelTags("tag1,tag2,tag3");
        requestBody.setPaymentPerHour(1L);
        requestBody.setPaymentPerDay(2L);
        requestBody.setPaymentPerMonth(3L);
        requestBody.setPaymentPerProject(4L);
        requestBody.setGradeDescription("Master");
        requestBody.setSoftware(softwareDtos);
        requestBody.setOccupation(occupationDtos);
        requestBody.setMediaData(mediaDataDtos);

        webTestClient.patch().uri("/portfolio/{id}", portfolioId)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.activityTypeName").isNotEmpty()
                .jsonPath("$.software").isNotEmpty()
                .jsonPath("$.grade").isNotEmpty()
                .jsonPath("$.occupation").isNotEmpty()
                .jsonPath("$.mediaData").isNotEmpty();
        Assertions.assertEquals(portfolioRepository.findAll().size(), 1);
    }

    @Test
    void deletePortfolioTest() {
        Long id = portfolioRepository.save(new Portfolio()).getId();
        Assertions.assertEquals(portfolioRepository.findAll().size(), 1);
        webTestClient.delete().uri("/portfolio/{id}", id)
                .exchange()
                .expectStatus().isNoContent();
        Assertions.assertEquals(portfolioRepository.findAll().size(), 0);
    }
}