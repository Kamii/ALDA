// package alda.sort;
import java.util.*;

/**
 * Detta är en av algoritmerna du ska implementera själv. Här får du, till
 * skillnad mot de övriga fallen, använda en extra array.
 * 
 * Se kursboken sidan 258 och framåt. Tänk dock på att implementationen i boken
 * jobbar på arrayer, men den här jobbar på en lista av godtycklig typ.
 */
public class MergeSorter<T extends Comparable<? super T>> extends Sorter<T> {

	@Override
	protected void doSort(List<T> list) {
		mergeSort(list);
	}

	/**
	 * Internal method for having fun
	 * @param list an array of Comparable items.
	 */
	@SuppressWarnings("unchecked")
	private void mergeSort(List<T> list)
	{
		T[] tmpArray = (T[]) new Comparable[list.size()];

		mergeSort(list, tmpArray, 0, list.size() - 1 );
	}

	/**
	 * Internal method that makes recursive calls.
	 * @param list an array of Comparable items.
	 * @param tmpArray an array to place the merged result.
	 * @param left the left-most index of the subarray.
	 * @param right the right-most index of the subarray.
	 */
	private void mergeSort(List<T> list, T[] tmpArray, int left, int right )
	{
		if(left < right)
		{
			int center = ( left + right ) / 2;
			mergeSort(list, tmpArray, left, center );
			mergeSort(list, tmpArray, center + 1, right );
			merge(list, tmpArray, left, center + 1, right );
		}
	}

	/**
	 * Internal method that merges two sorted halves of a subarray.
	 * @param List an array of Comparable items.
	 * @param tmpArray an array to place the merged result.
	 * @param leftPos the left-most index of the subarray.
	 * @param rightPos the index of the start of the second half.
	 * @param rightEnd the right-most index of the subarray.
	 *
	 * Det vi har ändrat här är att metoderna tar List<T> som argument
	 * och så har vi ändrat så get() används istället för indexparanterser
	 */
	private void merge(List<T> list, T[] tmpArray, int leftPos, int rightPos, int rightEnd )
	{
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;

		// Main loop
		while(leftPos <= leftEnd && rightPos <= rightEnd)
			if(list.get(leftPos).compareTo(list.get(rightPos)) <= 0)
				tmpArray[tmpPos++] = list.get(leftPos++);
			else
				tmpArray[tmpPos++] = list.get(rightPos++);

		while(leftPos <= leftEnd)    // Copy rest of first half
			tmpArray[ tmpPos++ ] = list.get(leftPos++);

		while( rightPos <= rightEnd )  // Copy rest of right half
			tmpArray[ tmpPos++ ] = list.get(rightPos++);

		// Copy tmpArray back
		for( int i = 0; i < numElements; i++, rightEnd-- )
			list.set(rightEnd, tmpArray[rightEnd]);
	}
}
