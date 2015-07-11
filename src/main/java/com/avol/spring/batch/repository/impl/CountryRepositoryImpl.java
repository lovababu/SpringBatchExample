package com.avol.spring.batch.repository.impl;

import com.avol.spring.batch.domains.Country;
import com.avol.spring.batch.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lovababu on 7/3/2015.
 */

@Repository
public class CountryRepositoryImpl implements CountryRepository {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CountryRepositoryImpl(DataSource dataSource) throws SQLException {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * This method illustrate the batchUpdate with <code>JdbcTemplate</code>.
     *
     * <code>BatchPreparedStatementSetter</code> callback interface to sets the values to PreparedStatement
     * for each iteration provided by JdbcTemplate.
     *
     * @param countryList
     * @return
     * @throws SQLException
     */

    @Override
    public int[] batchInsert(List<Country> countryList) throws SQLException {
        return this.jdbcTemplate.batchUpdate("INSERT INTO COUNTRY(ID, NAME, CODE) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Country country = countryList.get(i);
                ps.setInt(1, country.getId());
                ps.setString(2, country.getName());
                ps.setString(3, country.getCode());
            }

            @Override
            public int getBatchSize() {
                return countryList.size();
            }
        });
    }

    /**
     * This method illustrate how to use NamedParameterJdbcTemplate to perform batch operation.
     *
     * <code>SqlParameterSourceUtils</code> convert the custom object into <code>SqlParameterSource</code>
     *
     * @param countryList
     * @return
     * @throws SQLException
     */
    @Override
    public int[] batchInsertWithNamedJdbcTemplate(List<Country> countryList) throws SQLException {
        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(countryList.toArray());

        return this.namedParameterJdbcTemplate.batchUpdate("INSERT INTO COUNTRY(ID, NAME, CODE) VALUES (:id, :name, :Code)",
                sqlParameterSources);
    }

    @Override
    public List<Country> countries() throws SQLException {
        return jdbcTemplate.query("SELECT ID as id, NAME as name, CODE as code FROM COUNTRY",
                new BeanPropertyRowMapper(Country.class));
    }
}
