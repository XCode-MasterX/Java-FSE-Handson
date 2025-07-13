package com.cognizant.springlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(SpringLearnApplication.class, args);
        displayDate();
        displayCountry();
        displayCountries();
    }

    public static void displayDate() throws ParseException {
        LOGGER.info("START - displayDate");
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
        Date date = format.parse("31/12/2018");
        LOGGER.debug("Parsed Date: {}", date);
        LOGGER.info("END - displayDate");
    }

    public static void displayCountry() {
        LOGGER.info("START - displayCountry");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("country", Country.class);
        Country anotherCountry = context.getBean("country", Country.class);
        LOGGER.debug("Country: {}", country);
        LOGGER.info("END - displayCountry");
    }

    public static void displayCountries() {
        LOGGER.info("START - displayCountries");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countryList = context.getBean("countryList", List.class);
        LOGGER.debug("Country List: {}", countryList);
        LOGGER.info("END - displayCountries");
    }
}
