package org.maestro.results.main.actions.report;

import org.maestro.results.dto.TestReportRecord;

import java.util.List;

public interface ReportPlotter {
    void buildChart(final String title, final String xAxisTile, final String yAxisTitle,
                    List<TestReportRecord> resultRecords, final String fileName);
}
