import java.util.Arrays;

public class DeterministicSelect {
    private static long comparisons = 0;

    public static void main(String[] args) {
        int[] arr = {12, 3, 5, 7, 4, 19, 26};
        int k = 3;
        int result = select(arr, 0, arr.length - 1, k - 1);
        System.out.println("k-th smallest = " + result);
        System.out.println("Comparisons = " + comparisons);
    }

    public static int select(int[] arr, int left, int right, int k) {
        while (true) {
            if (left == right) return arr[left];

            int pivot = medianOfMedians(arr, left, right);
            int pivotIndex = partition(arr, left, right, pivot);

            if (k == pivotIndex) {
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        if (right - left < 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[(left + right) / 2];
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(arr, i, subRight + 1);
            int median = arr[(i + subRight) / 2];
            arr[left + numMedians] = median;
            numMedians++;
        }
        return medianOfMedians(arr, left, left + numMedians - 1);
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) { comparisons++; i++; }
            while (arr[j] > pivot) { comparisons++; j--; }
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j--;
            }
        }
        return i - 1;
    }
}
