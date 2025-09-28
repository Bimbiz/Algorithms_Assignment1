import java.util.Random;

public class MergeSortWithMetrics {
    private static long comparisons = 0;
    private static long allocations = 0;
    private static int maxDepth = 0;

    public static void main(String[] args) {
        int n = 20;
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100);
        }

        System.out.println("Before: ");
        printArray(arr);

        int[] buffer = new int[arr.length];
        allocations += arr.length; // буферная память
        mergeSort(arr, buffer, 0, arr.length - 1, 0);

        System.out.println("After: ");
        printArray(arr);

        System.out.println("\nMetrics:");
        System.out.println("Comparisons = " + comparisons);
        System.out.println("Allocations = " + allocations);
        System.out.println("Max recursion depth = " + maxDepth);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, int depth) {
        if (left >= right) return;

        if (right - left <= 10) { // cutoff на insertion sort
            insertionSort(arr, left, right);
            return;
        }

        int mid = (left + right) / 2;

        mergeSort(arr, buffer, left, mid, depth + 1);
        mergeSort(arr, buffer, mid + 1, right, depth + 1);

        maxDepth = Math.max(maxDepth, depth);

        merge(arr, buffer, left, mid, right);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            comparisons++;
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }

        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (i = left; i <= right; i++) arr[i] = buffer[i];
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
