import java.util.concurrent.ThreadLocalRandom;

// Array-based Implementation
public class MySort {

    /**
     * Insertion sort
     * @param arr an array of int items
     */
    public static void insertionSort(int[] arr) {
        int idx, temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            idx = i;
            
            while((idx > 0) && (temp < arr[idx - 1])) {
                arr[idx] = arr[idx - 1];
                idx--;
            }
            
            arr[idx] = temp;

        }
    }
    
    /**
     * Selection sort
     * @param arr an array of int items
     */
    public static void selectionSort(int[] arr) {
        int i, j, minIdx, temp;
        for (i = 0; i < arr.length - 1; i++) {
            minIdx = i;
            for (j = i; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            swapReferences(arr, minIdx, i);
        }
    }
    
    /**
     * Methods to swap two ints in an array
     * @param a an array of ints
     * @param index1 the index of the first int to be swapped
     * @param index2 the index of the second int to be swapped
     */
    public static void swapReferences(int[] a, int index1, int index2) {
        int temp  = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }
    
    /**
     * Heapsort
     * @param arr an array of int items
     */
    public static void heapsort(int[] arr) {
        // BottomUp the heap
        heapify(arr, arr.length / 2 - 1, arr.length);
        
        for (int i = arr.length - 1; i > 0; i--) {
            swapReferences(arr, 0, i);
            heapify(arr, i / 2 - 1, i);
        }
    }
    
    /**
     * Get the leftchild index of the specific node
     *
     * @param i the index of given node
     * @return the index of the leftchild node
     */
    private static int leftChild(int i) {
        return 2 * i + 1;
    }
    
    /**
     * Make the array with specific length become an array
     * The maxinum value is at the top of the heap
     *
     * @param arr an array of int items
     * @param idx the index of the last internal node
     * @param len the specific length in the array
     */
    private static void heapify(int[] arr, int idx, int len) {
        int childIdx;

        for (int i = idx; i >= 0; i--) {
            if (leftChild(i) < len) {
                childIdx = leftChild(i);
                // Get the index of child whose key is the biggest
                if ((childIdx != len - 1) && (arr[childIdx] < arr[childIdx + 1])) {
                    childIdx++;
                }
                if (arr[i] < arr[childIdx]) {
                    swapReferences(arr, i, childIdx);
                }
            }
        }
    }
    
    /**
     * Mergesort algorithm
     * @param arr an array of int items
     */
    public static void mergeSort(int[] arr) {
        // Use temp to store the temp sorting result
        int[] temp = new int[arr.length];
        
        mergeSort(arr, temp, 0, arr.length - 1);
    }
    
    /**
     * Internal method that makes the recursive calls
     * @param arr an array of int items
     * @param temp an array to place the merged result
     * @param low the left-most index of subarray
     * @param high the right-most index of subarray
     */
    private static void mergeSort(int[] arr, int[] temp, int low, int high) {
        int mid = (low + high) / 2;
        
        if (low < high) {
            // Interesting recursive function
            mergeSort(arr, temp, low, mid);
            mergeSort(arr, temp, mid + 1, high);
            merge(arr, temp, low, mid, mid + 1, high);
        }

    }
    
    /**
     * Internal method that merges 2 sorted halves of subarray
     * @param arr an array of int items
     * @param temp an array to place the merged result
     * @param leftEnd1 the left-most index of subarray 1
     * @param leftEnd2 the right-most index of subarray 1
     * @param rightEnd1 the left-most index of subarray 2
     * @param rightEnd2 the right-most index of subarray 2
     */
    private static void merge(int[] arr, int[] temp, int leftEnd1, int leftEnd2, int rightEnd1, int rightEnd2) {
        
        int idx1 = leftEnd1, idx2 = rightEnd1;
        int idx = leftEnd1;
        
        while (idx1 <= leftEnd2 && idx2 <= rightEnd2) {
            if (arr[idx1] < arr[idx2]) {
                temp[idx] = arr[idx1];
                idx1++;
            } else {
                temp[idx] = arr[idx2];
                idx2++;
            }
            idx++;
        }
        
        while (idx1 <= leftEnd2) {
            temp[idx] = arr[idx1];
            idx1++;
            idx++;
        }
        
        while (idx2 <= rightEnd2) {
            temp[idx] = arr[idx2];
            idx2++;
            idx++;
        }
        
        // Copy the temp array back to arr
        for (int i = leftEnd1; i <= rightEnd2; i++)
            arr[i] = temp[i];
        
    }
    
    /**
     * quicksort algorithm
     * @param arr an array of int items
     */
    public static void quicksort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }
    
    /**
     * Internal method that make the recursive calls
     * @param arr an array of int items
     * @param low the left-most index of the array or subarray
     * @param high the right-most index of the array or subarray
     */
    private static void quicksort(int[] arr, int low, int high) {
        
        // If there's fewer than 2 items, do nothing
        if (low < high) {
            int pivotIdx = ThreadLocalRandom.current().nextInt(low, high + 1);
            
            Comparable pivot = arr[pivotIdx];
            swapReferences(arr, pivotIdx, high);
            
            int i = low - 1;
            int j = high;
            do {
                do { i++; } while (pivot.compareTo(arr[i]) > 0);
                do { j--; } while (pivot.compareTo(arr[j]) <= 0);
                if (i < j) swapReferences(arr, i, j);
            } while (i < j);
            
            // Swap the pivot and arr[i]
            // Now i >= j (i is the first element in the part of
            // where all the elements are >= pivot)
            swapReferences(arr, i, high);
            
            // After swapping, i is the index of the pivot
            if (low < i - 1) quicksort(arr, low, i - 1);
            if (i + 1 < high) quicksort(arr, i + 1, high);
        }

    }
    
    
    
    public static void display(int[] arr) {
        System.out.print("[ ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("]");
    }

    
    public static void main(String[] args) {
        int[] arr = {33, 56, 12, 6, 3, 4, 1, 2, 7, 8, 199, 24};
        // insertionSort(arr);
        // selectionSort(arr);
        // heapsort(arr);
        // mergeSort(arr);
        quicksort(arr);
        display(arr);
        
        int[] a = {9, 4, 7, 2, 8, 2, 6};
        // heapsort(a);
        mergeSort(a);
        display(a);

    }
    
}
