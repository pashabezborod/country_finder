package com.neo.country_finder.exception;

public class CountryNotFoundException extends Exception {

    public CountryNotFoundException() {
        super("Country not found");
    }
}
