package vecka5;

public interface MiniHeap<T extends Comparable<? super T>> {

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

	/**
	 * Deletes the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T deleteMin();

	/**
	 * Finds and returns the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException
	 *             if the heap is empty.
	 */
	public T findMin();

	/**
	 * Checks if the heap is empty or not.
	 * 
	 * @return true if the heap is empty, otherwise false.
	 */
	public boolean isEmpty();

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

	/**
	 * Finds the index of a parent for a given child's index. This method is
	 * normally private, but is used to test the correctness of the heap.
	 * 
	 * @param child
	 *            the index of the child.
	 * @return an integer with the child's parent's index.
	 */
	public int getParent(int child);
}
