package com.avol.spring.batch.service.impl;

import com.avol.spring.batch.domains.Country;
import com.avol.spring.batch.repository.CountryRepository;
import com.avol.spring.batch.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lovababu on 7/3/2015.
 */

@Service
public class CountryServiceImpl  implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public int[] batchInsert(List<Country>  countryList) throws SQLException {
        return countryRepository.batchInsert(countryList);
    }

    @Override
    public int[] batchInsertWithNameJdbcTemplate(List<Country> countryList) throws SQLException {
        return countryRepository.batchInsertWithNamedJdbcTemplate(countryList);
    }

    @Override
    public List<Country> countries() throws SQLException {
        return countryRepository.countries();
    }
}
