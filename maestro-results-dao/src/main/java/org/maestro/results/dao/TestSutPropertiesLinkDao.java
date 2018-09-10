package org.maestro.results.dao;

import org.maestro.results.dto.TestSutPropertiesLink;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestSutPropertiesLinkDao extends AbstractDao {

    public List<TestSutPropertiesLink> fetch() {
        return jdbcTemplate.query("select * from test_sut_properties_link",
                new BeanPropertyRowMapper<>(TestSutPropertiesLink.class));
    }
}
