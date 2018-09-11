package org.maestro.results.dao;

import org.maestro.results.exceptions.DataNotFoundException;
import org.maestro.common.ConfigurationWrapper;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public abstract class AbstractDao extends NamedParameterJdbcDaoSupport {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    protected JdbcTemplate jdbcTemplate = null;
    protected static BasicDataSource ds;

    protected AbstractDao() {
        super();

        if (ds == null) {
            synchronized (this) {
                if (ds == null) {
                    ds = new BasicDataSource();

                    AbstractConfiguration config = ConfigurationWrapper.getConfig();
                    ds.setDriverClassName("org.mariadb.jdbc.Driver");

                    final String url = config.getString("datasource.url");
                    ds.setUrl(url);

                    final String username = config.getString("datasource.username");
                    ds.setUsername(username);

                    final String password = config.getString("datasource.password");
                    ds.setPassword(password);

                    ds.setInitialSize(2);

                    logger.info("Created a data source with initial size of {} and max size of {}", ds.getInitialSize(),
                            ds.getMaxTotal());

                    jdbcTemplate = new JdbcTemplate(ds);
                    jdbcTemplate.update("select 1 from dual");
                }
            }
        }
        else {
            jdbcTemplate = new JdbcTemplate(ds);
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
