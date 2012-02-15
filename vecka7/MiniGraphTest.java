package alda;

import static org.junit.Assert.*;

import org.junit.*;

public class MiniGraphTest {

	private MiniGraph<Character> graph = new MyGraph<Character>(); // MyGraph
																	// ersätts
																	// med
																	// namnet på
																	// din
																	// graftyp

	private static final int ORIGINAL_NUMBER_OF_NODES = 7;
	private static final int ORIGINAL_NUMBER_OF_EDGES = 12;
	private static final int ORIGINAL_EDGE_WEIGHT = 53;

	@Before
	public void setUp() {
		graph.addNode('A');
		graph.addNode('B');
		graph.addNode('C');
		graph.addNode('D');
		graph.addNode('E');
		graph.addNode('F');
		graph.addNode('G');

		graph.connectNodes('A', 'B', 2);
		graph.connectNodes('A', 'C', 4);
		graph.connectNodes('A', 'D', 1);
		graph.connectNodes('B', 'D', 3);
		graph.connectNodes('B', 'E', 10);
		graph.connectNodes('C', 'D', 2);
		graph.connectNodes('C', 'F', 5);
		graph.connectNodes('D', 'E', 7);
		graph.connectNodes('D', 'F', 8);
		graph.connectNodes('D', 'G', 4);
		graph.connectNodes('E', 'G', 6);
		graph.connectNodes('F', 'G', 1);
	}

	@Test
	public void testBasicFeatures() {
		assertTrue(graph.contains('A'));
		assertTrue(graph.contains('E'));
		assertTrue(graph.contains('G'));
		assertFalse(graph.contains('H'));
		assertFalse(graph.contains('Q'));
		assertFalse(graph.contains('Ä'));

		assertTrue(graph.edgeExistsBetween('A', 'B'));
		assertTrue(graph.edgeExistsBetween('D', 'G'));
		assertTrue(graph.edgeExistsBetween('G', 'E'));
		assertTrue(graph.edgeExistsBetween('D', 'C'));
		assertFalse(graph.edgeExistsBetween('A', 'G'));
		assertFalse(graph.edgeExistsBetween('E', 'C'));
		assertFalse(graph.edgeExistsBetween('D', 'H'));
		assertFalse(graph.edgeExistsBetween('I', 'F'));

		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT, graph.getTotalEdgeWeight());
	}

	@Test
	public void testAddNode() {
		graph.addNode('E'); // Duplicate
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());

		graph.addNode('H');
		assertEquals(ORIGINAL_NUMBER_OF_NODES + 1, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT, graph.getTotalEdgeWeight());
	}

	@Test
	public void testConnectNodes() {
		graph.connectNodes('A', 'F', -1); // negative cost
		graph.connectNodes('A', 'B', 1); // Already connected
		graph.connectNodes('G', 'D', 2); // already connected
		graph.connectNodes('H', 'A', 3); // H doesn't exists
		graph.connectNodes('D', 'H', 4);// H doesn't exists
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT, graph.getTotalEdgeWeight());
		assertFalse(graph.edgeExistsBetween('A', 'F'));
		assertTrue(graph.edgeExistsBetween('B', 'A'));
		assertTrue(graph.edgeExistsBetween('D', 'G'));
		assertFalse(graph.edgeExistsBetween('A', 'H'));
		assertFalse(graph.edgeExistsBetween('H', 'D'));

		graph.connectNodes('E', 'A', 9);
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES + 1, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT + 9, graph.getTotalEdgeWeight());
		assertTrue(graph.edgeExistsBetween('A', 'E'));
		assertTrue(graph.edgeExistsBetween('E', 'A'));

		graph.connectNodes('F', 'E', 11);
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES + 2, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT + 20, graph.getTotalEdgeWeight());
		assertTrue(graph.edgeExistsBetween('F', 'E'));
		assertTrue(graph.edgeExistsBetween('E', 'F'));
	}

	@Test
	public void testMinimumSpanningTree() {
		MiniGraph<Character> tree = graph.generateMinimumSpanningTree();
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT, graph.getTotalEdgeWeight());
		assertEquals(ORIGINAL_NUMBER_OF_NODES, tree.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_NODES - 1, tree.getNumberOfEdges());
		assertEquals(16, tree.getTotalEdgeWeight());
	}

	@Test
	public void testLoop() {
		graph.connectNodes('E', 'E', 2);
		assertEquals(ORIGINAL_NUMBER_OF_NODES, graph.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_EDGES + 1, graph.getNumberOfEdges());
		assertEquals(ORIGINAL_EDGE_WEIGHT + 2, graph.getTotalEdgeWeight());

		MiniGraph<Character> tree = graph.generateMinimumSpanningTree();
		assertEquals(ORIGINAL_NUMBER_OF_NODES, tree.getNumberOfNodes());
		assertEquals(ORIGINAL_NUMBER_OF_NODES - 1, tree.getNumberOfEdges());
		assertEquals(16, tree.getTotalEdgeWeight());
	}

	@Test
	public void testEmptyGraph() {
		graph = new MyGraph<Character>(); // MyGraph ersätts med namnet på din
											// graftyp
		graph.connectNodes('A', 'F', 1);
		assertEquals(0, graph.getNumberOfNodes());
		assertEquals(0, graph.getNumberOfEdges());
		assertEquals(0, graph.getTotalEdgeWeight());

		MiniGraph<Character> tree = graph.generateMinimumSpanningTree();
		assertEquals(0, tree.getNumberOfNodes());
		assertEquals(0, tree.getNumberOfEdges());
		assertEquals(0, tree.getTotalEdgeWeight());
	}

}
