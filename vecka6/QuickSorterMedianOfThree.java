package alda.sort;

import java.util.List;

/**
 * Quicksort, mer eller mindre rakt av från boken. Pivotvärdet väljs med
 * median-of-three.
 * 
 * För information om quicksort generellt: se kursboken sidan 264 och framåt.
 * Tänk dock på att implementationen i boken jobbar på arrayer, men den här
 * jobbar på en lista av godtycklig typ och att den använder en annan
 * pivotvalsstrategi vilket kräver att man skriver om den lite grann.
 */
public class QuickSorterMedianOfThree<T extends Comparable<? super T>> extends
		Sorter<T> {

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

	private void quicksort(List<T> l, int left, int right) {
		if (left + CUTOFF <= right) {
			T pivot = median3(l, left, right);

			int i = left;
			int j = right - 1;

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

			swap(l, i, right - 1); // restore pivot

			quicksort(l, left, i - 1); // sort small elements
			quicksort(l, i + 1, right); // sort large elements
		} else {
			insertionSort(l, left, right);
		}
	}

	private T median3(List<T> l, int left, int right) {
		int center = (left + right) / 2;
		if (l.get(center).compareTo(l.get(left)) < 0)
			swap(l, left, center);
		if (l.get(right).compareTo(l.get(left)) < 0)
			swap(l, right, left);
		if (l.get(right).compareTo(l.get(center)) < 0)
			swap(l, right, center);

		swap(l, center, right - 1);
		return l.get(right - 1);
	}

	@Override
	protected void doSort(List<T> l) {
		quicksort(l, 0, l.size() - 1);
	}

}
