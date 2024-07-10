package com.neo.country_finder;

import com.neo.country_finder.exception.CountryNotFoundException;
import com.neo.country_finder.repository.CountryDataRepository;
import com.neo.country_finder.service.SearchService;
import com.neo.country_finder.service.WikiDataService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Search number tests")
class SearchTests {

    @Autowired
    private SearchService searchService;
    @Autowired
    private WikiDataService wikiDataService;
    @Autowired
    private CountryDataRepository repository;
    @Autowired
    private MockMvc mockMvc;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
    private final String path = "/search?number";

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void beforeEach() throws IOException {
        wikiDataService.initData();
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @DisplayName("Check provided data")
    void checkProvidedData() throws CountryNotFoundException {
        assertEquals("Bahamas", searchService.find("1-242-3222-931"));
        assertEquals("United States, Canada", searchService.find("+111_653_847_65"));
        assertEquals("Russia", searchService.find("+7(142)3423412"));
        assertEquals("Kazakhstan", searchService.find("7.711.222.72.31"));
    }

    @Test
    @DisplayName("Check non-exists number")
    void checkNonExistsNumber() {
        assertThrows(CountryNotFoundException.class, () -> searchService.find("0123456789"));
    }

    @Test
    @DisplayName("Check letters in number")
    void checkLettersInNumber() throws Exception {
        mockMvc.perform(get(path + "df012f3456789q")).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Check too small number")
    void checkTooSmallNumber() throws Exception {
        mockMvc.perform(get(path + "123")).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Check too large number")
    void checkTooLargeNumber() throws Exception {
        mockMvc.perform(get(path + "123456789123456789")).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Check null number")
    void checkNullNumber() throws Exception {
        mockMvc.perform(get("/search")).andExpect(status().is4xxClientError());
    }
}
