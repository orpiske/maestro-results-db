package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.mpt.reports.AbstractRenderer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

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

//    protected plotData(final ReportPlotter plotter) {
//        // ProtocolReportDataPlotter rdp = new ProtocolReportDataPlotter(baseReportDir);
//
//        rdp.buildChart("", "", "Messages p/ second", testResultRecords,
//                "performance-by-protocol.png");
//    }

    protected <T extends AbstractRenderer> void generateIndex(T renderer, File baseReportDir) throws Exception {
        // Index HTML generation
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, renderer.render(), StandardCharsets.UTF_8);
    }
}
