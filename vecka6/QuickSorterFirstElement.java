package alda.sort;

import java.util.List;

/**
 * Detta �r en av algoritmerna du ska implementera sj�lv. Algoritmen ska vara
 * quicksort, och pivotv�rdet ska alltid vara det f�rsta v�rdet, det som p�
 * sidan 265 i boken f�rklaras vara "a wrong way".
 * 
 * F�r information om quicksort generellt: se kursboken sidan 264 och fram�t.
 * T�nk dock p� att implementationen i boken jobbar p� arrayer, men den h�r
 * jobbar p� en lista av godtycklig typ och att den anv�nder en annan
 * pivotvalsstrategi vilket kr�ver att man skriver om den lite grann.
 */
public class QuickSorterFirstElement<T extends Comparable<? super T>> extends
		Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		// TODO Auto-generated method stub

	}

}
