import java.util.ArrayList;
import java.util.Collections;

public class Algorithms {

    public static void selectionSort(int[] list, int n) {
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (list[j] < list[min])
                    min = j;
            }
            if (min != i)
                swap(list, min, i);
        }
    }

    public static void quicksort(int[] list, int low, int high) {
        int stackSize = high - low + 1;
        int[] stack = new int[stackSize];
        int top = -1;
        stack[++top] = low;
        stack[++top] = high;
        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];
            int pivot = partition(list, low, high);
            if (pivot - 1 > low) {
                stack[++top] = low;
                stack[++top] = pivot - 1;
            }
            if (pivot + 1 < high) {
                stack[++top] = pivot + 1;
                stack[++top] = high;
            }
        }
    }

    public static int[] bucketSort(int[] list, int n) {
        int numberOfBuckets = (int) Math.sqrt(n);
        ArrayList<Integer>[] buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++)
            buckets[i] = new ArrayList<>();
        int max = max(list);
        for (int number : list)
            buckets[hash(number, max, numberOfBuckets)].add(number);
        for (ArrayList<Integer> bucket : buckets)
            Collections.sort(bucket);
        int[] sortedArray = new int[n];
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (int number : bucket)
                sortedArray[index++] = number;
        }
        return sortedArray;
    }

    public static int linearSearch(int[] list, int x) {
        int size = list.length;
        for (int i = 0; i < size; i++) {
            if (list[i] == x)
                return i;
        }
        return -1;
    }

    public static int binarySearch(int[] list, int x) {
        int low = 0;
        int high = list.length - 1;
        while (high - low > 1) {
            int mid = (high + low) / 2;
            if (list[mid] < x)
                low = mid + 1;
            else
                high = mid;
        }
        if (list[low] == x)
            return low;
        else if (list[high] == x)
            return high;
        return -1;
    }

    private static int hash(int i, int max, int numberOfBuckets) {
        return (int) Math.floor(i / max * (numberOfBuckets - 1));
    }

    private static int partition(int[] list, int low, int high) {
        int pivot = list[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list[j] <= pivot) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    private static int max(int[] list) {
        int max = list[0];
        for (int i = 1; i < list.length; i++) {
            if (list[i] > max)
                max = list[i];
        }
        return max;
    }

}
