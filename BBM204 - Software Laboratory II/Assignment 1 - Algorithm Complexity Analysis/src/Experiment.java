import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    public static long getSelectionSortAverageRunningTime(int[] list) {
        long sumOfRecordedRunningTimes = 0;
        for (int i = 0; i < 10; i++) {
            int[] listCopy = list.clone();
            long startTime = System.nanoTime();
            Algorithms.selectionSort(listCopy, list.length);
            sumOfRecordedRunningTimes += System.nanoTime() - startTime;
        }
        long totalMilliseconds = sumOfRecordedRunningTimes / 1000000;
        return totalMilliseconds / 10;
    }

    public static long getQuicksortAverageRunningTime(int[] list) {
        long sumOfRecordedRunningTimes = 0;
        for (int i = 0; i < 10; i++) {
            int[] listCopy = list.clone();
            long startTime = System.nanoTime();
            Algorithms.quicksort(listCopy, 0, list.length - 1);
            sumOfRecordedRunningTimes += System.nanoTime() - startTime;
        }
        long totalMilliseconds = sumOfRecordedRunningTimes / 1000000;
        return totalMilliseconds / 10;
    }

    public static long getBucketSortAverageRunningTime(int[] list) {
        long sumOfRecordedRunningTimes = 0;
        for (int i = 0; i < 10; i++) {
            int[] listCopy = list.clone();
            long startTime = System.nanoTime();
            Algorithms.bucketSort(listCopy, list.length);
            sumOfRecordedRunningTimes += System.nanoTime() - startTime;
        }
        long totalMilliseconds = sumOfRecordedRunningTimes / 1000000;
        return totalMilliseconds / 10;
    }

    public static long getLinearSearchAverageRunningTime(int[] list) {
        long sumOfRecordedRunningTimes = 0;
        for (int i = 0; i < 1000; i++) {
            int searchIndex = new Random().nextInt(list.length);
            int searchNumber = list[searchIndex];
            long startTime = System.nanoTime();
            int index = Algorithms.linearSearch(list, searchNumber);
            sumOfRecordedRunningTimes += System.nanoTime() - startTime;
        }
        return sumOfRecordedRunningTimes / 1000;
    }

    public static long getBinarySearchAverageRunningTime(int[] list) {
        long sumOfRecordedRunningTimes = 0;
        for (int i = 0; i < 1000; i++) {
            int searchIndex = new Random().nextInt(list.length);
            int searchNumber = list[searchIndex];
            long startTime = System.nanoTime();
            int index = Algorithms.binarySearch(list, searchNumber);
            sumOfRecordedRunningTimes += System.nanoTime() - startTime;
        }
        return sumOfRecordedRunningTimes / 1000;
    }

    public static int[] readFile(int inputSize) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("TrafficFlowDataset.csv"));
        int[] list = new int[inputSize];
        reader.readLine();
        for (int i = 0; i < inputSize; i++) {
            int flowDuration = Integer.parseInt(reader.readLine().split(",")[6]);
            list[i] = flowDuration;
        }
        return list;
    }

}
