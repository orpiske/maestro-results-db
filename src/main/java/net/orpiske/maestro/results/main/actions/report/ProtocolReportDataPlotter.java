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
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProtocolReportDataPlotter {
    private File outputDir;

    public ProtocolReportDataPlotter(final File outputDir) {
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
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setPlotContentSize(.98);
        chart.getStyler().setYAxisTickMarkSpacingHint(20);

        Set<String> configurations = new TreeSet<>();

        resultRecords.stream()
                .forEach(item -> configurations.add(item.getSutTags())
                );

        configurations.forEach(item -> addSeriesByConfiguration(item, resultRecords, chart));

        try {
            File bitmapFile = new File(outputDir, fileName);

            BitmapEncoder.saveBitmap(chart, bitmapFile.toString(),
                    BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addSeriesByConfiguration(final String configuration, List<TestResultRecord> resultRecords, CategoryChart chart) {
        List<TestResultRecord> filteredResults = resultRecords.stream()
                .filter(item -> item.getSutTags().equals(configuration))
                .collect(Collectors.toList());

        List<String> groupSet = new ArrayList<>(filteredResults.size());
        List<Double> resultSet = new ArrayList<>(filteredResults.size());

        filteredResults.forEach(resultRecord -> {
            groupSet.add(resultRecord.getMessagingProtocol() + " " + resultRecord.getEnvResourceRole());
            resultSet.add(resultRecord.getTestRateGeometricMean());
        });

        chart.addSeries(configuration, groupSet, resultSet);
    }

}
