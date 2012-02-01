package vecka5;

/**
 * MyMiniHeap is a d-heap, over and out
 * @author Leon Hennings
 * @author Kamyar Sajjadi
 * with help from Mark Allen Weiss 
 */
public class MyMiniHeap<T extends Comparable<? super T>> implements MiniHeap<T>
{
	private T[] heap;
	private int size;
	private int d;
	private static final int DEFAULT_CAPACITY = 10;

	public MyMiniHeap()
	{
		this(2,DEFAULT_CAPACITY);
	}

	public MyMiniHeap(int childs)
	{
		this(childs,DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public MyMiniHeap(int childs, int capacity)
	{
		if(childs < 2)
			throw new IllegalArgumentException();

		size = 0;
		d = childs;
		heap = (T[]) new Comparable[capacity+1];
	}

	/**
	 * Inserts an element into the heap, placing it correctly according to heap
	 * properties.
	 * 
	 * @param element
	 *            the element to insert.
	 * @throws IllegalArgumentException
	 *             if the element to insert is null.
	 */
	public void insert(T element)
	{
		if(element == null)
			throw new IllegalArgumentException();

		if( size == heap.length - 1 )
			enlargeheap(heap.length * 2);

		int hole = ++size;
		for( ; hole > 1 && element.compareTo( heap[getParent(hole)] ) < 0; hole = getParent(hole) )
			heap[ hole ] = heap[getParent(hole)];
		heap[ hole ] = element;
	}

	/**
	 * Method to enlarge the heap
	 * @param int the new size of the heap
	 */
	@SuppressWarnings("unchecked")
	private void enlargeheap( int newSize )
	{
		T [] old = heap;
		heap = (T []) new Comparable[ newSize ];
		for( int i = 0; i < old.length; i++ )
			heap[ i ] = old[ i ];        
	}

	/**
	 * Deletes the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T deleteMin()
	{
		if(isEmpty())
			throw new IllegalStateException();

		T minItem = findMin();
    heap[1] = heap[size--];
    percolateDown(1);

		return minItem;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown( int hole )
	{
		int child;
		T tmp = heap[ hole ];

		for( ; getChild(hole) <= size; hole = child )
		{
			child = getChild(hole);
			
			for(int i = 0; i<d; i++)
			{
				if(child+i <= size && heap[child + i].compareTo(heap[child]) < 0)
					child = child + i;
			}
			if( heap[child].compareTo(tmp) < 0 )
				heap[ hole ] = heap[child];
			else
				break;
		}
		heap[ hole ] = tmp;
	}

//	or( ; getChild(hole) <= size(); hole = child) {
//		child = getChild(hole);
//		int tempChild = getChild(hole);
//
//		for(int i = 0; i < d && tempChild != size(); i++, tempChild++){
//			if(heapArray[tempChild + 1].compareTo(heapArray[child]) < 0){
//				child = tempChild + 1;
//			}
//		}
//
//		if (heapArray[child].compareTo(tempElement) < 0)
//			heapArray[hole] = heapArray[child];
//		else
//			break;
//	}

	/**
	 * Finds and returns the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T findMin()
	{
		if(isEmpty())
			throw new IllegalStateException();
		return heap[1];
	}

	/**
	 * Checks if the heap is empty or not.
	 * 
	 * @return true if the heap is empty, otherwise false.
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Makes the heap empty.
	 */
	public void makeEmpty()
	{
		size = 0;
	}

	/**
	 * Returns the number of elements in the heap.
	 * 
	 * @return the number of elements in the heap.
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Finds the index of the first child for a given parent's index. This
	 * method is normally private, but is used to test the correctness of the
	 * heap.
	 * 
	 * @param parent
	 *            the index of the parent.
	 * @return an integer with the index of the parent's first child.
	 */
	public int getChild(int parent)
	{
		return d * (parent - 1) + 2;
	}

	/**
	 * Finds the index of a parent for a given child's index. This method is
	 * normally private, but is used to test the correctness of the heap.
	 * 
	 * @param child
	 *            the index of the child.
	 * @return an integer with the child's parent's index.
	 */
	public int getParent(int child)
	{
		return ((child - 2) / d) + 1;
	}
}
