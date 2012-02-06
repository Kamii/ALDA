package alda.sort;

import java.util.List;

/**
 * Selection sort tas inte upp i kursboken men fungerar s� att man g�r igenom
 * listan fr�n b�rjan till slutet. F�r varje position tittar man efter om det
 * finns ett l�gre v�rde l�ngre fram i listan. Om det g�r det s� byter man ut
 * v�rdet p� den nuvarande positionen med det minsta i den kvarvarande listan.
 */
public class SelectionSorter<T extends Comparable<? super T>> extends Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		for (int indexToSort = 0; indexToSort < l.size(); indexToSort++) {
			T smallestElement = l.get(indexToSort);
			int smallestElementIndex = indexToSort;

			for (int searchIndex = indexToSort + 1; searchIndex < l.size(); searchIndex++) {
				T currentElement = l.get(searchIndex);
				if (currentElement.compareTo(smallestElement) < 0) {
					smallestElement = currentElement;
					smallestElementIndex = searchIndex;
				}
			}

			if (smallestElementIndex != indexToSort) {
				l.set(smallestElementIndex, l.get(indexToSort));
				l.set(indexToSort, smallestElement);
			}
		}
	}

}
