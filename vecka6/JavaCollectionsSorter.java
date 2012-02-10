
import java.util.Collections;
import java.util.List;

/**
 * Använder sig av sorteringsmetoden i Collections-klassen. Vilken algoritm är
 * det? Ändrar det sig beroende på datatyp? Varför det? I det här fallet?
 */
public class JavaCollectionsSorter<T extends Comparable<? super T>> extends
		Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		Collections.sort(l);
	}

}
