package alda.sort;

import java.util.List;

/**
 * Bubbelsort tas inte upp i boken men fungerar så att man går igenom listan
 * från början till slutet och jämför alla par av värden. Om två värden ligger i
 * fel ordning byter man plats på dem. Därefter upprepar man detta tills man
 * gått igenom hela listan en gång utan att behöva göra några byten.
 * 
 * Hur skulle efektiviteten hos algoritmen förändras om man använde en iterator
 * för att gå igenom listan istället? Om man använde en ListIterator? Skulle det
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
