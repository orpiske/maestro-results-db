package org.maestro.results.main.actions.load.loaders;

import org.maestro.common.URLQuery;
import org.maestro.common.exceptions.MaestroException;
import org.maestro.results.dao.TestMsgPropertyDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestMsgProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

public class TestMsgPropertyLoader {
    private static final Logger logger = LoggerFactory.getLogger(TestMsgPropertyLoader.class);

    public static void loadMsgProperties(final File hostDir, final Test test, final Map<String, Object> properties) {
        String[] msgProperties = {"apiName", "variableSize",
                "apiVersion", "messageSize"};

        TestMsgPropertyDao dao = new TestMsgPropertyDao();
        for (String msgProperty : msgProperties) {
            String value = (String) properties.get(msgProperty);
            if (value != null) {
                insertTestMsgProperty(hostDir, test, dao, msgProperty, value);
            }
        }

        String tmpUrl = (String) properties.get("brokerUri");

        if (tmpUrl == null) {
            throw new MaestroException("Invalid test case: the backend did not save the endpoint URL");
        }
        String url = URLDecoder.decode(tmpUrl);

        try {
            URLQuery urlQuery = new URLQuery(url);

            Map<String, String> uriParams = urlQuery.getParams();

            for (Map.Entry<String, String> entry : uriParams.entrySet()) {
                insertTestMsgProperty(hostDir, test, dao, entry.getKey(), entry.getValue());
            }

        } catch (URISyntaxException e) {
            logger.error("Unable to parse URL {}", url, e);
            throw new MaestroException("Invalid URL", e);
        }
    }

    private static void insertTestMsgProperty(File hostDir, final Test test, TestMsgPropertyDao dao,
                                              final String msgProperty, final String value) {
        TestMsgProperty testMsgProperty = new TestMsgProperty();

        testMsgProperty.setTestId(test.getTestId());
        testMsgProperty.setTestNumber(test.getTestNumber());
        testMsgProperty.setTestMsgPropertyResourceName(hostDir.getName());
        testMsgProperty.setTestMsgPropertyName(msgProperty);
        testMsgProperty.setTestMsgPropertyValue(value);

        logger.debug("About to insert property {} for test {}", testMsgProperty, test.getTestId());
        dao.insert(testMsgProperty);
    }
}
