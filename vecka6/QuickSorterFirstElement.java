package alda.sort;

import java.util.List;

/**
 * Detta är en av algoritmerna du ska implementera själv. Algoritmen ska vara
 * quicksort, och pivotvärdet ska alltid vara det första värdet, det som på
 * sidan 265 i boken förklaras vara "a wrong way".
 * 
 * För information om quicksort generellt: se kursboken sidan 264 och framåt.
 * Tänk dock på att implementationen i boken jobbar på arrayer, men den här
 * jobbar på en lista av godtycklig typ och att den använder en annan
 * pivotvalsstrategi vilket kräver att man skriver om den lite grann.
 */
public class QuickSorterFirstElement<T extends Comparable<? super T>> extends
		Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		// TODO Auto-generated method stub

	}

}
