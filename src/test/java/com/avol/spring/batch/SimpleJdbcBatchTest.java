package com.avol.spring.batch;

import com.avol.spring.batch.config.AppConfig;
import com.avol.spring.batch.domains.Country;
import com.avol.spring.batch.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Lovababu on 7/3/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SimpleJdbcBatchTest {

    @Autowired
    private CountryService countryService;

    @Test
    public void testBatchInsert() throws SQLException {

        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setId(2);
        country.setName("America");
        country.setCode("US");

        countryList.add(country);

        country = new Country();
        country.setId(3);
        country.setName("Australia");
        country.setCode("AU");

        countryList.add(country);

        country = new Country();
        country.setId(4);
        country.setName("London");
        country.setCode("UK");

        countryList.add(country);

        int[] batchReturn = countryService.batchInsert(countryList);
        //batchReturn contains the no of records updated for each iteration.

        //Get and print the records.
        List<Country> countries = countryService.countries();

        assertNotNull(countries);

        System.out.println("*************** BatchInsert Countries List *************");
        System.out.println("ID       | NAME           | CODE     ");
        System.out.println("--------------------------------------");
        countries.stream().forEach(c -> print(c));
    }

    @Test
    public void testBatchInsertWithNamedJdbcTemplate() throws SQLException {

        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setId(5);
        country.setName("Brazil");
        country.setCode("BR");

        countryList.add(country);

        country = new Country();
        country.setId(6);
        country.setName("China");
        country.setCode("CN");

        countryList.add(country);

        int[] batchReturn = countryService.batchInsertWithNameJdbcTemplate(countryList);
        //batchReturn contains the no of records updated for each iteration.

        //Get and print the records.
        List<Country> countries = countryService.countries();

        assertNotNull(countries);

        System.out.println("***************BatchInsertWithNamedJdbcTemplateCountries List *************");
        System.out.println("ID       | NAME           | CODE     ");
        System.out.println("--------------------------------------");
        countries.stream().forEach(c -> print(c));
    }

    private void print(Country country) {
        System.out.println(country.getId() + "    |   "  + country.getName() + "     |    " + country.getCode());
    }

}
