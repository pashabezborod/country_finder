package com.neo.country_finder.service.impl;

import com.neo.country_finder.exception.CountryNotFoundException;
import com.neo.country_finder.model.db.CountryData;
import com.neo.country_finder.repository.CountryDataRepository;
import com.neo.country_finder.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
class SearchServiceImpl implements SearchService {

    private final CountryDataRepository repository;

    @Override
    @Transactional(readOnly = true)
    public String find(@Valid String number) throws CountryNotFoundException {
        number = number.replaceAll("\\D", "");
        while (!number.isEmpty()) {
            Optional<CountryData> data = repository.findById(number);
            if (data.isPresent()) return data.get().getCountry();
            number = number.substring(0, number.length() - 1);
        }
        throw new CountryNotFoundException();
    }
}
