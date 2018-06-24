package org.maestro.results.main.actions.load;

import org.maestro.common.exceptions.MaestroException;
import org.maestro.results.dao.*;
import org.maestro.results.dto.*;
import org.maestro.results.main.actions.load.loaders.EnvResourceLoader;
import org.maestro.results.main.actions.load.loaders.FailConditionLoader;
import org.maestro.results.main.actions.load.loaders.TestMsgPropertyLoader;
import org.maestro.results.main.actions.load.utils.PropertyUtils;
import org.maestro.common.URLQuery;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PropertiesProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProcessor.class);

    private String envName;
    private Test test;

    public PropertiesProcessor(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }

    public void loadTest(final File hostDir) {
        logger.debug("Loading host-specific properties: {}", hostDir);
        Map<String, Object> properties = new HashMap<>();

        Collection<File> fileCollection = FileUtils.listFiles(hostDir, new String[] { "properties"}, true);
        fileCollection.forEach(item -> PropertyUtils.loadProperties(item, properties));

        logger.debug("Recording message properties: {}", hostDir);
        TestMsgPropertyLoader.loadMsgProperties(hostDir, test, properties);

        logger.debug("Recording fail conditions: {}", hostDir);
        FailConditionLoader.loadFailConditions(hostDir, test, properties);

        logger.debug("Recording results per environment: {}", hostDir);
        EnvResourceLoader.loadEnvResults(hostDir, test, envName, properties);

        String rateStr = (String) properties.get("rate");
        String durationStr = (String) properties.get("duration");

        logger.info("Updating duration to {} and rate to {} for test {}/{}", durationStr, rateStr, test.getTestId(),
                test.getTestNumber());


        TestDao testDao = new TestDao();
        testDao.updateDurationAndRate(test.getTestId(), test.getTestNumber(), Integer.parseInt(durationStr),
                Integer.parseInt(rateStr));
    }

}
