package graphalg;

import java.util.concurrent.ThreadLocalRandom;

class QuickSort {
    
    /**
     * quicksort algorithm
     * @param arr an array of Object items
     */
    public static void quicksort(Object[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }
    
    /**
     * Internal method that make the recursive calls
     * @param arr an array of Object items
     * @param low the left-most index of the array or subarray
     * @param high the right-most index of the array or subarray
     */
    private static void quicksort(Object[] arr, int low, int high) {
        
        // If there's fewer than 2 items, do nothing
        if (low < high) {
            int pivotIdx = ThreadLocalRandom.current().nextInt(low, high + 1);
            
            
            NewEdge pivot = (NewEdge)arr[pivotIdx];
            swapReferences(arr, pivotIdx, high);
            
            int i = low - 1;
            int j = high;
            do {
                do { i++; } while (pivot.compareTo(arr[i]) > 0);
                do { j--; } while (pivot.compareTo(arr[j]) <= 0 && j > low);
                if (i < j) swapReferences(arr, i, j);
            } while (i < j);
            // k > low to make sure j won't smaller than 0 (e.g. goes to -1)
            
            // Swap the pivot and arr[i]
            // Now i >= j (i is the first element in the part of
            // where all the elements are >= pivot)
            swapReferences(arr, i, high);
            
            // After swapping, i is the index of the pivot
            if (low < i - 1) quicksort(arr, low, i - 1);
            if (i + 1 < high) quicksort(arr, i + 1, high);
        }

    }
    
    /**
     * Methods to swap two ints in an array
     * @param a an array of Objects
     * @param index1 the index of the first int to be swapped
     * @param index2 the index of the second int to be swapped
     */
    public static void swapReferences(Object[] a, int index1, int index2) {
        Object temp  = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }
    
}
