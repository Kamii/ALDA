import java.util.List;

/**
 * Detta är en av algoritmerna du ska implementera själv. Algoritmen ska vara
 * quicksort, och pivotvärdet ska väljas slumpmässigt, det som på
 * sidan 266 i boken förklaras vara "a safe maneuver".
 * 
 * För information om quicksort generellt: se kursboken sidan 264 och framåt.
 * Tänk dock på att implementationen i boken jobbar på arrayer, men den här
 * jobbar på en lista av godtycklig typ och att den använder en annan
 * pivotvalsstrategi vilket kräver att man skriver om den lite grann.
 */
public class QuickSorterFirstElement<T extends Comparable<? super T>> extends Sorter<T> {

	private static final int CUTOFF = 10;

	private void insertionSort(List<T> l, int left, int right) {
		int j;
		for (int p = left + 1; p <= right; p++) {
			T tmp = l.get(p);
			for (j = p; j > left && tmp.compareTo(l.get(j - 1)) < 0; j--) {
				l.set(j, l.get(j - 1));
			}
			l.set(j, tmp);
		}
	}

	/**
	 * Internal method for the sorting. 
	 * @param list The list that we want to sort.
	 * @param left the left-most index of the subarray.
	 * @param right the right-most index of the subarray.
	 */
	private void quicksort(List<T> l, int left, int right) {
		if (left + CUTOFF <= right) {
			T pivot = firstElement(l, left, right);

			//Ändrat lite på index jämfört med 
			//orginalkoden (QuickSorterMedianThree)
			int i = left-1;
			int j = right+1;

			for (;;) {
				while (l.get(++i).compareTo(pivot) < 0) {
				}
				while (l.get(--j).compareTo(pivot) > 0) {
				}
				if (i < j)
					swap(l, i, j);
				else
					break;
			}

			swap(l, i, right); // restore pivot

			quicksort(l, left, i-1); // sort small elements
			quicksort(l, i, right); // sort large elements
		} else 
		{
			insertionSort(l, left, right);
		}
	}

	/**
	 *	Method to pick the pivot. 
	 *	returns the first element in List l from interval 
	 *	left to right and swaps it to the last place. 
	 *	@return T
	 */
	private T firstElement(List<T> l, int left, int right) 
	{
		T first = l.get(left);
		swap(l, left, right);
		return first;  
	}

	@Override
	protected void doSort(List<T> l) 
	{
		quicksort(l, 0, l.size() - 1);
	}
}
