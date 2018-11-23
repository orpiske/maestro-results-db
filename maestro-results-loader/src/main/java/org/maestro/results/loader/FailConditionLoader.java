package org.maestro.results.loader;

import org.maestro.results.dao.TestFailConditionDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestFailCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FailConditionLoader {
    private static final Logger logger = LoggerFactory.getLogger(FailConditionLoader.class);

    private final Test test;
    private final TestFailConditionDao dao;

    public FailConditionLoader(final Test test) {
        this.test = test;
        dao = new TestFailConditionDao();
    }

    public void load(final String hostName, final Map<String, Object> properties) {
        String[] failConditions = {"fcl"};

        logger.debug("Recording fail conditions for: {}", hostName);

        for (String failCondition : failConditions) {
            String value = (String) properties.get(failCondition);
            if (value != null) {
                insertTestFailCondition(hostName, failCondition, value);
            }
        }
    }

    private void insertTestFailCondition(final String hostName, final String failCondition, final String value) {
        TestFailCondition dto = new TestFailCondition();

        dto.setTestId(test.getTestId());
        dto.setTestNumber(test.getTestNumber());
        dto.setTestFailConditionResourceName(hostName);
        dto.setTestFailConditionName(failCondition);
        dto.setTestFailConditionValue(value);

        if (logger.isTraceEnabled()) {
            logger.trace("About to insert fail condition {} for test {}", dto, test.getTestId());
        }

        dao.insert(dto);
    }
}
