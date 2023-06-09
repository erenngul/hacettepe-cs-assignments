import java.io.IOException;
import java.util.Arrays;

public class MainManager {

    private static final int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

    // Sorting Random Data
    public static void sortRandomData() throws IOException {
        double[] selectionSortTimes = new double[10];
        double[] quicksortTimes = new double[10];
        double[] bucketSortTimes = new double[10];
        for (int i = 0; i < 10; i++) {
            int[] list = Experiment.readFile(inputSizes[i]);
            selectionSortTimes[i] = Experiment.getSelectionSortAverageRunningTime(list);
            quicksortTimes[i] = Experiment.getQuicksortAverageRunningTime(list);
            bucketSortTimes[i] = Experiment.getBucketSortAverageRunningTime(list);
        }
        System.out.println("Selection Sort Random Data Times: " + Arrays.toString(selectionSortTimes));
        System.out.println("Quicksort Random Data Times: " + Arrays.toString(quicksortTimes));
        System.out.println("Bucket Sort Random Data Times: " + Arrays.toString(bucketSortTimes));
        double[][] yAxis = new double[3][10];
        yAxis[0] = Arrays.copyOf(selectionSortTimes, 10);
        yAxis[1] = Arrays.copyOf(quicksortTimes, 10);
        yAxis[2] = Arrays.copyOf(bucketSortTimes, 10);
        Chart.showAndSaveChart("Tests on Random Data", inputSizes, yAxis, "SortingRandom1");
        Chart.showAndSaveChart("Tests on Random Data 2", inputSizes, yAxis, "SortingRandom2");
    }

    // Sorting Sorted Data
    public static void sortSortedData() throws IOException {
        double[] selectionSortTimes = new double[10];
        double[] quicksortTimes = new double[10];
        double[] bucketSortTimes = new double[10];
        for (int i = 0; i < 10; i++) {
            int[] list = Experiment.readFile(inputSizes[i]);
            Arrays.sort(list);
            selectionSortTimes[i] = Experiment.getSelectionSortAverageRunningTime(list);
            quicksortTimes[i] = Experiment.getQuicksortAverageRunningTime(list);
            bucketSortTimes[i] = Experiment.getBucketSortAverageRunningTime(list);
        }
        System.out.println("Selection Sort Sorted Data Times: " + Arrays.toString(selectionSortTimes));
        System.out.println("Quicksort Sorted Data Times: " + Arrays.toString(quicksortTimes));
        System.out.println("Bucket Sort Sorted Data Times: " + Arrays.toString(bucketSortTimes));
        double[][] yAxis = new double[3][10];
        yAxis[0] = Arrays.copyOf(selectionSortTimes, 10);
        yAxis[1] = Arrays.copyOf(quicksortTimes, 10);
        yAxis[2] = Arrays.copyOf(bucketSortTimes, 10);
        Chart.showAndSaveChart("Tests on Sorted Data", inputSizes, yAxis, "SortingSorted");
    }

    // Sorting Reversely Sorted Data
    public static void sortReverselySortedData() throws IOException {
        double[] selectionSortTimes = new double[10];
        double[] quicksortTimes = new double[10];
        double[] bucketSortTimes = new double[10];
        for (int i = 0; i < 10; i++) {
            int[] list = Experiment.readFile(inputSizes[i]);
            Arrays.sort(list);
            for (int j = 0, k = list.length - 1; j < k; j++, k--) {
                int temp = list[j];
                list[j] = list[k];
                list[k] = temp;
            }
            selectionSortTimes[i] = Experiment.getSelectionSortAverageRunningTime(list);
            quicksortTimes[i] = Experiment.getQuicksortAverageRunningTime(list);
            bucketSortTimes[i] = Experiment.getBucketSortAverageRunningTime(list);
        }
        System.out.println("Selection Sort Reversely Sorted Data Times: " + Arrays.toString(selectionSortTimes));
        System.out.println("Quicksort Reversely Sorted Data Times: " + Arrays.toString(quicksortTimes));
        System.out.println("Bucket Sort Reversely Sorted Data Times: " + Arrays.toString(bucketSortTimes));
        double[][] yAxis = new double[3][10];
        yAxis[0] = Arrays.copyOf(selectionSortTimes, 10);
        yAxis[1] = Arrays.copyOf(quicksortTimes, 10);
        yAxis[2] = Arrays.copyOf(bucketSortTimes, 10);
        Chart.showAndSaveChart("Tests on Reversely Sorted Data", inputSizes, yAxis, "SortingReverselySorted");
        Chart.showAndSaveChart("Tests on Reversely Sorted Data 2", inputSizes, yAxis, "SortingReverselySorted2");
    }

    // Searching Data
    public static void searchData() throws IOException {
        double[] linearSearchRandomDataTimes = new double[10];
        double[] linearSearchSortedDataTimes = new double[10];
        double[] binarySearchTimes = new double[10];
        int[] arr = Experiment.readFile(10000);
        Experiment.getLinearSearchAverageRunningTime(arr);
        Experiment.getBinarySearchAverageRunningTime(arr);
        for (int i = 0; i < 10; i++) {
            int[] list = Experiment.readFile(inputSizes[i]);
            int[] sortedList = list.clone();
            Arrays.sort(sortedList);
            linearSearchRandomDataTimes[i] = Experiment.getLinearSearchAverageRunningTime(list);
            linearSearchSortedDataTimes[i] = Experiment.getLinearSearchAverageRunningTime(sortedList);
            binarySearchTimes[i] = Experiment.getBinarySearchAverageRunningTime(sortedList);
        }
        System.out.println("Linear Searching Random Data Times: " + Arrays.toString(linearSearchRandomDataTimes));
        System.out.println("Linear Searching Sorted Data Times: " + Arrays.toString(linearSearchSortedDataTimes));
        System.out.println("Binary Searching Sorted Data Times: " + Arrays.toString(binarySearchTimes));
        double[][] yAxis = new double[3][10];
        yAxis[0] = Arrays.copyOf(linearSearchRandomDataTimes, 10);
        yAxis[1] = Arrays.copyOf(linearSearchSortedDataTimes, 10);
        yAxis[2] = Arrays.copyOf(binarySearchTimes, 10);
        Chart.showAndSaveChart("Tests on Searched Data", inputSizes, yAxis, "Searching");
    }

}
