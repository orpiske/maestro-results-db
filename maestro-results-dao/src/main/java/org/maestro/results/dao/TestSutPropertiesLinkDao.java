package org.maestro.results.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.maestro.results.dto.TestSutPropertiesLink;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.Duration;
import java.util.List;

public class TestSutPropertiesLinkDao extends AbstractDao {
    private static CacheManager cacheManager;
    private static Cache<Integer, List> allResultsCache;

    public TestSutPropertiesLinkDao() {
        if (cacheManager == null) {
            synchronized (this) {
                if (cacheManager == null) {
                    CacheConfigurationBuilder<Integer, List> config = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, List.class, ResourcePoolsBuilder.heap(30))
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(90)));

                    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                            .withCache("testSut",config)
                            .build();

                    cacheManager.init();

                    allResultsCache = cacheManager.getCache("testSut", Integer.class, List.class);
                }
            }
        }
    }

    public List<TestSutPropertiesLink> fetch() {
        List<TestSutPropertiesLink> ret = allResultsCache.get(0);

        if (ret == null) {
            ret = jdbcTemplate.query("select * from test_sut_properties_link",
                    new BeanPropertyRowMapper<>(TestSutPropertiesLink.class));

            allResultsCache.put(0, ret);
        }

        return ret;
    }
}
