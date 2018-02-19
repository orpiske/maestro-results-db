package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.TestResultRecord;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SutConfigurationReportDataPlotter implements ReportPlotter {
    private File outputDir;

    public SutConfigurationReportDataPlotter(final File outputDir) {
        this.outputDir = outputDir;
    }

    public void buildChart(final String title, final String xAxisTile, final String yAxisTitle,
                           List<TestResultRecord> resultRecords, final String fileName) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(1280)
                .height(1024)
                .title(title)
                .xAxisTitle(xAxisTile)
                .yAxisTitle(yAxisTitle)
                .theme(Styler.ChartTheme.Matlab)
                .build();

        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setLegendLayout(Styler.LegendLayout.Vertical);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setPlotContentSize(.98);

        addSeriesByConfiguration(resultRecords, chart);

        try {
            File bitmapFile = new File(outputDir, fileName);

            BitmapEncoder.saveBitmap(chart, bitmapFile.toString(),
                    BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addSeriesByConfiguration(List<TestResultRecord> resultRecords, CategoryChart chart) {
        List<String> groupSet = new ArrayList<>(resultRecords.size());
        List<Double> resultSet = new ArrayList<>(resultRecords.size());

        resultRecords.forEach(resultRecord -> {
            groupSet.add(resultRecord.getTestTags());
            resultSet.add(resultRecord.getTestRateGeometricMean());
        });

        chart.addSeries("Configurations", groupSet, resultSet);
    }

}
