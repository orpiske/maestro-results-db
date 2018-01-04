package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dao.exceptions.DataNotFoundException;
import net.orpiske.mpt.common.ConfigurationWrapper;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected JdbcTemplate jdbcTemplate;

    protected AbstractDao() {
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
            e.printStackTrace();
        }


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

    protected int runUpdate(String query, Object...args) {
        return jdbcTemplate.update(query, args);
    }
}
