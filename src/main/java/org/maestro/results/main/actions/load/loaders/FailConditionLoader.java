package org.maestro.results.main.actions.load.loaders;

import org.maestro.results.dao.TestFailConditionDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestFailCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class FailConditionLoader {
    private static final Logger logger = LoggerFactory.getLogger(FailConditionLoader.class);

    public static void loadFailConditions(final File reportFile, final Test test, final Map<String, Object> properties) {
        String[] failConditions = {"fcl"};

        TestFailConditionDao dao = new TestFailConditionDao();
        for (String failCondition : failConditions) {
            String value = (String) properties.get(failCondition);
            if (value != null) {
                insertTestFailCondition(reportFile, test, dao, failCondition, value);
            }
        }
    }

    private static void insertTestFailCondition(final File reportFile, final Test test, final TestFailConditionDao dao,
                                                final String failCondition, final String value) {
        TestFailCondition dto = new TestFailCondition();

        dto.setTestId(test.getTestId());
        dto.setTestNumber(test.getTestNumber());
        dto.setTestFailConditionResourceName(reportFile.getName());
        dto.setTestFailConditionName(failCondition);
        dto.setTestFailConditionValue(value);

        logger.debug("About to insert fail condition {} for test {}", dto, test.getTestId());
        dao.insert(dto);
    }
}
