package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.EnvResults;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class EnvResultsDao extends AbstractDao {
    public int insert(EnvResults dto) {
        return runInsert(
                "insert into env_results(env_name, env_resource_id, test_id, env_resource_role, test_rate_min, " +
                        "test_rate_max, test_rate_error_count, test_rate_samples, test_rate_geometric_mean, " +
                        "test_rate_standard_deviation, error) " +
                        "values(:envName, :envResourceId, :testId, :envResourceRole, :testRateMin, :testRateMax, " +
                        ":testRateErrorCount, :testRateSamples, :testRateGeometricMean, :testRateStandardDeviation, " +
                        ":error)",
                dto);
    }

    public List<EnvResults> fetchById(int id) {
        return jdbcTemplate.query("select * from env_results where env_results_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(EnvResults.class));
    }

    public List<EnvResults> fetch() {
        return jdbcTemplate.query("select * from env_results",
                new BeanPropertyRowMapper<>(EnvResults.class));
    }
}
