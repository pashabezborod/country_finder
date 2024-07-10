package com.neo.country_finder;

import com.neo.country_finder.service.WikiDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CountryFinderApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CountryFinderApplication.class, args)
                .getBean(WikiDataService.class)
                .initData();
    }
}
