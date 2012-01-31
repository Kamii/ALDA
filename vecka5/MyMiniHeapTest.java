package alda;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class MyMiniHeapTest {
	private MyMiniHeap<Integer> heap = new MyMiniHeap<Integer>(3);

	private void insert(int... numbers) {
		for (int i : numbers) {
			heap.insert(i);
		}
	}

	@Test
	public void testDeleteMin() {
		insert(3, 2, 5, 1, 6);
		assertTrue(heap.size() == 5);
		assertTrue(heap.findMin() == 1);
		 
		assertTrue(heap.deleteMin() == 1);
		assertTrue(heap.findMin() == 2);
		assertTrue(heap.size() == 4);
		
		assertTrue(heap.deleteMin() == 2);
		assertTrue(heap.findMin() == 3);
		assertTrue(heap.size() == 3);
		
		assertTrue(heap.deleteMin() == 3);
		assertTrue(heap.findMin() == 5);
		assertTrue(heap.size() == 2);
		
		assertTrue(heap.deleteMin() == 5);
		assertTrue(heap.findMin() == 6);
		assertTrue(heap.size() == 1);
		
		assertTrue(heap.deleteMin() == 6);
		assertTrue(heap.size() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationWithOneChild(){
		heap=new MyMiniHeap<Integer>(1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreationWithZeroChildren(){
		heap=new MyMiniHeap<Integer>(0);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testEmptyDeleteMin() {
		heap.deleteMin();
	}

	@Test
	public void testFindMin() {
		insert(3, 4, 6, 7, 1, 8, 2, 5);
		assertTrue(heap.size() == 8);
		assertTrue(heap.findMin() == 1);

		heap.makeEmpty();
		assertTrue(heap.isEmpty());
		
		insert(182, 64, 233, 906, 42, 678);
		assertTrue(heap.size() == 6);
		assertTrue(heap.findMin() == 42);
		
		assertTrue(heap.deleteMin() == 42);
		assertTrue(heap.size() == 5);
		assertTrue(heap.findMin() == 64);
		
		assertTrue(heap.deleteMin() == 64);
		assertTrue(heap.size() == 4);
		assertTrue(heap.findMin() == 182);
		
		assertTrue(heap.deleteMin() == 182);
		assertTrue(heap.size() == 3);
		assertTrue(heap.findMin() == 233);
		
		assertTrue(heap.deleteMin() == 233);
		assertTrue(heap.size() == 2);
		assertTrue(heap.findMin() == 678);
		
		assertTrue(heap.deleteMin() == 678);
		assertTrue(heap.size() == 1);
		assertTrue(heap.findMin() == 906);
		
		heap.insert(12);
		assertTrue(heap.findMin() == 12);
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyFindMin() {
		heap.findMin();
	}

	@Test
	public void testGetParent() {
		assertTrue(heap.getParent(2) == 1);
		assertTrue(heap.getParent(4) == 1);
		assertTrue(heap.getParent(5) == 2);
		assertTrue(heap.getParent(6) == 2);
		assertTrue(heap.getParent(7) == 2);
		assertTrue(heap.getParent(8) == 3);
		assertTrue(heap.getParent(10) == 3);
		assertTrue(heap.getParent(11) == 4);
		assertTrue(heap.getParent(13) == 4);
		assertTrue(heap.getParent(14) == 5);
		assertTrue(heap.getParent(16) == 5);
		
		MyMiniHeap<Integer> fourHeap = new MyMiniHeap<Integer>(4);
		assertTrue(fourHeap.getParent(2) == 1);
		assertTrue(fourHeap.getParent(5) == 1);
		assertTrue(fourHeap.getParent(6) == 2);
		assertTrue(fourHeap.getParent(7) == 2);
		assertTrue(fourHeap.getParent(8) == 2);
		assertTrue(fourHeap.getParent(9) == 2);
		assertTrue(fourHeap.getParent(10) == 3);
		assertTrue(fourHeap.getParent(13) == 3);
		assertTrue(fourHeap.getParent(14) == 4);
		assertTrue(fourHeap.getParent(17) == 4);
		
		MyMiniHeap<Integer> binaryHeap = new MyMiniHeap<Integer>(2);
		assertTrue(binaryHeap.getParent(2) == 1);
		assertTrue(binaryHeap.getParent(3) == 1);
		assertTrue(binaryHeap.getParent(4) == 2);
		assertTrue(binaryHeap.getParent(5) == 2);
		assertTrue(binaryHeap.getParent(6) == 3);
		assertTrue(binaryHeap.getParent(7) == 3);
		assertTrue(binaryHeap.getParent(8) == 4);
		assertTrue(binaryHeap.getParent(9) == 4);
		assertTrue(binaryHeap.getParent(10) == 5);
		assertTrue(binaryHeap.getParent(11) == 5);
		
		MyMiniHeap<Integer> fiveHeap = new MyMiniHeap<Integer>(5);
		assertTrue(fiveHeap.getParent(2) == 1);
		assertTrue(fiveHeap.getParent(6) == 1);
		assertTrue(fiveHeap.getParent(7) == 2);
		assertTrue(fiveHeap.getParent(8) == 2);
		assertTrue(fiveHeap.getParent(9) == 2);
		assertTrue(fiveHeap.getParent(10) == 2);
		assertTrue(fiveHeap.getParent(11) == 2);
		assertTrue(fiveHeap.getParent(12) == 3);
		assertTrue(fiveHeap.getParent(16) == 3);
		assertTrue(fiveHeap.getParent(17) == 4);
		assertTrue(fiveHeap.getParent(21) == 4);
		assertTrue(fiveHeap.getParent(22) == 5);
		assertTrue(fiveHeap.getParent(26) == 5);
		assertTrue(fiveHeap.getParent(27) == 6);
		assertTrue(fiveHeap.getParent(31) == 6);
		assertTrue(fiveHeap.getParent(32) == 7);
	}

	@Test
	public void testGetChild() {
		assertTrue(heap.getChild(1) == 2);
		assertTrue(heap.getChild(2) == 5);
		assertTrue(heap.getChild(3) == 8);
		assertTrue(heap.getChild(4) == 11);
		assertTrue(heap.getChild(5) == 14);
		assertTrue(heap.getChild(6) == 17);
		
		MyMiniHeap<Integer> fourHeap = new MyMiniHeap<Integer>(4);
		assertTrue(fourHeap.getChild(1) == 2);
		assertTrue(fourHeap.getChild(2) == 6);
		assertTrue(fourHeap.getChild(3) == 10);
		assertTrue(fourHeap.getChild(4) == 14);
		assertTrue(fourHeap.getChild(5) == 18);
		assertTrue(fourHeap.getChild(6) == 22);
		
		MyMiniHeap<Integer> binaryHeap = new MyMiniHeap<Integer>(2);
		assertTrue(binaryHeap.getChild(1) == 2);
		assertTrue(binaryHeap.getChild(2) == 4);
		assertTrue(binaryHeap.getChild(3) == 6);
		assertTrue(binaryHeap.getChild(4) == 8);
		assertTrue(binaryHeap.getChild(5) == 10);
		assertTrue(binaryHeap.getChild(6) == 12);
		
		MyMiniHeap<Integer> fiveHeap = new MyMiniHeap<Integer>(5);
		assertTrue(fiveHeap.getChild(1) == 2);
		assertTrue(fiveHeap.getChild(2) == 7);
		assertTrue(fiveHeap.getChild(3) == 12);
		assertTrue(fiveHeap.getChild(4) == 17);
		assertTrue(fiveHeap.getChild(5) == 22);
		assertTrue(fiveHeap.getChild(6) == 27);
		assertTrue(fiveHeap.getChild(7) == 32);
	}

	@Test
	public void testInsert() {
		heap.insert(3);
		assertTrue(heap.size() == 1);
		assertTrue(heap.findMin() == 3);

		heap.insert(5);
		assertTrue(heap.size() == 2);
		assertTrue(heap.findMin() == 3);

		heap.insert(7);
		assertTrue(heap.size() == 3);
		assertTrue(heap.findMin() == 3);
		
		heap.insert(2);
		assertTrue(heap.size() == 4);
		assertTrue(heap.findMin() == 2);
		
		heap.insert(1);
		assertTrue(heap.size() == 5);
		assertTrue(heap.findMin() == 1);
		
		assertTrue(heap.deleteMin() == 1);
		assertTrue(heap.size() == 4);
		assertTrue(heap.findMin() == 2);
		
		heap.insert(-1);
		assertTrue(heap.size() == 5);
		assertTrue(heap.findMin() == -1);
		
		heap.insert(3);
		assertTrue(heap.size() == 6);
		assertTrue(heap.findMin() == -1);
		
		assertTrue(heap.deleteMin() == -1);
		assertTrue(heap.size() == 5);
		assertTrue(heap.findMin() == 2);
		
		int length = heap.size();
		for(int i=0; i<length; i++)
			heap.deleteMin();
		
		assertTrue(heap.isEmpty());
		heap.insert(42);
		assertTrue(heap.size() == 1);
		assertTrue(heap.findMin() == 42);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalInsert() {
		heap.insert(null);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(heap.isEmpty());
		
		insert(1);
		assertFalse(heap.isEmpty());
		
		heap.makeEmpty();
		assertTrue(heap.isEmpty());
	}

	@Test
	public void testMakeEmpty() {
		insert(1,2,3);
		assertFalse(heap.isEmpty());
		
		heap.makeEmpty();
		assertTrue(heap.isEmpty());
	}

	@Test
	public void testSize() {
		assertTrue(heap.size() == 0);
		
		insert(1,2,3);
		assertTrue(heap.size() == 3);
		
		insert(5,7,-2);
		assertTrue(heap.size() == 6);
		
		assertTrue(heap.deleteMin() == -2);
		assertTrue(heap.size() == 5);
		
		assertTrue(heap.deleteMin() == 1);
		assertTrue(heap.size() == 4);
		
		heap.makeEmpty();
		assertTrue(heap.isEmpty());
		assertTrue(heap.size() == 0);
	}
	
	@Test
	public void testWithManyElements() {
		int nrOfElements = 1000;
		Random r = new Random();
		MyMiniHeap<Integer> largeHeap = new MyMiniHeap<Integer>(3);
		for(int i=0; i<nrOfElements; i++)
			largeHeap.insert(r.nextInt(nrOfElements));
		
		
		int smallest = largeHeap.findMin();
		for(int i=0; i<nrOfElements; i++) {
			int temp = largeHeap.deleteMin();
			assertTrue(smallest <= temp);
			smallest = temp;
		}
	}
}

