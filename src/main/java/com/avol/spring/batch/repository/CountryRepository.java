package com.avol.spring.batch.repository;

import com.avol.spring.batch.domains.Country;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lovababu on 7/3/2015.
 */
public interface CountryRepository {

    int[] batchInsert(List<Country> countryList) throws SQLException;

    int[] batchInsertWithNamedJdbcTemplate(List<Country> countryList) throws SQLException;

    List<Country> countries() throws SQLException;
}
