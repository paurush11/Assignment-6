package edu.neu.coe.info6205.sort.elementary;

import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class mainMethodImplementation {
	public static void main(String[] args) {

		Integer[] randomArray, orderedArray, partiallyOrderedArray, reverseOrderedArray;
		InsertionSort insertionSort = new InsertionSort();
		int n = 10000; // starting value for n
		int numTrials = 5; // number of trials for each value of n

		// Create datasets for different array orderings
		XYSeries randomData = new XYSeries("Random Array");
		XYSeries orderedData = new XYSeries("Ordered Array");
		XYSeries partiallyOrderedData = new XYSeries("Partially Ordered Array");
		XYSeries reverseOrderedData = new XYSeries("Reverse Ordered Array");

		for (int i = 0; i < numTrials; i++) {
			randomArray = createRandomArray(n);
			orderedArray = createOrderedArray(n);
			partiallyOrderedArray = createPartiallyOrderedArray(n);
			reverseOrderedArray = createReverseOrderedArray(n);
			System.out.println("Random array:");
			randomData.add(n, calculate(insertionSort, randomArray));
			System.out.println("Ordered array:");
			orderedData.add(n, calculate(insertionSort, orderedArray));
			System.out.println("Partially ordered array:");
			partiallyOrderedData.add(n, calculate(insertionSort, partiallyOrderedArray));
			System.out.println("Reverse ordered array:");
			reverseOrderedData.add(n, calculate(insertionSort, reverseOrderedArray));

			n *= 2; // double n for next trial
			System.out.println();
		}

		// Add datasets to the collection
		XYDataset dataset = new XYSeriesCollection(randomData);
		((XYSeriesCollection) dataset).addSeries(orderedData);
		((XYSeriesCollection) dataset).addSeries(partiallyOrderedData);
		((XYSeriesCollection) dataset).addSeries(reverseOrderedData);

		// Create the chart
		JFreeChart chart = ChartFactory.createXYLineChart("Running Time of Insertion Sort - Paurush Batish",
				"Array Size (n)", "Time (ms)", dataset, PlotOrientation.VERTICAL, true, true, false);

		// Display the chart
		ChartFrame frame = new ChartFrame("Insertion Sort Running Time", chart);
		frame.pack();
		frame.setVisible(true);
	}

	private static long calculate(InsertionSort insertionSort, Integer[] array) {
		long startTime = System.currentTimeMillis();
		insertionSort.sort(array, 0, array.length);
		long endTime = System.currentTimeMillis();
		System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
		return endTime - startTime;
	}

	private static Integer[] createRandomArray(int n) {
		Random random = new Random();
		Integer[] array = new Integer[n];
		for (int i = 0; i < n; i++) {
			array[i] = random.nextInt();
		}
		return array;
	}

	private static Integer[] createOrderedArray(int n) {
		Integer[] array = new Integer[n];
		for (int i = 0; i < n; i++) {
			array[i] = i;
		}
		return array;
	}

	private static Integer[] createPartiallyOrderedArray(int n) {
		Integer[] array = new Integer[n];
		for (int i = 0; i < n / 2; i++) {
			array[i] = i;
		}
		for (int i = n / 2; i < n; i++) {
			array[i] = n - i - 1;
		}
		return array;
	}

	private static Integer[] createReverseOrderedArray(int n) {
		Integer[] array = new Integer[n];
		for (int i = 0; i < n; i++) {
			array[i] = n - i - 1;
		}
		return array;
	}
}
