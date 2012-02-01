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
	private int numberOfChilds;
	private static final int DEFAULT_CAPACITY = 10;

	public MyMiniHeap()
	{
		this(2,DEFAULT_CAPACITY);
	}

	public MyMiniHeap(int childs)
	{
		if(childs < 2)
			throw new IllegalArgumentException();
		this(childs, DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public MyMiniHeap(int childs, int capacity)
	{
		size = 0;
		numberOfChilds = childs;
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
	public void insert(T element);
	{
		if( currentSize == array.length - 1 )
			enlargeArray( array.length * 2 + 1 );

		int hole = ++size;
		for( ; hole > 1 && x.compareTo( array[getParent()] ) < 0; getParent() )
			array[ hole ] = array[getParent()];
		array[ hole ] = x;
	}

	/**
	 * Method to enlarg the array
	 * @param int the new size of the array
	 */
	@SuppressWarnings("unchecked")
	private void enlargeArray( int newSize )
	{
		AnyType [] old = array;
		array = (AnyType []) new Comparable[ newSize ];
		for( int i = 0; i < old.length; i++ )
			array[ i ] = old[ i ];        
	}

	/**
	 * Deletes the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T deleteMin();
	{
	}

	/**
	 * Finds and returns the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T findMin();
	{
	}

	/**
	 * Checks if the heap is empty or not.
	 * 
	 * @return true if the heap is empty, otherwise false.
	 */
	public boolean isEmpty();
	{
		return size == 0;
	}

	/**
	 * Makes the heap empty.
	 */
	public void makeEmpty();

	/**
	 * Returns the number of elements in the heap.
	 * 
	 * @return the number of elements in the heap.
	 */
	public int size();
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
	public int getChild(int parent);
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
	public int getParent(int child);
	{
		return (child - 2 / d) + 1;
	}
}
