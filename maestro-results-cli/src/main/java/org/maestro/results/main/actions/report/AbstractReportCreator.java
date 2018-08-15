package org.maestro.results.main.actions.report;

import org.maestro.results.dto.Sut;
import org.maestro.results.main.actions.report.exceptions.EmptyResultSet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

/**
 * Base class for the report creators
 */
public abstract class AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(AbstractReportCreator.class);

    private final String outputDir;
    private final String testName;

    public AbstractReportCreator(final String outputDir, final String testName) {
        this.outputDir = outputDir;
        this.testName = testName;
    }

    protected String getOutputDir() {
        return outputDir;
    }

    protected String getTestName() {
        return testName;
    }

    protected File createReportBaseDir(final String baseName) {
        File baseReportDir = new File(outputDir, baseName);

        if (!baseReportDir.exists()) {
            if (!baseReportDir.mkdirs()) {
                logger.error("Unable to create report directory {}", baseReportDir);
            }
        }

        return baseReportDir;
    }

    protected File createReportBaseDir(final ReportInfo reportInfo) {
        return createReportBaseDir(reportInfo.baseName());
    }


    protected void generateIndex(final String resourceName, File baseReportDir, final Map<String, Object> context) throws Exception {
        ReportRenderer reportRenderer = new ReportRenderer(ReportTemplates.DEFAULT, resourceName);

        // Index HTML generation
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, reportRenderer.render(context), StandardCharsets.UTF_8);
    }

    protected void validateResultSet(final Sut sut, final String setName, Collection<?> resultSet) {
        if (resultSet == null || resultSet.size() == 0) {
            throw new EmptyResultSet(sut, setName);
        }
    }
}