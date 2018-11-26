package org.maestro.results.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.maestro.results.dto.EnvResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.Duration;
import java.util.List;


import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

public class EnvResourceDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(EnvResourceDao.class);
    private static CacheManager cacheManager;
    private static Cache<Integer, List> cache;

    public EnvResourceDao() {
        if (cacheManager == null) {
            synchronized (this) {
                if (cacheManager == null) {
                    CacheConfigurationBuilder<Integer, List> config = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, List.class, ResourcePoolsBuilder.heap(30))
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)));

                    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                            .withCache("envRes",config)
                            .build();

                    cacheManager.init();

                    cache = cacheManager.getCache("envRes", Integer.class, List.class);
                }
            }
        }

    }

    public int insert(EnvResource dto) {
        return runInsert("insert into env_resource(env_resource_name, env_resource_os_name, env_resource_os_arch, " +
                        "env_resource_os_version, env_resource_os_other, env_resource_hw_name, env_resource_hw_model, " +
                        "env_resource_hw_cpu, env_resource_hw_ram, env_resource_hw_disk_type, env_resource_hw_other) " +
                        "values(:envResourceName, :envResourceOsName, :envResourceOsArch, :envResourceOsVersion, " +
                        ":envResourceOsOther, :envResourceHwName, :envResourceHwModel, :envResourceHwCpu, " +
                        ":envResourceHwRam, :envResourceHwDiskType, :envResourceHwOther)",
                dto);
    }


    public EnvResource fetchByName(final String name) throws DataNotFoundException {
        return runQuery("select * from env_resource where env_resource_name = ?",
                new BeanPropertyRowMapper<>(EnvResource.class),
                name);
    }

    public List<EnvResource> fetch() throws DataNotFoundException {
        return runQueryMany("select * from env_resource", new BeanPropertyRowMapper<>(EnvResource.class));
    }

    public List<EnvResource> fetchForTest(int testId) throws DataNotFoundException {
        List<EnvResource> ret = cache.get(testId);

        if (ret == null) {
            logger.warn("Record not in cache. Retrieving from the DB");

            ret = runQueryMany("select * from env_resource where env_resource_name in (" +
                            "select env_resource_name from test_results where test_id = ? group by env_resource_name)",
                    new BeanPropertyRowMapper<>(EnvResource.class),
                    testId);

            cache.put(testId, ret);
        }
        else {
            logger.info("Returning cached result");
        }

        return ret;
    }
}
