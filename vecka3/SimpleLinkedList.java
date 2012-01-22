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
public class SimpleLinkedList<E> implements List<E> 
{

	private static class Element<E> 
	{
		public E data;
		public Element<E> prev;
		public Element<E> next;
		public boolean deleted = false; 

		public Element() 
		{
		}

		public Element(E data, Element<E> prev, Element<E> next) 
		{
			this.data = data;
			this.prev = prev;
			this.next = next;
			prev.next = this;
			next.prev = this;

		}
	}

	private class SimpleLinkedListIterator implements ListIterator<E> 
	{

		private Element<E> current;
		private int currentIndex;
		private int expectedModCount;

		public SimpleLinkedListIterator(Element<E> current, int currentIndex) 
		{
			this.current = current;
			this.currentIndex = currentIndex;
			this.expectedModCount = modCount;
		}

		@Override
		public void add(E element) 
		{
			throw new UnsupportedOperationException("add is not supported");
		}

		/*
		 * hasNext() kommer att returnera true om current 
		 * element har en next som ej är deleted. Om next 
		 * är markerad som deleted så ska den gå vidare 
		 * till nästa element. Detta kommer att ske tills 
		 * vi kommer till ett objekt som ej är marketat som 
		 * deleted och ej är tail:n.
		 */
		@Override
		public boolean hasNext() 
		{
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
		 * Denna metod är i stortsätt som hasNext().
		 *  Skillnaden är att den kollar om det finns en 
		 * prev i current. Den gör givetvis samma kontroll 
		 * så att inte prev är deleted, om den är flaggad 
		 * som deleted så går den vidare och kollar föregående. 
		 */
		@Override
		public boolean hasPrevious() 
		{
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
		 * next() returnera <E> från nästa Element. Om nästa 
		 * är flaggad som deleted så kommer den att loopa 
		 * tills ett element hittas som ej är flaggat som deleted. 
		 * När elementet är hittat så kommer iteratorns 
		 * "pekare" att ändras fram ett steg. 
		 */
		@Override
		public E next() 
		{
			if (!hasNext())
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			do
			{
				current = current.next;
			} while (current.deleted);

			currentIndex++;
			return current.data;
		}

		@Override
		public int nextIndex() 
		{
			return currentIndex;
		}

		/*
		 * Metoden returnerar föregåendes data och flyttar
		 * iteratorn ett steg bakåt. Om det föregående elementet 
		 * är deleted så ska iteratorn ej ställa sig på det 
		 * utan den ska loopa tills den hittar ett objekt som 
		 * ej är deleted och ställa sig där.
		 */
		@Override
		public E previous() 
		{
			if (!hasPrevious())
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			E data = current.data;
			do
			{
				current = current.prev;
			} while (current.deleted); //fortsätt så länge deleted är sant

			currentIndex--;
			return data;
		}

		@Override
		public int previousIndex() 
		{
			return currentIndex - 1;
		}

		@Override
		public void remove() 
		{
			throw new UnsupportedOperationException("remove is not supported");
		}

		@Override
		public void set(E element) 
		{
			throw new UnsupportedOperationException("set is not supported");
		}
	}

	private int size;
	private int modCount;
	private int sumDeleted; // Summan av samtliga som är flaggade som deleted
	private Element<E> head;
	private Element<E> tail;

	public SimpleLinkedList() 
	{
		clear();
	}

	private void checkIndex(int index, int upperBoundary) 
	{
		if (index < 0 || index > upperBoundary)
			throw new IndexOutOfBoundsException(String.format(
					"Illegal index %d. Acceptable range is 0 to %d", index,
					upperBoundary));
	}

	/*
	 * Hämtar det Element som ligger på det angivna 
	 * indexet. Den börjar i head.next, dvs det
	 * första Elementet i vår länkade lista. Sedan 
	 * så stegar den fram tills den kommit till
	 * det angivna indexet. Om Elementet är flaggat 
	 * som deleted så kommer index att ökas med 1.
	 * Detta innebär att ett extra varv kommer att 
	 * köras för varje Element som är deleted.  
	 */
	private Element<E> getElement(int index) 
	{
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
	public boolean add(E element) 
	{
		new Element<E>(element, tail.prev, tail);
		size++;
		modCount++;
		return true;
	}

	@Override
	public void add(int index, E element) 
	{
		checkIndex(index, size());
		Element<E> temp = getElement(index);
		new Element<E>(element, temp, temp.next);
		size++;
		modCount++;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) 
	{
		for (E element : c)
			add(element);
		return c.size() > 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) 
	{
		for (E element : c)
			add(index++, element);
		return c.size() > 0;
	}

	/*
	 * I denna metod har vi lagt till sumDeleted
	 */
	@Override
	public void clear() 
	{
		head = new Element<E>();
		tail = new Element<E>();
		head.next = tail;
		tail.prev = head;
		size = 0;
		sumDeleted = 0; // sumDeleted sätts till 0
		modCount++;
	}

	@Override
	public boolean contains(Object o) 
	{
		for (E element : this)
			if (o == null ? element == null : o.equals(element))
				return true;
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) 
	{
		for (Object o : c)
			if (!contains(o))
				return false;
		return true;
	}

	@Override
	public E get(int index) 
	{
		checkIndex(index, size() - 1);
		return getElement(index + 1).data;
	}

	@Override
	public int indexOf(Object o) 
	{
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
	public boolean isEmpty() 
	{
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() 
	{
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object o) 
	{
		ListIterator<E> iterator = listIterator(size());
		while (iterator.hasPrevious()) 
		{
			E element = iterator.previous();
			if (o == null ? element == null : o.equals(element))
				return iterator.nextIndex();
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() 
	{
		return new SimpleLinkedListIterator(head, 0);
	}

	@Override
	public ListIterator<E> listIterator(int index) 
	{
		checkIndex(index, size());
		return new SimpleLinkedListIterator(getElement(index), index);
	}

	@Override
	public boolean remove(Object o) 
	{
		int index = indexOf(o);
		if (index >= 0) 
		{
			remove(index);
			return true;
		} 
		else 
		{
			return false;
		}
	}

	/*
	 * Markerar elementet som deleted samt att den tar 
	 * bort datan som elementet håller. När hälften av 
	 * listan är markerad som deleted så kommer den att 
	 * länka om samtliga element. 
	 */
	@Override
	public E remove(int index) 
	{
		checkIndex(index, size() - 1);
		Element<E> removed = getElement(index+1);
		E data = removed.data;
		
		getElement(index+1).data = null;
		getElement(index+1).deleted = true;
		sumDeleted++;
		modCount++;
		size--;

		if(sumDeleted >= (size()+sumDeleted)/2)
			deleteAllDeleted();

		return data;
	}

	/*
	 * Metod för att länka om alla element som är deleted. 
	 * Om next är flaggad som deleted så tar den nästa 
	 * element efter next och ändrar dens pekare till det 
	 * nuvarande elementet. När omlänkningen är klar så 
	 * sätts sumDeleted till 0.
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
	public boolean removeAll(Collection<?> c) 
	{
		boolean changed = false;
		for (Object o : c) 
		{
			while(remove(o))
			{
				changed=true;
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) 
	{
		boolean changed = false;
		int n = 0;
		while (n < size()) 
		{
			if (c.contains(get(n))) 
			{
				n++;
			} 
			else 
			{
				remove(n);
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public E set(int index, E newElementValue) 
	{
		checkIndex(index, size() - 1);
		Element<E> e = getElement(index + 1);
		E oldValue = e.data;
		e.data = newElementValue;
		return oldValue;
	}

	@Override
	public int size() 
	{
		return size;
	}

	public String toString() 
	{
		StringBuilder buffer = new StringBuilder();

		buffer.append("[");
		Iterator<E> iter = iterator();
		while (iter.hasNext()) 
		{
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
	public List<E> subList(int fromIndex, int toIndex) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] arg0) 
	{
		throw new UnsupportedOperationException();
	}

}

