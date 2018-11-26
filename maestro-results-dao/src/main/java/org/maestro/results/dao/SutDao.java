package org.maestro.results.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.maestro.results.dto.Sut;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

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

    public int insertWithId(Sut dto) {
        return runInsert("insert into sut(sut_id, sut_name, sut_version, sut_jvm_info, sut_other, sut_tags) " +
                        "values(:sutIdc, :sutName, :sutVersion, :sutJvmInfo, :sutOther, :sutTags)", dto);
    }

    public Sut fetchById(int id) throws DataNotFoundException {
        return runQuery("select * from sut where sut_id = ?",
                new BeanPropertyRowMapper<>(Sut.class), id);
    }

    public List<Sut> fetch() throws DataNotFoundException {
        return runQueryMany("select * from sut",
                new BeanPropertyRowMapper<>(Sut.class));
    }


    public Sut fetch(final String sutName, final String sutVersion) throws DataNotFoundException {
        return runQuery("select * from sut where sut_name = ? and sut_version = ?",
                new BeanPropertyRowMapper<>(Sut.class), sutName, sutVersion);
    }


    public Sut testSut(int testId) throws DataNotFoundException {
        Sut ret = testSutCache.get(testId);

        if (ret == null) {
            ret = runQuery("select sut.* from sut,test where sut.sut_id = test.sut_id and test.test_id = ? group by sut_id",
                    new BeanPropertyRowMapper<>(Sut.class),
                    testId);

            testSutCache.put(testId, ret);
        }

        return ret;
    }
}
