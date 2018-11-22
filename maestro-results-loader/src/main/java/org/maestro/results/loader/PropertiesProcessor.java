package org.maestro.results.loader;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;
import org.apache.commons.io.FileUtils;
import org.maestro.results.loader.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PropertiesProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProcessor.class);

    private final Test test;

    private final TestMsgPropertyLoader testMsgPropertyLoader;
    private final FailConditionLoader failConditionLoader;
    private final EnvResourceLoader envResourceLoader;
    private final TestDao testDao;

    public PropertiesProcessor(final Test test, final String envName) {
        this.test = test;

        testMsgPropertyLoader = new TestMsgPropertyLoader(test);
        failConditionLoader = new FailConditionLoader(test);
        envResourceLoader = new EnvResourceLoader(test, envName);
        testDao = new TestDao();
    }

    public void loadTest(final File hostDir) {
        logger.debug("Loading host-specific properties: {}", hostDir);
        Map<String, Object> properties = new HashMap<>();

        Collection<File> fileCollection = FileUtils.listFiles(hostDir, new String[] { "properties"}, true);
        fileCollection.forEach(item -> PropertyUtils.loadProperties(item, properties));

        testMsgPropertyLoader.load(hostDir, properties);

        failConditionLoader.load(hostDir, properties);

        envResourceLoader.load(hostDir, properties);

        String rateStr = (String) properties.get("rate");
        String durationStr = (String) properties.get("duration");
        String durationType = (String) properties.get("durationType");
        String connectionCount = (String) properties.get("parallelCount");

        logger.info("Updating duration to {}/{} and rate to {} for test {}/{}", durationStr, durationType, rateStr,
                test.getTestId(), test.getTestNumber());

        testDao.updateDurationAndRate(test.getTestId(), test.getTestNumber(), Integer.parseInt(durationStr),
                durationType, Integer.parseInt(rateStr), Integer.parseInt(connectionCount));
    }

}
