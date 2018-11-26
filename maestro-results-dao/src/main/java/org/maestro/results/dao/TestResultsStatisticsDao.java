package org.maestro.results.dao;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.maestro.results.dto.TestResultStatistics;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.Duration;
import java.util.List;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

public class TestResultsStatisticsDao extends AbstractDao {
    private static CacheManager cacheManager;
    private static Cache<Integer, TestResultStatistics> testResultStatisticsCache;

    public TestResultsStatisticsDao() {
        if (cacheManager == null) {
            synchronized (this) {
                if (cacheManager == null) {
                    CacheConfigurationBuilder<Integer, TestResultStatistics> config = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(Integer.class, TestResultStatistics.class, ResourcePoolsBuilder.heap(30))
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)));

                    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                            .withCache("testResultStatistics",config)
                            .build();

                    cacheManager.init();

                    testResultStatisticsCache = cacheManager.getCache("testResultStatistics", Integer.class, TestResultStatistics.class);
                }
            }
        }
    }

    public List<TestResultStatistics> fetch() throws DataNotFoundException {
        return runQueryMany("select * from test_results_statistics",
                new BeanPropertyRowMapper<>(TestResultStatistics.class));
    }

    public TestResultStatistics successFailureCount(int testId) throws DataNotFoundException {
        TestResultStatistics ret = testResultStatisticsCache.get(testId);

        if (ret == null) {
            ret = runQuery("select * from test_results_statistics where test_id = ?",
                    new BeanPropertyRowMapper<>(TestResultStatistics.class),
                    testId);

            testResultStatisticsCache.put(testId, ret);
        }

        return ret;
    }
}
