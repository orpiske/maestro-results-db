package net.orpiske.maestro.results.main.actions.report;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Base class for the report creators
 */
public abstract class AbstractReportCreator {
    private final String outputDir;

    public AbstractReportCreator(final String outputDir) {
        this.outputDir = outputDir;
    }

    protected File createReportBaseDir(final ReportInfo reportInfo) {
        // Directory creating
        File baseReportDir = new File(outputDir, reportInfo.baseName());

        baseReportDir.mkdirs();

        return baseReportDir;
    }


    protected void generateIndex(final String resourceName, File baseReportDir, final Map<String, Object> context) throws Exception {
        ReportRenderer reportRenderer = new ReportRenderer(ReportTemplates.DEFAULT, resourceName, context);

        // Index HTML generation
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, reportRenderer.render(), StandardCharsets.UTF_8);
    }
}
