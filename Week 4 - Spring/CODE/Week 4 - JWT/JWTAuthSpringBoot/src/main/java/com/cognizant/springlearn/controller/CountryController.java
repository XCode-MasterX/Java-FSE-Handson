package com.cognizant.springlearn.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;

@RestController
public class CountryController {

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return Arrays.asList(
            new Country("US", "United States"),
            new Country("IN", "India"),
            new Country("JP", "Japan")
        );
    }
}

