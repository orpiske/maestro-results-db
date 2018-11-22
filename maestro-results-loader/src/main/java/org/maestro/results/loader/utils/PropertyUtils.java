package org.maestro.results.loader.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Deprecated
public class PropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);


    public static void loadProperties(final File testProperties, Map<String, Object> context) {
        if (testProperties.exists()) {
            Properties prop = new Properties();

            try (FileInputStream in = new FileInputStream(testProperties)) {
                prop.load(in);

                for (Map.Entry e : prop.entrySet()) {
                    logger.trace("Adding entry {} with value {}", e.getKey(), e.getValue());
                    context.put((String) e.getKey(), e.getValue());
                }
            } catch (FileNotFoundException e) {
                logger.error("File not found error: {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("Input/output error: {}", e.getMessage(), e);
            }
        }
        else {
            logger.debug("There are no properties file at {}", testProperties.getPath());
        }
    }

}
