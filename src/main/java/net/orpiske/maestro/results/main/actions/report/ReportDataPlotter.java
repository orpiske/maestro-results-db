package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.TestResultRecord;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDataPlotter {
    public void buildChart(final String title, final String xAxisTile, final String yAxisTitle,
                           List<TestResultRecord> resultRecords, final String fileName) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(1280)
                .height(1024)
                .title(title)
                .xAxisTitle(xAxisTile)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setPlotContentSize(.98);
        chart.getStyler().setYAxisTickMarkSpacingHint(20);


        Set<String> protocols = new HashSet<>();

        resultRecords.stream()
                .forEach(item -> protocols.add(item.getMessagingProtocol())
        );

         protocols.forEach(item -> addSeriesByProtocol(item, resultRecords, chart));

        try {
            BitmapEncoder.saveBitmap(chart, fileName,
                    BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addSeriesByProtocol(final String protocol, List<TestResultRecord> resultRecords, CategoryChart chart) {
        List<TestResultRecord> filteredResults = resultRecords.stream()
                .filter(item -> item.getMessagingProtocol().equals(protocol))
                .collect(Collectors.toList());

        List<String> groupSet = new ArrayList<>(filteredResults.size());
        List<Double> resultSet = new ArrayList<>(filteredResults.size());

        filteredResults.forEach(resultRecord -> {
            groupSet.add(resultRecord.getSutTags());
            resultSet.add(resultRecord.getTestRateGeometricMean());
            });

        System.out.println("Adding series for " + protocol);


        chart.addSeries(protocol, groupSet, resultSet);
    }

}
