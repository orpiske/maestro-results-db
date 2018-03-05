package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeltaReportCreator extends AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(DeltaReportCreator.class);
    private final ReportsDao reportsDao = new ReportsDao();

    class CsvCallbackHandler implements RowCallbackHandler {
        final BufferedWriter writer;

        public CsvCallbackHandler(final File outputFile) throws IOException {
            this.writer = new BufferedWriter(new FileWriter(outputFile));
        }

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            try {
                CSVPrinter printer = CSVFormat.DEFAULT.withHeader(resultSet).print(writer);

                printer.printRecords(resultSet);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public DeltaReportCreator(String outputDir) {
        super(outputDir);
    }

    @Override
    protected File createReportBaseDir(String baseName) {
        File baseReportDir = new File(getOutputDir(), "delta");

        if (!baseReportDir.exists()) {
            if (!baseReportDir.mkdirs()) {
                logger.error("Unable to create directory {} for delta reports", baseName);
            }
        }

        return baseReportDir;
    }

    public ReportInfo create(final Sut sut, final String protocol, final int messageSize) throws Exception {
        DeltaReportInfo reportInfo = new DeltaReportInfo(sut, protocol, messageSize);

        File baseReportDir = createReportBaseDir(reportInfo);
        File reportFile = new File(baseReportDir, reportInfo.baseName() + ".csv");

        CsvCallbackHandler csvCallbackHandler = new CsvCallbackHandler(reportFile);
        reportsDao.reportDeltas(sut.getSutName(), sut.getSutVersion(), protocol, messageSize, csvCallbackHandler);

        reportInfo.setReportSize(reportFile.length());
        return reportInfo;
    }

}
