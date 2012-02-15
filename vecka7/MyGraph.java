package alda;

/**
 * 
 */

public class MyGraph implements MiniGraph<T extends Comparable<? super T>> {
    private T /*datastruktur*/
    
	/**
	 * Method for adding a node to the graph. Silently ignores any duplicates
	 * @param n
	 *            The node to add to the graph.
	 */
	public void addNode(T n){

    }

	/**
	 * Method for removing a node from the graph. Before the node is removed,
	 * all edges associated with the node must be removed. Silently ignores any
	 * nodes not already in the graph or nodes that still have edges associated
	 * with them.
	 * 
	 * THIS IS AN OPTIONAL METHOD THAT YOU DO NOT NEED TO IMPLEMENT UNLESS YOU
	 * WANT TO. THE TEST CASES DOES NOT USE IT, SO IF YOU IMPLEMENT IT YOU NEED
	 * TO TEST IT YOURSELVES.
	 * 
	 */
	public void removeNode(T n);

	/**
	 * Method for creating an unidirectional edge between two nodes. Does
	 * nothing if the cost is negative, the edges are already connected, or if
	 * one or more of the nodes doesn't exists.
	 * 
	 * @param n1
	 *            The first node to create an edge between
	 * @param n2
	 *            The second node to create an edge between
	 * @param weight
	 *            The cost for traversing the edge
	 */
	public void connectNodes(T n1, T n2, int weight){
    
    }

	/**
	 * Method for removing an edge between two nodes. Does nothing if the edge
	 * doesn't exists, or if one or more of the nodes doesn't exists.
	 * 
	 * THIS IS AN OPTIONAL METHOD THAT YOU DO NOT NEED TO IMPLEMENT UNLESS YOU
	 * WANT TO. THE TEST CASES DOES NOT USE IT, SO IF YOU IMPLEMENT IT YOU NEED
	 * TO TEST IT YOURSELVES.
	 * 
	 * @param n1
	 *            The first node that identifies the edge.
	 * @param n2
	 *            The second node that identifies the edge.
	 */
	public void disconnectNodes(T n1, T n2);

	/**
	 * Method for searching the graph for a certain node. If the node is present
	 * in the graph, the method returns true, otherwise, it returns false.
	 * 
	 * @return boolean true if the graph contains n, otherwise false.
	 */
	public boolean contains(T n);

	/**
	 * Method for finding the number of nodes in the graph.
	 * 
	 * @returns int The number of nodes in the graph.
	 */
	public int getNumberOfNodes();

	/**
	 * Checks if there exists and edge between nodes n1 and n2. Used for testing
	 * purposes.
	 * 
	 * @param n1
	 *            The first node that identifies the edge.
	 * @param n2
	 *            The second node that identifies the edge.
	 * @return true if and edge exists between n1 and n2, otherwise false.
	 */
	public boolean edgeExistsBetween(T n1, T n2);

	/**
	 * Gets the number of edges in the graph. Used for testing purposes.
	 * 
	 * @return the number of edges in the graph.
	 */
	public int getNumberOfEdges();

	/**
	 * Gets the total weight of all edges. Used for testing purposes.
	 * 
	 * @return the total weight of all the edges
	 */
	public int getTotalEdgeWeight();

	/**
	 * Method for calculating a minimum spanning tree for the graph. The method
	 * is not allowed to modify the graph during the calculation, ie. the
	 * original graph must be identical to how the graph looked before the
	 * invocation of the method.
	 * 
	 * The minimum spanning tree is calculated using either Kruskal's or Prim's
	 * algorithm.
	 * 
	 * The algorithm is allowed to assume that the graph is connected and fail
	 * in any way it like, including silently, if it isn't.
	 * 
	 * @return Graph A new instance of the Graph class, representing a minimal
	 *         spanning tree.
	 */
	public MiniGraph<T> generateMinimumSpanningTree(){

    }
}
