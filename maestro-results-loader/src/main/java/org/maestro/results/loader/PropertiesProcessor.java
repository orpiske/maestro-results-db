package org.maestro.results.loader;

import org.apache.commons.io.FilenameUtils;
import org.maestro.common.PropertyUtils;
import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.EnvResource;
import org.maestro.results.dto.EnvResults;
import org.maestro.results.dto.Test;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProcessor.class);

    private final Test test;

    private final TestMsgPropertyLoader testMsgPropertyLoader;
    private final FailConditionLoader failConditionLoader;
    private final EnvResourceLoader envResourceLoader;
    private final EnvResultsLoader envResultsLoader;
    private final TestDao testDao;

    public PropertiesProcessor(final Test test, final String envName) {
        this.test = test;

        testMsgPropertyLoader = new TestMsgPropertyLoader(test);
        failConditionLoader = new FailConditionLoader(test);
        envResourceLoader = new EnvResourceLoader(test, envName);
        envResultsLoader = new EnvResultsLoader(test, envName);

        testDao = new TestDao();
    }

    public void loadTest(final File reportDir, final List<File> files, final String hostName, final String hostRole) {
        logger.debug("Loading host-specific properties: {}", reportDir);
        Map<String, Object> properties = new HashMap<>();

        // First, load all the properties from all the files in the report dir into a map
        files.stream()
                .filter(f -> f.getName().endsWith(".properties"))
                .forEach(f -> PropertyUtils.loadProperties(f, properties));

        // then use the contents of that map to cross the data and load into the DB

        EnvResource envResource = envResourceLoader.load(hostName, properties);

        logger.info("Loading message properties");
        testMsgPropertyLoader.load(hostName, properties);

        logger.info("Loading fail-conditions");
        failConditionLoader.load(hostName, properties);

        logger.info("Loading env resource info");
        envResultsLoader.load(envResource, hostRole, properties);

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
