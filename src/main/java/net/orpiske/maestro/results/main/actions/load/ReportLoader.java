package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dto.Test;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReportLoader {
    private static final Logger logger = LoggerFactory.getLogger(ReportLoader.class);

    private Test test;
    private String envName;

    private PropertiesProcessor pp;

    public ReportLoader(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }

    private static void loadProperties(final File testProperties, Map<String, Object> context) {
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

    private void loadFromDir(final File dir, final List<File> files) {
        test.setTestNumber(Integer.parseInt(dir.getName()));

        TestProcessor tp = new TestProcessor(test);

        int testId = tp.loadTest(dir);

        test.setTestId(testId);
        pp = new PropertiesProcessor(test, envName);

        Map<String, Object> values = new HashMap<>();

        files.forEach(item -> loadProperties(item, values));
        pp.loadTest(files.get(0), values);
    }


    public void loadFiles(final File directory) {
        Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[] { "properties"}, true);
        Map<File, List<File>> cache = new HashMap<>();

        logger.info("Loading all properties file from {}", directory);
        while (iterator.hasNext()) {
            File file = iterator.next();

            File parent = file.getParentFile().getParentFile();
            List<File> subFiles = cache.get(parent);
            if (subFiles == null) {
                subFiles = new LinkedList<>();
            }

            logger.trace("Adding file {}", file);
            subFiles.add(file);

            cache.put(parent, subFiles);
            logger.trace("Processing directory {}", parent);
        }

        cache.forEach(this::loadFromDir);


    }
}
