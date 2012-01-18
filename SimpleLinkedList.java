import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Dokumentationen for metoderna finns i interfacet.
 * @author henrikbe
 */
public class SimpleLinkedList<E> implements List<E> {

	private static class Element<E> {
		public E data;
		public Element<E> prev;
		public Element<E> next;
		public boolean deleted = false; 

		public Element() {

		}

		public Element(E data, Element<E> prev, Element<E> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
			prev.next = this;
			next.prev = this;

		}
	}

	private class SimpleLinkedListIterator implements ListIterator<E> {

		private Element<E> current;
		private int currentIndex;
		private int expectedModCount;

		public SimpleLinkedListIterator(Element<E> current, int currentIndex) {
			this.current = current;
			this.currentIndex = currentIndex;
			this.expectedModCount = modCount;
		}

		@Override
		public void add(E element) {
			throw new UnsupportedOperationException("add is not supported");
		}

		/*
		 * Denna ska vi kommentera
		 */
		@Override
		public boolean hasNext() {
			Element<E> tmp = current;

			while(tmp.next != tail && tmp.next.deleted)
			{
				tmp = tmp.next;
			}
			if(tmp.next != tail)
				return true;
			else
				return false;
		}

		/*
		 * Denna ska vi kommentera
		 */
		@Override
		public boolean hasPrevious() {
			Element<E> tmp = current;

			while(tmp != head && tmp.prev.deleted)
			{
				tmp = tmp.prev;
			}
			if(tmp != head)
				return true;
			else
				return false;
		}

		/*
		 * Denna ska vi kommentera
		 */
		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			do
			{
				current = current.next;
			}while(current.deleted);

			currentIndex++;
			return current.data;
		}

		@Override
		public int nextIndex() {
			return currentIndex;
		}

		/*
		 * Denna ska vi kommentera
		 */
		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			E data = current.data;
			do
			{
				current = current.prev;
			}while(current.deleted);

			currentIndex--;
			return data;
		}

		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove is not supported");

		}

		@Override
		public void set(E element) {
			throw new UnsupportedOperationException("set is not supported");
		}

	}

	private int size;
	private int modCount;
	private int sumDeleted;
	private Element<E> head;
	private Element<E> tail;

	public SimpleLinkedList() {
		clear();
	}

	private void checkIndex(int index, int upperBoundary) {
		if (index < 0 || index > upperBoundary)
			throw new IndexOutOfBoundsException(String.format(
					"Illegal index %d. Acceptable range is 0 to %d", index,
					upperBoundary));
	}

	/*
	 * Denna ska vi kommentera
	 */
	private Element<E> getElement(int index) {
		// Inga indatakontroller eftersom vi litar pa de andra metoderna i
		// klassen.
		Element<E> temp = head;
		for (int n = 0; n < index; n++)
		{
			temp = temp.next;
			if(temp.deleted)
				index++;
		}
		return temp;
	}

	@Override
	public boolean add(E element) {
		new Element<E>(element, tail.prev, tail);
		size++;
		modCount++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		checkIndex(index, size());
		Element<E> temp = getElement(index);
		new Element<E>(element, temp, temp.next);
		size++;
		modCount++;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E element : c)
			add(element);
		return c.size() > 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E element : c)
			add(index++, element);
		return c.size() > 0;
	}

	/*
	 * Denna ska vi kommentera
	 */
	@Override
	public void clear() {
		head = new Element<E>();
		tail = new Element<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
		sumDeleted = 0; //Raknare for samtliga element som ar markerade som deleted
		modCount++;
	}

	@Override
	public boolean contains(Object o) {
		for (E element : this)
			if (o == null ? element == null : o.equals(element))
				return true;

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o))
				return false;

		return true;
	}

	@Override
	public E get(int index) {
		checkIndex(index, size() - 1);
		return getElement(index + 1).data;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		for (E element : this)
		{
			if (o == null ? element == null : o.equals(element))
				return index;
			else
				index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		ListIterator<E> iterator = listIterator(size());
		while (iterator.hasPrevious()) {
			E element = iterator.previous();
			if (o == null ? element == null : o.equals(element))
				return iterator.nextIndex();
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new SimpleLinkedListIterator(head, 0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		checkIndex(index, size());
		return new SimpleLinkedListIterator(getElement(index), index);
	}

	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index >= 0) {
			remove(index);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * denna ska vi kommentera 	
	 */
	@Override
	public E remove(int index) {
		checkIndex(index, size() - 1);
		
		Element<E> removed = getElement(index+1);
		E data = removed.data;
		
		// Delete pa elementet pa index
		getElement(index+1).deleted = true;
		sumDeleted++;
		size--;

		if(sumDeleted >= (size()+sumDeleted)/2)
			deleteAllDeleted();

		modCount++;

		return data;
	}

	/*
	 * denna ska vi kommentera 	
	 */
	private void deleteAllDeleted()
	{
		Element<E> element = head;

		while(element != tail)
	 	{
			if(element.next.deleted)
			{
				element.next.next.prev = element;
				element.next = element.next.next;
			}
			element = element.next;
		}
		sumDeleted = 0;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object o : c) {
			while(remove(o)){
				changed=true;
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		int n = 0;
		while (n < size()) {
			if (c.contains(get(n))) {
				n++;
			} else {
				remove(n);
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public E set(int index, E newElementValue) {
		checkIndex(index, size() - 1);
		Element<E> e = getElement(index + 1);
		E oldValue = e.data;
		e.data = newElementValue;
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("[");
		Iterator<E> iter = iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext())
				buffer.append(", ");
		}
		buffer.append("]");

		return buffer.toString();
	}

	// Har nedanfor bryter vi mot kontraktet for listan. Metoderna ar inte
	// "optional", men de tillfor inget till uppgiften.

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException();
	}

}

