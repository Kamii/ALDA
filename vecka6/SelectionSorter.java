import java.util.List;

/**
 * Selection sort tas inte upp i kursboken men fungerar så att man går igenom
 * listan från början till slutet. För varje position tittar man efter om det
 * finns ett lägre värde längre fram i listan. Om det gör det så byter man ut
 * värdet på den nuvarande positionen med det minsta i den kvarvarande listan.
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
