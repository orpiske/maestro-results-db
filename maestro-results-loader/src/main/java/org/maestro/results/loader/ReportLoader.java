package org.maestro.results.loader;

import org.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class ReportLoader {
    private static final Logger logger = LoggerFactory.getLogger(ReportLoader.class);

    private Test test;
    private String envName;

    public ReportLoader(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }


    public void loadFromDir(final File dir, final List<File> files, final String hostName, final String hostRole) {
        TestProcessor tp = new TestProcessor(test);

        logger.info("Adding a new test record from data from dir {}", dir);

        tp.loadTest();

        // Load test data for each host
        PropertiesProcessor pp = new PropertiesProcessor(test, envName);

        pp.loadTest(dir, files, hostName, hostRole);
    }

    public void load(final File reportDir, final String hostName, final String hostRole) {
        File[] tmp = reportDir.listFiles();

        loadFromDir(reportDir, Arrays.asList(tmp), hostName, hostRole);
    }
}
