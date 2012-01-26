import static org.junit.Assert.*;

import org.junit.*;

/**
 * Början på en testsvit för borttag ur ett AVL-träd. Testar inte alla
 * rotationstyper, så vill ni vara hyfsat säkra på att er kod fungerar bör ni
 * utöka testfallen.
 */
public class AvlTreeTester {

	private AvlTree<Integer> tree = new AvlTree<Integer>();

	private void checkTree(int expectedHeight, int expectedSize,
			int expectedMin, int expectedMax) {
		assertEquals(expectedSize, tree.size());
		assertEquals(new Integer(expectedMin), tree.findMin());
		assertEquals(new Integer(expectedMax), tree.findMax());
		assertTrue(tree.hasCorrectHeightInfo());
		assertTrue(tree.isSearchTree());
	}

	@Test 
	public void rightRotationRemove()
	{
		assertTrue(tree.isEmpty());
		tree.insert(13);
		tree.insert(16);
		tree.insert(17);
		tree.insert(2);
		tree.remove(2);
		checkTree(1, 3, 13, 17);
		tree.makeEmpty();
	}

	@Test 
	public void leftRotationRemove()
	{
		assertTrue(tree.isEmpty());
		tree.insert(56);
		tree.insert(43);
		tree.insert(89);
		tree.insert(30);
		tree.remove(89);
		checkTree(1, 3, 30, 56);
		tree.makeEmpty();
	}

	@Test
	public void rightLeftRotation()
	{
		assertTrue(tree.isEmpty());
		tree.insert(43);
		tree.insert(10);
		tree.insert(60);
		tree.insert(6);
		tree.insert(67);
		tree.insert(54);
		tree.insert(46);
		tree.insert(56);
		tree.remove(6);
		checkTree(2, 7, 10, 67);
		tree.makeEmpty();
	}

	@Test
	public void leftRightRotation()
	{
		assertTrue(tree.isEmpty());
		tree.insert(54);
		tree.insert(10);
		tree.insert(73);
		tree.insert(24);
		tree.insert(9);
		tree.insert(75);
		tree.insert(23);
		tree.insert(25);
		tree.remove(75);
		checkTree(2, 7, 9, 73);
		tree.makeEmpty();
	}

	@Test
	public void aLotOfNodes()
	{
		assertTrue(tree.isEmpty());
		final int MUCH_NODES = 5000;

		for(int i = 0; i <= MUCH_NODES; i++)
			tree.insert(i);
		
		for(int i = 0; i <=5000; i++)
		{
			tree.remove(i);
			assertTrue(tree.hasCorrectHeightInfo());
			assertTrue(tree.isSearchTree());
			assertEquals(MUCH_NODES-i, tree.size());
		}
	}

	@Test 
	public void isEmptyTest()
	{
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
	}

	@Test
	public void removeFromEmptyTree()
	{
		tree.remove(1);
		assertEquals(-1, tree.maxHeight());
		assertTrue(tree.hasCorrectHeightInfo());
		assertTrue(tree.isSearchTree());
		assertEquals(0, tree.size());
	}

	@Test
	public void addDuplicate()
	{
		tree.insert(5);
		tree.insert(5);
		checkTree(0, 1, 5, 5);
	}

	@Test
	public void removeNonExisting()
	{
		tree.insert(2);
		tree.remove(1);
		checkTree(0, 1, 2, 2);
	}

	@Test
	public void basicAddAndRemove() {
		tree.insert(1);
		checkTree(0, 1, 1, 1);
		assertFalse(tree.isEmpty());
		tree.insert(2);
		checkTree(1, 2, 1, 2);
		tree.insert(3);
		checkTree(1, 3, 1, 3);

		for (int n = 4; n <= 10; n++) {
			tree.insert(n);
			checkTree(3, n, 1, n);
		}

		tree.remove(1);
		checkTree(3, 9, 2, 10);
		assertFalse(tree.isEmpty());

		tree.remove(2);
		checkTree(3, 8, 3, 10);

		tree.remove(9);
		checkTree(2, 7, 3, 10);

		tree.insert(1);
		checkTree(3, 8, 1, 10);

		tree.insert(0);
		checkTree(3, 9, 0, 10);

		tree.remove(5);
		checkTree(3, 8, 0, 10);

	}

	@Test
	public void random() {
	}

}
