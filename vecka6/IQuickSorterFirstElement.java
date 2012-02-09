

//package alda.sort;
import java.util.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

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
public class IQuickSorterFirstElement<T extends Comparable<? super T>> extends
		Sorter<T> {

	private static final int CUTOFF = 10;
//inte ändrad

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
/*
	private void insertionSort(List<T> l, int left, int right) {
		int j;
        ListIterator<T> insertIter = l.listIterator(left+1);
        //stega fram till startpunkt
        //for(int i = 0 ; i < left + 1 ; i++){insertIter.next();}
//yttre loop
		for (int p = left + 1; p <= right; p++) {
			T tmp = insertIter.next();
            ListIterator<T> innerIter = insertIter;
//inre loop
            T innerTmp = innerIter.previous();
			for (j = p; j > left && tmp.compareTo(innerTmp=innerIter.previous() ) < 0; j--) {
				l.set(j, innerTmp);
			}
			l.set(j, tmp);
		}
	}
*/
	private void quicksort(List<T> l, int left, int right) {
		if (left + CUTOFF <= right) {
			T pivot = firstElement(l, left, right);

			int i = left-1;
			int j = right+1;
            ListIterator<T> leftIter = l.listIterator(left);
            ListIterator<T> rightIter = l.listIterator(right);
			for (;;) {
                do{i=leftIter.nextIndex();
                }
				while (leftIter.hasNext() && (leftIter.next().compareTo(pivot) < 0));
                do{j=rightIter.previousIndex();
                }
				while (rightIter.hasPrevious() && (rightIter.previous().compareTo(pivot) > 0));
				
				if (i < j){
					swap(l, i, j);
                }
				else
					break;
			}

			swap(l, i, right); // restore pivot

			quicksort(l, left, i - 1); // sort small elements
			quicksort(l, i + 1, right); // sort large elements
		} else {
			insertionSort(l, left, right);
		}
	}
	private T firstElement(List<T> l, int left, int right) {
        T first = l.get(left);
        swap(l, left, right);
        return first;  
    }
	@Override
	protected void doSort(List<T> l) {
		quicksort(l, 0, l.size() - 1);
	}
    public static void main(String[] args){
        List<Integer> a = new LinkedList<Integer>();
        Random r = new Random();
        for(int i = 0; i<10000; i++){
            a.add(r.nextInt(10000));
        }
        IQuickSorterFirstElement qs = new IQuickSorterFirstElement();
        qs.doSort(a);
        System.out.println(a);
    }

}
