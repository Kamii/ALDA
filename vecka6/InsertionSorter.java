package alda.sort;

import java.util.List;

/**
 * Se kursboken sidan 248 och fram�t. T�nk dock p� att implementationen i boken
 * jobbar p� arrayer, men den h�r jobbar p� en lista av godtycklig typ.
 */
public class InsertionSorter<T extends Comparable<? super T>> extends Sorter<T>  {

	@Override
	protected void doSort(List<T> l) {
		int j;
		for(int p=1; p<l.size(); p++){
			T tmp = l.get(p);
			for(j=p; j>0 && tmp.compareTo(l.get(j-1))<0; j--){
				l.set(j, l.get(j-1));
			}
			l.set(j, tmp);
		}
	}


}
