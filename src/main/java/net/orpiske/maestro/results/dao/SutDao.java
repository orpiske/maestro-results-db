package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.Sut;
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
}
