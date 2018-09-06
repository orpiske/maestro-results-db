package org.maestro.results.dao;

import org.maestro.results.dto.Sut;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class SutDao extends AbstractDao {
    public int insert(Sut dto) {
        return runInsert(
                "insert into sut(sut_name, sut_version, sut_jvm_info, sut_other, sut_tags) " +
                        "values(:sutName, :sutVersion, :sutJvmInfo, :sutOther, :sutTags)", dto);
    }

    public List<Sut> fetchById(int id) {
        return jdbcTemplate.query("select * from sut where sut_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(Sut.class));
    }

    public List<Sut> fetch() {
        return jdbcTemplate.query("select * from sut",
                new BeanPropertyRowMapper<>(Sut.class));
    }

    public List<Sut> fetchDistinct() {
        return jdbcTemplate.query("select * from sut group by sut_name, sut_version order by sut_id",
                new BeanPropertyRowMapper<>(Sut.class));
    }


    public List<String> fetchSutTags(final String sutName, final String sutVersion) {
        return jdbcTemplate.queryForList("select sut_tags from sut where sut_name = ? and sut_version = ?",
                new Object[]{ sutName, sutVersion },
                String.class);
    }

    public Sut testSut(int testId) {
        return jdbcTemplate.queryForObject("select sut.* from sut,test where sut.sut_id = test.sut_id and test.test_id = ? group by sut_id",
                new Object[]{testId},
                new BeanPropertyRowMapper<>(Sut.class));
    }
}
