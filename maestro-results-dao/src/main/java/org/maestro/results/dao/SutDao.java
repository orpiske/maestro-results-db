package org.maestro.results.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.maestro.results.dto.Sut;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.Duration;
import java.util.List;

public class SutDao extends AbstractDao {
    private static CacheManager cacheManager;
    private static Cache<Integer, Sut> testSutCache;

    public SutDao() {
        if (cacheManager == null) {
            synchronized (this) {
                if (cacheManager == null) {
                    CacheConfigurationBuilder<Integer, Sut> config = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, Sut.class, ResourcePoolsBuilder.heap(30))
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)));

                    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                            .withCache("testSut",config)
                            .build();

                    cacheManager.init();

                    testSutCache = cacheManager.getCache("testSut", Integer.class, Sut.class);
                }
            }
        }
    }

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
        Sut ret = testSutCache.get(testId);

        if (ret == null) {
            ret = jdbcTemplate.queryForObject("select sut.* from sut,test where sut.sut_id = test.sut_id and test.test_id = ? group by sut_id",
                    new Object[]{testId},
                    new BeanPropertyRowMapper<>(Sut.class));

            testSutCache.put(testId, ret);
        }

        return ret;
    }
}
