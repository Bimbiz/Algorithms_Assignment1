import java.util.Random;

public class QuickSortWithMetrics {
    private static long comparisons = 0;
    private static int maxDepth = 0;
    private static Random rand = new Random();

    public static void main(String[] args) {
        int[] arr = {34, 7, 23, 32, 5, 62, 78, 12, 45, 90};
        quickSort(arr, 0, arr.length - 1, 0);

        System.out.println("Sorted:");
        for (int x : arr) System.out.print(x + " ");
        System.out.println("\nComparisons: " + comparisons);
        System.out.println("Max recursion depth: " + maxDepth);
    }

    public static void quickSort(int[] arr, int left, int right, int depth) {
        while (left < right) {
            int pivotIndex = partition(arr, left, right);

            maxDepth = Math.max(maxDepth, depth);

            // Определяем меньшую часть для рекурсии
            if (pivotIndex - left < right - pivotIndex) {
                quickSort(arr, left, pivotIndex - 1, depth + 1);
                left = pivotIndex + 1; // продолжаем на правой части (итеративно)
            } else {
                quickSort(arr, pivotIndex + 1, right, depth + 1);
                right = pivotIndex - 1; // продолжаем на левой части
            }
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivotIndex = left + rand.nextInt(right - left + 1);
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
