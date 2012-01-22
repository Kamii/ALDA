package alda;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class SimpleLinkedListTester {

	@Before
	public void setUp() {
		list.add(100);
		list.add(200);
		list.add(300);
	}

	private List<Integer> list = new SimpleLinkedList<Integer>();

	private void testContent(List<Integer> list, Integer... expectedData) {
		assertEquals("Different length of list and expected data",
				expectedData.length, list.size());

		Iterator<Integer> iter = list.iterator();
		for (int n = 0; n < expectedData.length; n++) {
			assertEquals(expectedData[n], iter.next());
			assertTrue(list.contains(expectedData[n]));
			assertEquals(expectedData[n], list.get(n));
		}
	}

	private void testContent(Integer... expectedData) {
		testContent(list, expectedData);
	}

	@Test
	public void simpleAdd() {
		testContent(100, 200, 300);
		list.add(3, 400);
		list.add(0, 500);
		list.add(2, 600);
		testContent(500, 100, 600, 200, 300, 400);
	}

	@Test
	public void addAndRemove() {
		list.remove(1);
		list.remove(new Integer(100));
		testContent(300);
		list.add(400);
		list.add(2, 500);
		list.add(0, 600);
		testContent(600, 300, 400, 500);
		list.clear();
		assertTrue(list.isEmpty());
		testContent();
		list.add(0, 700);
		list.add(0, 800);
		list.add(2, 900);
		testContent(800, 700, 900);
	}

	@Test
	public void addAll() {
		Set<Integer> data = new HashSet<Integer>();
		data.add(400);
		data.add(500);
		data.add(600);
		data.add(700);

		list.addAll(data);

		assertEquals(7, list.size());
		assertTrue(list.containsAll(data));
		for (Integer i : data) {
			assertTrue(list.indexOf(i) >= 3);
		}

		data.clear();

		data.add(800);
		data.add(900);

		list.addAll(0, data);

		assertEquals(9, list.size());
		assertTrue(list.containsAll(data));
		for (Integer i : data) {
			assertTrue(list.indexOf(i) < 2);
		}

		data.clear();

		data.add(1000);

		list.addAll(2, data);
		assertEquals(10, list.size());
		assertTrue(list.containsAll(data));
		assertEquals(2, list.indexOf(1000));
	}

	@Test
	public void duplicatesAndIndexOf() {
		list.add(100);
		list.add(200);
		testContent(100, 200, 300, 100, 200);

		assertEquals(0, list.indexOf(100));
		assertEquals(1, list.indexOf(200));
		assertEquals(-1, list.indexOf(555));
		assertEquals(-1, list.lastIndexOf(555));
		assertEquals(3, list.lastIndexOf(100));
		assertEquals(4, list.lastIndexOf(200));
		assertEquals(2, list.indexOf(300));
		assertEquals(2, list.lastIndexOf(300));

		list.remove(new Integer(100));
		list.remove(new Integer(300));

		testContent(200, 100, 200);
	}

	@Test
	public void listIterator() {
		ListIterator<Integer> iter = list.listIterator();
		assertTrue(iter.hasNext());
		assertFalse(iter.hasPrevious());
		assertEquals(new Integer(100), iter.next());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(200), iter.next());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(300), iter.next());

		assertFalse(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(300), iter.previous());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(300), iter.next());
		assertEquals(new Integer(300), iter.previous());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(200), iter.previous());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());
		assertEquals(new Integer(100), iter.previous());

		assertTrue(iter.hasNext());
		assertFalse(iter.hasPrevious());
		assertEquals(new Integer(100), iter.next());

		assertTrue(iter.hasNext());
		assertTrue(iter.hasPrevious());

	}

	@Test
	public void nullItems() {
		list.add(null);
		assertEquals(4, list.size());
		list.add(0, null);
		assertEquals(5, list.size());
		list.remove(null);
		assertEquals(4, list.size());
		assertEquals(3, list.indexOf(null));
	}

	@Test
	public void removeAll() {
		Set<Integer> data = new HashSet<Integer>();
		data.add(400);
		data.add(100);
		data.add(300);

		list.removeAll(data);
		testContent(200);
	}

	@Test
	public void removeAllWithDuplicates() {
		list.add(100);
		list.add(200);
		list.add(500);
		list.add(500);

		Set<Integer> data = new HashSet<Integer>();
		data.add(400);
		data.add(100);
		data.add(100);
		data.add(300);
		data.add(500);

		list.removeAll(data);
		testContent(200, 200);
	}

	@Test
	public void retainAll() {
		Set<Integer> data = new HashSet<Integer>();
		data.add(400);
		data.add(100);
		data.add(300);

		list.retainAll(data);
		testContent(100, 300);
	}

	@Test
	public void set() {
		list.set(0, 111);
		list.set(1, 222);
		list.set(2, 333);
		testContent(111, 222, 333);
	}

	private void compareIterators(ListIterator<Integer> i1,
			ListIterator<Integer> i2) {
		assertEquals(i1.hasNext(), i2.hasNext());
		assertEquals(i1.hasPrevious(), i2.hasPrevious());
		assertEquals(i1.nextIndex(), i2.nextIndex());
		assertEquals(i1.previousIndex(), i2.previousIndex());
	}

	private void iterateForward(ListIterator<Integer> i1,
			ListIterator<Integer> i2) {
		compareIterators(i1, i2);
		while (i1.hasNext()) {
			assertEquals(i1.next(), i2.next());
			compareIterators(i1, i2);
		}
	}

	private void iterateBackward(ListIterator<Integer> i1,
			ListIterator<Integer> i2) {
		compareIterators(i1, i2);
		while (i1.hasPrevious()) {
			assertEquals(i1.previous(), i2.previous());
			compareIterators(i1, i2);
		}
	}

	private Collection<Integer> generateCollection(int maxValue, int count,
			Random rnd) {
		List<Integer> collection = new LinkedList<Integer>();

		for (int n = 0; n < count; n++) {
			collection.add(rnd.nextInt(maxValue));
		}

		return collection;
	}

	@Test
	public void random() {
		final int ITERATIONS = 2000;
		final int MAX_VALUE = 20;

		Random rnd = new Random();
		list.clear();
		List<Integer> oracle = new ArrayList<Integer>();

		for (int test = 0; test < ITERATIONS; test++) {
			int value = rnd.nextInt(MAX_VALUE);
			int index;
			Collection<Integer> c;

			int operation = rnd.nextInt(17);
			// System.err.println(operation);
			switch (operation) {
			// insert
			case 0:
				assertEquals(oracle.add(value), list.add(value));
				break;
			case 1:
			case 2:
				index = rnd.nextInt(oracle.size() + 1);
				list.add(index, value);
				oracle.add(index, value);
				break;
			// remove
			case 3:
			case 4:
				if (!oracle.isEmpty()) {
					index = rnd.nextInt(oracle.size());
					list.remove(index);
					oracle.remove(index);
				}
				break;
			case 5:
			case 6:
			case 7:
				if (!oracle.isEmpty()) {
					assertEquals(oracle.remove(new Integer(value)),
							list.remove(new Integer(value)));
				}
				break;
			case 8:
				list.clear();
				oracle.clear();
				break;
			case 9:
				iterateForward(oracle.listIterator(), list.listIterator());
				break;
			case 10:
				if (oracle.size() > 0)
					index = rnd.nextInt(oracle.size());
				else
					index = 0;
				iterateForward(oracle.listIterator(index),
						list.listIterator(index));
				break;
			case 11:
				iterateBackward(oracle.listIterator(), list.listIterator());
				break;
			case 12:
				if (oracle.size() > 0)
					index = rnd.nextInt(oracle.size());
				else
					index = 0;
				iterateBackward(oracle.listIterator(index),
						list.listIterator(index));
				break;
			case 13:
				c = generateCollection(MAX_VALUE, 10, rnd);
				assertEquals(oracle.addAll(c), list.addAll(c));
				break;
			case 14:
				index = rnd.nextInt(oracle.size() + 1);
				c = generateCollection(MAX_VALUE, 10, rnd);
				assertEquals(oracle.addAll(index, c), list.addAll(index, c));
				break;
			case 15:
				c = generateCollection(MAX_VALUE, 10, rnd);
				assertEquals(oracle.removeAll(c), list.removeAll(c));
				break;
			case 16:
				c = generateCollection(MAX_VALUE, 10, rnd);
				assertEquals(oracle.retainAll(c), list.retainAll(c));
				break;
			default:
				throw new RuntimeException("No such case available");
			}

			assertEquals(oracle.isEmpty(), list.isEmpty());
			assertEquals(oracle.size(), list.size());
		}
	}

}
