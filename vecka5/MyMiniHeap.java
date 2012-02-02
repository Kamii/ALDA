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

	/**
	 * Constructor with no arguments.
	 * This constructor will call the constructor with 2 arguments. 
	 * The standard size of the d-heap i 10 with 2 childs 
	 */
	public MyMiniHeap()
	{
		this(2,DEFAULT_CAPACITY);
	}

	/**
	 * Constructor with one arguments.
	 * @param childs The number of childs
	 * Creates a d-heap with standard capacity of 10 and number
	 * of childs is the argument. This constructor will call the 
	 * constructor with 2 arguments. 
	 */
	public MyMiniHeap(int childs)
	{
		this(childs,DEFAULT_CAPACITY);
	}

	/**
	 * Constructor with two arguments.
	 * @param childs The number of childs.
	 * @param capacity The size of the heap. 
	 * @throws IllegalStateException If childs is less then 2.
	 * Creates a d-heap with given capacity and childrens
	 */
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
	 * @param element the element to insert.
	 * @throws IllegalArgumentException if the element to insert is null.
	 * hole är den första tomma platsen. I metoden finns det en loop
	 * Denna loop kollar om föräldrarna behöver flyttas, detta då det 
	 * nya elementet kan vara mindre än element som redan finns i listan. 
	 * Vi använder oss av getParent(int) för att få föräldern för varje 
	 * element. 
	 */
	public void insert(T element)
	{
		if(element == null)
			throw new IllegalArgumentException();

		if( size == heap.length - 1 )
			enlargeheap(heap.length * 2+1);

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
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException if the heap is empty.
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
	private void percolateDown(int hole)
	{
		int child;
		T tmp = heap[ hole ];

		for( ; getChild(hole) <= size; hole = child )
		{
			child = getChild(hole);
			
			//Kollar vilket av barnen som är det minsta och sätter 
			//variabeln child till detta barn. 
			for(int i = 0; i<d; i++)
				if(child+i <= size && heap[getChild(hole) + i].compareTo(heap[child]) < 0)
					child = getChild(hole) + i;

			if( heap[child].compareTo(tmp) < 0 )
				heap[ hole ] = heap[child];
			else
				break;
		}
		heap[ hole ] = tmp;
	}

	/**
	 * Finds and returns the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException if the heap is empty.
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
	 * @param parent the index of the parent.
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
	 * @param child the index of the child.
	 * @return an integer with the child's parent's index.
	 */
	public int getParent(int child)
	{
		return ((child - 2) / d) + 1;
	}
}
