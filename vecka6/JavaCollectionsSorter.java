package alda.sort;

import java.util.Collections;
import java.util.List;

/**
 * Anv�nder sig av sorteringsmetoden i Collections-klassen. Vilken algoritm �r
 * det? �ndrar det sig beroende p� datatyp? Varf�r det? I det h�r fallet?
 */
public class JavaCollectionsSorter<T extends Comparable<? super T>> extends
		Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		Collections.sort(l);
	}

}
