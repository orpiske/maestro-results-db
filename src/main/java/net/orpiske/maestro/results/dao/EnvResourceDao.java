package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.EnvResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class EnvResourceDao extends AbstractDao {
    public int insert(EnvResource dto) {
        return runInsert(
                "insert into env_resource(env_resource_name, env_resource_os_name, env_resource_os_arch, " +
                        "env_resource_os_version, env_resource_os_other, env_resource_hw_name, env_resource_hw_model, " +
                        "env_resource_hw_cpu, env_resource_hw_ram, env_resource_hw_disk_type, env_resource_hw_other) " +
                        "values(:envResourceName, :envResourceOsName, :envResourceOsArch, :envResourceOsVersion, " +
                        ":envResourceOsOther, :envResourceHwName, :envResourceHwModel, :envResourceHwCpu, " +
                        ":envResourceHwRam, :envResourceHwDiskType, :envResourceHwOther)",
                dto);
    }

    public List<EnvResource> fetchById(int id) {
        return jdbcTemplate.query("select * from env_resource where env_resource_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(EnvResource.class));
    }

    public List<EnvResource> fetch() {
        return jdbcTemplate.query("select * from env_resource",
                new BeanPropertyRowMapper<>(EnvResource.class));
    }
}
