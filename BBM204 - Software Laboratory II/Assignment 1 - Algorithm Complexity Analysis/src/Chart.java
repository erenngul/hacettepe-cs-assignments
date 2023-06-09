import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.Arrays;

public class Chart {
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis, String chartType) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        if (chartType.equals("SortingRandom1")) {
            chart.addSeries("Selection Sort", doubleX, yAxis[0]);
            chart.addSeries("Quicksort", doubleX, yAxis[1]);
            chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        }
        else if (chartType.equals("SortingRandom2")) {
            chart.addSeries("Quicksort", doubleX, yAxis[1]);
            chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        }
        else if (chartType.equals("SortingSorted")) {
            chart.addSeries("Selection Sort", doubleX, yAxis[0]);
            chart.addSeries("Quicksort", doubleX, yAxis[1]);
            chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        }
        else if (chartType.equals("SortingReverselySorted")) {
            chart.addSeries("Selection Sort", doubleX, yAxis[0]);
            chart.addSeries("Quicksort", doubleX, yAxis[1]);
            chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        }
        else if (chartType.equals("SortingReverselySorted2")) {
            chart.addSeries("Quicksort", doubleX, yAxis[1]);
            chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        }
        else if (chartType.equals("Searching")) {
            chart.addSeries("Linear Search (Random Data)", doubleX, yAxis[0]);
            chart.addSeries("Linear Search (Sorted Data)", doubleX, yAxis[1]);
            chart.addSeries("Binary Search (Sorted Data)", doubleX, yAxis[2]);
            chart.setYAxisTitle("Time in Nanoseconds");
        }

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
