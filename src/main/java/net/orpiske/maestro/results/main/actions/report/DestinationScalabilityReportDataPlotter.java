package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.TestResultRecord;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DestinationScalabilityReportDataPlotter implements ReportPlotter {
    private File outputDir;

    class Pair implements Comparable<Pair> {
        String configuration;
        String envResourceRole;

        public Pair(TestResultRecord testResultRecord) {
            this.configuration = testResultRecord.getSutTags();
            this.envResourceRole = testResultRecord.getEnvResourceRole();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(configuration, pair.configuration) &&
                    Objects.equals(envResourceRole, pair.envResourceRole);
        }

        @Override
        public int hashCode() {

            return Objects.hash(configuration, envResourceRole);
        }


        @Override
        public int compareTo(Pair pair) {
            if (this.equals(pair)) {
                return 0;
            }

            if (!this.configuration.equals(pair.configuration)) {
                return this.configuration.compareTo(pair.configuration);
            }

            return this.envResourceRole.compareTo(pair.envResourceRole);
        }
    }

    public DestinationScalabilityReportDataPlotter(final File outputDir) {
        this.outputDir = outputDir;
    }

    public void buildChart(final String title, final String xAxisTile, final String yAxisTitle,
                           List<TestResultRecord> resultRecords, final String fileName) {

        // Create Chart
        XYChart chart = new XYChartBuilder()
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
        chart.getStyler().setXAxisLogarithmic(true);

        Set<Pair> configurations = new TreeSet<>();

        resultRecords.stream().forEach(item -> configurations.add(new Pair(item)));

        configurations.forEach(item -> addSeriesByConfiguration(item, resultRecords, chart));

        try {
            File bitmapFile = new File(outputDir, fileName);

            BitmapEncoder.saveBitmap(chart, bitmapFile.toString(),
                    BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addSeriesByConfiguration(final Pair configuration, List<TestResultRecord> resultRecords, XYChart chart) {
        List<TestResultRecord> filteredResults = resultRecords.stream()
                .filter(item -> (item.getSutTags().equals(configuration.configuration) && item.getEnvResourceRole().equals(configuration.envResourceRole)))
                .collect(Collectors.toList());

        List<Integer> groupSet = new ArrayList<>(filteredResults.size());
        List<Double> resultSet = new ArrayList<>(filteredResults.size());

        filteredResults.forEach(resultRecord -> {
            groupSet.add(resultRecord.getLimitDestinations());
            resultSet.add(resultRecord.getTestRateGeometricMean());
        });

        // chart.addSeries(configuration, groupSet, resultSet);
        XYSeries series = chart.addSeries(configuration.configuration + "-" + configuration.envResourceRole,
                groupSet, resultSet);
    }

}
