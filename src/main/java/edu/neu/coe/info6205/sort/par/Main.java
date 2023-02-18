package edu.neu.coe.info6205.sort.par;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

	public static void main(String[] args) {
		processArgs(args);

		Random random = new Random();

		ArrayList<Long> timeList = new ArrayList<>();
		int[] array;
		for (int arraySize = 100000; arraySize <= 300000; arraySize += 100000) {
			System.out.println("Array size: " + arraySize);
			array = new int[arraySize];
			XYSeriesCollection dataset = new XYSeriesCollection();
			for (int threadCount = 2; threadCount < 65; threadCount = threadCount * 2) {
				System.out.println("Thread count is: " + threadCount);
				XYSeries series = new XYSeries("Thread Count: " + threadCount);
				for (int j = 50; j < 100; j += 5) {
					ParSort.cutoff = 100 * (j + 1);
					for (int i = 0; i < array.length; i++)
						array[i] = random.nextInt(10000000);
					long time;
					long startTime = System.currentTimeMillis();
					for (int t = 0; t < 10; t++) {
						for (int i = 0; i < array.length; i++)
							array[i] = random.nextInt(10000000);
						ParSort.sort(array, 0, array.length);
					}
					long endTime = System.currentTimeMillis();
					time = (endTime - startTime);
					timeList.add(time);
					series.add(ParSort.cutoff, (double) time / 10);
				}
				dataset.addSeries(series);
			}
			JFreeChart chart = ChartFactory.createXYLineChart("Sort Time vs Cutoff for Array Size " + arraySize,
					"Cutoff", "Sort Time (ms)", dataset, PlotOrientation.VERTICAL, true, true, false);

			// set plot colors
			XYPlot plot = (XYPlot) chart.getPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
			plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
			plot.setOutlinePaint(Color.GRAY);

			// set axis colors
			NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
			domainAxis.setLabelPaint(Color.BLACK);
			domainAxis.setTickLabelPaint(Color.BLACK);
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setLabelPaint(Color.BLACK);
			rangeAxis.setTickLabelPaint(Color.BLACK);

			// save chart to file
			try {
				ChartUtilities.saveChartAsPNG(new File("./src/graph_" + arraySize + ".png"), chart, 800, 600);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void processArgs(String[] args) {
		String[] xs = args;
		while (xs.length > 0)
			if (xs[0].startsWith("-"))
				xs = processArg(xs);
	}

	private static String[] processArg(String[] xs) {
		String[] result = new String[0];
		System.arraycopy(xs, 2, result, 0, xs.length - 2);
		processCommand(xs[0], xs[1]);
		return result;
	}

	private static void processCommand(String x, String y) {
		if (x.equalsIgnoreCase("N"))
			setConfig(x, Integer.parseInt(y));
		else
		// TODO sort this out
		if (x.equalsIgnoreCase("P")) // noinspection ResultOfMethodCallIgnored
			ForkJoinPool.getCommonPoolParallelism();
	}

	private static void setConfig(String x, int i) {
		configuration.put(x, i);
	}

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private static final Map<String, Integer> configuration = new HashMap<>();

}