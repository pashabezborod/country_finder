package com.neo.country_finder.controller;

import com.neo.country_finder.exception.CountryNotFoundException;
import com.neo.country_finder.service.SearchService;
import com.neo.country_finder.validation.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
class SearchController {

    private final SearchService searchServiceImpl;

    @GetMapping
    @CrossOrigin(originPatterns = {"http://localhost:[*]"})
    String search(@RequestParam @Phone String number) throws CountryNotFoundException {
        return searchServiceImpl.find(number);
    }
}
