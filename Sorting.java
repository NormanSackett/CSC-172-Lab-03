/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program to play with various sorting algorithms. 
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.io.File;

public class Sorting {


 /**
     * 
     * Sorts the numbers present in the file based on the algorithm provided.
     * 0 = Arrays.sort() (Java Default)
     * 1 = Bubble Sort
     * 2 = Selection Sort 
     * 3 = Insertion Sort 
     * 4 = Mergesort
     * 5 = Quicksort
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  { 
    	In[] in = new In[6];
        in[0] = new In("1Kints.txt");
        in[1] = new In("2Kints.txt");
        in[2] = new In("4Kints.txt");
        in[3] = new In("8Kints.txt");
        in[4] = new In("16Kints.txt");
        in[5] = new In("32Kints.txt");
        
		  // Storing file input in an array
        for(int i = 0; i < in.length; i++) { // loops for each file
        	Integer[] a = in[i].readAllInts();

        	// TODO: Generate 3 other arrays, b, c, d where
        	// b contains sorted integers from a (You can use Java Arrays.sort() method)
        	// c contains all integers stored in reverse order 
        	// (you can have your own O(n) solution to get c from b
        	// d contains almost sorted array 
        	//(You can copy b to a and then perform (0.1 * d.length)  many swaps to acheive this. 
        	Integer[] b = a.clone();
        	Arrays.sort(b);
        
        	Integer[] c = b.clone();
        	reverse(c);
        
        	Integer[] d = b.clone();
        	desort(d);
        		
        	//TODO: 
        	// Read the second argument and based on input select the sorting algorithm
        	//  * 0 = Arrays.sort() (Java Default)
        	//  * 1 = Bubble Sort
        	//  * 2 = Selection Sort 
        	//  * 3 = Insertion Sort 
        	//  * 4 = Mergesort
        	//  * 5 = Quicksort
        
         	// TODO: For each array, a, b, c, d:  
        	Integer[] storageArr = a.clone();
        	String arrayUsed = "a";
    		for (int k = 0; k <= 5; k++) { // loops for each sorting algorithm
    			Stopwatch timer = new Stopwatch();
    			
    			String algorithmUsed = sortRouter(storageArr, k);
        			
        		double time = timer.elapsedTimeMillis();
        		
        		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
          		//TODO: Replace with your own netid
        		String netID = "nsackett";
            			
          		StdOut.printf("%s\t %s\t %8.1f\t   %s\t  %s\t  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, in[i].getFileName());
    		}
    		System.out.println();
        }
    }
    
    // reverses the order of an array
    public static <E extends Comparable<? super E>> void reverse(E[] arr) {
    	swap(arr, arr.length / 2);
    }
    
    // de-sorts an array with a number of swamps equal to 10% of the array's length
    public static <E extends Comparable<? super E>> void desort(E[] arr) {
    	swap(arr, arr.length / 10);
    }
    
    // swaps elements of an array until a certain element
    public static <E extends Comparable<? super E>> void swap(E[] arr, int swapStop) {
    	for(int i = 0; i < swapStop;) {
    		E temp = arr[i];
    		arr[i] = arr[arr.length - i - 1];
    		arr[arr.length - i++ - 1] = temp;
    	}
    }
    
    public static <E extends Comparable<? super E>> void swap(E[] arr, int index1, int index2) {
    	E temp = arr[index1];
    	arr[index1] = arr[index2];
    	arr[index2] = temp;
    }
    
    // routes the input array to the selected sorting algorithm with a case statement and returns the sorting algorithm used
    public static <E extends Comparable<? super E>> String sortRouter(E[] arr, int sortNum) {
    	switch(sortNum) {
    	case 0:
    		Arrays.sort(arr);
    		return "Array.sort()";
    	case 1:
    		bubblesort(arr);
    		return "Bubble Sort";
    	case 2:
    		selectsort(arr);
    		return "Selection Sort";
    	case 3:
    		inssort(arr);
    		return "Insertion Sort";
    	case 4:
    		E[] temp = (E[]) new Integer[arr.length]; // slightly type unsafe but that's okay
    		mergesort(arr, temp, 0, arr.length - 1);
    		return "Merge Sort";
    	case 5:
    		qsort(arr, 0, arr.length - 1);
    		return "Quicksort";
    	default:
    		return "no sorting algorithm matches input";
    	}
    }

    // code snippet from Clifford A Shaffer Data Structures and Algorithm Analysis Edition 3.2
    static <E extends Comparable<? super E>> void bubblesort(E[] A) {
    	for (int i=0; i<A.length-1; i++) // Bubble up i’th record
    		for (int j=A.length-1; j>i; j--)
    			if ((A[j].compareTo(A[j-1]) < 0))
    				swap(A, j, j-1);
    }
    
    // code snippet from Clifford A Shaffer Data Structures and Algorithm Analysis Edition 3.2
    static <E extends Comparable<? super E>> void selectsort(E[] A) {
    	for (int i=0; i<A.length-1; i++) { // Select i’th record
    		int lowindex = i; // Remember its index
    		for (int j=A.length-1; j>i; j--) // Find the least value
    			if (A[j].compareTo(A[lowindex]) < 0)
    				lowindex = j; // Put it in place
    		swap(A, i, lowindex);
    	}
    }

    // code snippet from Clifford A Shaffer Data Structures and Algorithm Analysis Edition 3.2
    static <E extends Comparable<? super E>> void inssort(E[] A) {
    	for (int i=1; i<A.length; i++) // Insert i’th record
    		for (int j=i; (j>0) && (A[j].compareTo(A[j-1])<0); j--)
    			swap(A, j, j-1);
    }
    
    static <E extends Comparable<? super E>> void inssort(E[] A, int lowerBound, int upperBound) {
    	for (int i=lowerBound; i<upperBound; i++) // Insert i’th record
    		for (int j=i; (j>0) && (A[j].compareTo(A[j-1])<0); j--)
    			swap(A, j, j-1);
    }
    
    // code snippet from Clifford A Shaffer Data Structures and Algorithm Analysis Edition 3.2
    static <E extends Comparable<? super E>> void mergesort(E[] A, E[] temp, int l, int r) {
    	int mid = (l+r)/2; // Select midpoint
    	if (l == r) return; // List has one element
    	mergesort(A, temp, l, mid); // Mergesort first half
    	mergesort(A, temp, mid+1, r); // Mergesort second half
    	for (int i=l; i<=r; i++) // Copy subarray to temp
    		temp[i] = A[i];
    	// Do the merge operation back to A
    	int i1 = l; int i2 = mid + 1;
    	for (int curr=l; curr<=r; curr++) {
    		if (i1 == mid+1) // Left sublist exhausted
    			A[curr] = temp[i2++];
    		else if (i2 > r) // Right sublist exhausted
    			A[curr] = temp[i1++];
    		else if (temp[i1].compareTo(temp[i2])<0) // Get smaller
    			A[curr] = temp[i1++];
    		else A[curr] = temp[i2++];
    	}
    }
    
    // quicksort code snippets from Clifford A Shaffer Data Structures and Algorithm Analysis Edition 3.2
    
    // uses midpoint as pivot for quicksort
    static <E extends Comparable<? super E>> int findpivot(E[] A, int i, int j)
    { return (i+j)/2; }
    
    static <E extends Comparable<? super E>> int partition(E[] A, int l, int r, E pivot) {
    do { // Move bounds inward until they meet
    	while (A[++l].compareTo(pivot)<0);
    		while ((r!=0) && (A[--r].compareTo(pivot)>0));
    			swap(A, l, r); // Swap out-of-place values
    } while (l < r); // Stop when they cross
    	swap(A, l, r); // Reverse last, wasted swap
    	return l; // Return first position in right partition
    }

    
    static <E extends Comparable<? super E>> void qsort(E[] A, int i, int j) { // Quicksort
    	int pivotindex = findpivot(A, i, j); // Pick a pivot
    	swap(A, pivotindex, j); // Stick pivot at end
    	// k will be the first position in the right subarray
    	int k = partition(A, i-1, j, A[j]);
    	swap(A, k, j); // Put pivot in place
    	if ((k-i) > 1) qsort(A, i, k-1); // Sort left partition
    	if ((j-k) > 1) qsort(A, k+1, j); // Sort right partition
    }
} 

