package com.neo.country_finder.service;

import com.neo.country_finder.exception.CountryNotFoundException;
import jakarta.validation.Valid;

public interface SearchService {

    String find(@Valid String number) throws CountryNotFoundException;
}
