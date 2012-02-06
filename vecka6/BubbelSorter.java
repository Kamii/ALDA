package alda.sort;

import java.util.List;

/**
 * Bubbelsort tas inte upp i boken men fungerar s� att man g�r igenom listan
 * fr�n b�rjan till slutet och j�mf�r alla par av v�rden. Om tv� v�rden ligger i
 * fel ordning byter man plats p� dem. D�refter upprepar man detta tills man
 * g�tt igenom hela listan en g�ng utan att beh�va g�ra n�gra byten.
 * 
 * Hur skulle efektiviteten hos algoritmen f�r�ndras om man anv�nde en iterator
 * f�r att g� igenom listan ist�llet? Om man anv�nde en ListIterator? Skulle det
 * fungera?
 */
public class BubbelSorter<T extends Comparable<? super T>> extends Sorter<T> {

	@Override
	protected void doSort(List<T> l) {
		boolean sorted;
		do {
			sorted = true;
			for (int i = 1; i < l.size(); i++) {
				if (l.get(i - 1).compareTo(l.get(i)) > 0) {
					swap(l, i - 1, i);
					sorted = false;
				}
			}
		} while (!sorted);
	}
}
