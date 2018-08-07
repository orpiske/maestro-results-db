package org.maestro.results.dao;

import org.maestro.results.dao.exceptions.DataNotFoundException;
import org.maestro.common.ConfigurationWrapper;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDao extends NamedParameterJdbcDaoSupport {
    protected static JdbcTemplate jdbcTemplate = null;

    protected AbstractDao() {
        super();

        if (jdbcTemplate == null) {
            AbstractConfiguration config = ConfigurationWrapper.getConfig();
            SimpleDriverDataSource ds = new SimpleDriverDataSource();

            ds.setDriverClass(org.mariadb.jdbc.Driver.class);

            final String url = config.getString("datasource.url");
            ds.setUrl(url);

            final String username = config.getString("datasource.username");
            ds.setUsername(username);

            final String password = config.getString("datasource.password");
            ds.setPassword(password);

            try {
                Connection conn = ds.getConnection();

                jdbcTemplate = new JdbcTemplate(ds);
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
        }

        super.setJdbcTemplate(jdbcTemplate);
    }

    protected <T, Y> T runQuery(String query, RowMapper<T> rowMapper, Y id)
            throws DataNotFoundException {
        T ret = null;

        try {
            ret = jdbcTemplate.queryForObject(query, rowMapper, id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("A record with ID " + id + " was not found in the DB", e);
        }

        return ret;
    }

    protected void runUpdate(final String query, Object o) {
        SqlParameterSource beanParameters = new BeanPropertySqlParameterSource(o);

        getNamedParameterJdbcTemplate().update(query, beanParameters);
    }

    protected int runUpdate(final String query, Object...args) {
        return jdbcTemplate.update(query, args);
    }

    protected void runEmptyInsert(final String query, Object o) {
        SqlParameterSource beanParameters = new BeanPropertySqlParameterSource(o);

        getNamedParameterJdbcTemplate().update(query, beanParameters);
    }


    protected int runInsert(final String query, Object o) {
        SqlParameterSource beanParameters = new BeanPropertySqlParameterSource(o);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getNamedParameterJdbcTemplate().update(query, beanParameters, keyHolder);
        return keyHolder.getKey().intValue();
    }

}
