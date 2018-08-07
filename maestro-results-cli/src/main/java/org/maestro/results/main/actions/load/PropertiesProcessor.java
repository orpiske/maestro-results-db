package org.maestro.results.main.actions.load;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;
import org.maestro.results.main.actions.load.loaders.EnvResourceLoader;
import org.maestro.results.main.actions.load.loaders.FailConditionLoader;
import org.maestro.results.main.actions.load.loaders.TestMsgPropertyLoader;
import org.maestro.results.main.actions.load.utils.PropertyUtils;
import org.apache.commons.io.FileUtils;
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

        logger.info("Updating duration to {} and rate to {} for test {}/{}", durationStr, rateStr, test.getTestId(),
                test.getTestNumber());

        testDao.updateDurationAndRate(test.getTestId(), test.getTestNumber(), Integer.parseInt(durationStr),
                Integer.parseInt(rateStr));
    }

}
