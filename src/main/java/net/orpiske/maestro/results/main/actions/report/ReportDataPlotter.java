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

public class ReportDataPlotter {
    class Pair {
        private String protocol;
        private String envResourceRole;

        public Pair(final TestResultRecord testResultRecord) {
            this.protocol = testResultRecord.getMessagingProtocol();
            this.envResourceRole = testResultRecord.getEnvResourceRole();
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getEnvResourceRole() {
            return envResourceRole;
        }

        public void setEnvResourceRole(String envResourceRole) {
            this.envResourceRole = envResourceRole;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(protocol, pair.protocol) &&
                    Objects.equals(envResourceRole, pair.envResourceRole);
        }

        @Override
        public int hashCode() {

            return Objects.hash(protocol, envResourceRole);
        }
    }

    private File outputDir;

    public ReportDataPlotter(final File outputDir) {
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


        // AbstractMap.SimpleEntry<String, String> kp; // ; = new AbstractMap.SimpleEntry<>();
        // Set<String> protocols = new HashSet<>();
        Set<Pair> protocols = new HashSet<>();

        resultRecords.stream()
                .forEach(item -> protocols.add(new Pair(item))
        );

         protocols.forEach(item -> addSeriesByProtocol(item, resultRecords, chart));

        try {
            File bitmapFile = new File(outputDir, fileName);

            BitmapEncoder.saveBitmap(chart, bitmapFile.toString(),
                    BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addSeriesByProtocol(final Pair pair, List<TestResultRecord> resultRecords, CategoryChart chart) {
        List<TestResultRecord> filteredResults = resultRecords.stream()
                .filter(item -> item.getMessagingProtocol().equals(pair.getProtocol()))
                .collect(Collectors.toList());

        List<String> groupSet = new ArrayList<>(filteredResults.size());
        List<Double> resultSet = new ArrayList<>(filteredResults.size());

        filteredResults.forEach(resultRecord -> {
            groupSet.add(resultRecord.getSutTags());
            resultSet.add(resultRecord.getTestRateGeometricMean());
            });

        System.out.println("Adding series for " + pair.getProtocol() + "/" + pair.getEnvResourceRole());


        chart.addSeries(pair.getProtocol() + " " + pair.getEnvResourceRole(), groupSet, resultSet);
    }

}
