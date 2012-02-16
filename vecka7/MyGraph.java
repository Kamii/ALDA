import java.util.*;

/**
 *@author Leon Hennings
 *@author Kamyar Sajjadi
 */

public class MyGraph<T extends Comparable<? super T>> implements MiniGraph<T> 
{
	private HashSet<T> nodes = new HashSet<T>(); 
	private ArrayList<Edge<T>> edges= new ArrayList<Edge<T>>();

	/**
	 * This is the Edge class. 
	 * this class is pretty straightforward.
	 */
	private static class Edge<T> implements Comparable<Edge<T>>
	{
		private T node1;
		private T node2;
		private int weight;

		/**
		 * Constructor
		 * @param n1 One of the nodes
		 * @param n2 The other node ^^
		 * @param w weight of the edge
		 */
		public Edge(T n1, T n2, int w){
			node1 = n1;    
			node2 = n2;    
			weight = w;
		}

		/**
		 * GetMethod for weight
		 * @return int weight
		 */
		public int getW(){
			return weight;
		}

		/**
		 * GetMethod for node1
		 * @return T node1
		 */
		public T getN1(){
			return node1;
		}

		/**
		 * GetMethod for node2
		 * @return T node2
		 */
		public T getN2(){
			return node2;
		}

		/**
		 * A basic compareTo method 
		 * this method is comparing the weight of the edges
		 * @return int 
		 */
		public int compareTo(Edge other){
			if(other.weight < weight)
				return 1;
			else if (other.weight < weight)
				return -1;
			else
				return 0;
		}

		/**
		 * toString
		 * @return String 
		 */
		public String toString()
		{
			return node1+" "+ node2+" Weight: "+weight;
		}
	}

	/**
	 * Method for adding a node to the graph. Silently ignores any duplicates
	 * @param n The node to add to the graph.
	 */
	public void addNode(T n)
	{
		nodes.add(n);
	}

	/**
	 * Method for removing a node from the graph. Before the node is removed,
	 * all edges associated with the node must be removed. Silently ignores any
	 * nodes not already in the graph or nodes that still have edges associated
	 * with them.
	 *
	 * NOT IMPLEMENTED
	 */
	public void removeNode(T n){}

	/**
	 * Method for creating an unidirectional edge between two nodes. Does
	 * nothing if the cost is negative, the edges are already connected, or if
	 * one or more of the nodes doesn't exists.
	 * @param n1 The first node to create an edge between
	 * @param n2 The second node to create an edge between
	 * @param weight The cost for traversing the edge
	 */
	public void connectNodes(T n1, T n2, int weight)
	{
		if(!(weight<0))
			if(nodes.contains(n1) && nodes.contains(n2))
				if(!edgeExistsBetween(n1, n2))
				{
					Edge<T> e = new Edge<T>(n1, n2, weight);
					edges.add(e);
				}
	}

	/**
	 * Method for removing an edge between two nodes. Does nothing if the edge
	 * doesn't exists, or if one or more of the nodes doesn't exists.
	 * 
	 * NOT IMPLEMENTED
	 *
	 * @param n1 The first node that identifies the edge.
	 * @param n2 The second node that identifies the edge.
	 */
	public void disconnectNodes(T n1, T n2){}

	/**
	 * Method for searching the graph for a certain node. If the node is present
	 * in the graph, the method returns true, otherwise, it returns false.
	 * 
	 * @return boolean true if the graph contains n, otherwise false.
	 */
	public boolean contains(T n){
		return nodes.contains(n);
	}

	/**
	 * Method for finding the number of nodes in the graph.
	 * 
	 * @returns int The number of nodes in the graph.
	 */
	public int getNumberOfNodes(){
		return nodes.size();
	}

	/**
	 * Checks if there exists and edge between nodes n1 and n2. Used for testing
	 * purposes.
	 * 
	 * @param n1 The first node that identifies the edge.
	 * @param n2 The second node that identifies the edge.
	 * @return true if and edge exists between n1 and n2, otherwise false.
	 */
	public boolean edgeExistsBetween(T n1, T n2){
		for(Edge e : edges){
			if(e.node1 == n1 && e.node2 == n2
					|| e.node1 == n2 && e.node2 == n1)
				return true;
		}
		return false;
	}

	/**
	 * Gets the number of edges in the graph. Used for testing purposes.
	 * 
	 * @return the number of edges in the graph.
	 */
	public int getNumberOfEdges(){
		return edges.size();
	}

	/**
	 * Gets the total weight of all edges. Used for testing purposes.
	 * 
	 * @return the total weight of all the edges
	 */
	public int getTotalEdgeWeight(){
		int total = 0;
		for(Edge e : edges){
			total += e.weight;
		}
		return total;
	}

	/**
	 * Method for calculating a minimum spanning tree for the graph. 
	 * This method is generating the minimum spanning tree. We are using kruskals 
	 * algoritm. 
	 *
	 * @return tree Graph A new instance of the Graph class, representing a minimal
	 * spanning tree. 
	 */
	public MyGraph<T> generateMinimumSpanningTree(){
		// grafen som ska returneras
		MyGraph<T> tree = new MyGraph<T>();
		// bågarna sorterade efter minst först
		PriorityQueue<Edge> deck = new PriorityQueue<Edge>(edges);

		while(!deck.isEmpty())
		{
			Edge<T> tmp = deck.poll();

			// Om ingen av noderna finns med i tree så lägger vi till noderna i tree och 
			// kopplar dessa. Notera att addAndConnectToMinimumSpanningTree har en 
			// kontroll så att det i inte redan finns en kopling mellan noderna. I detta 
			// fall behövs det då en nod som är kopplad till sig själv ej ska läggas till. 
			if( (!tree.nodes.contains(tmp.getN1()) || !tree.nodes.contains(tmp.getN2()) )) 
			{
				addAndConnectToMinimumSpanningTree(tmp, tree);
			}

			// Annars om noderna redan finns med i vårat träd så ska vi eventuellt slå 
			// ihop träden till ett större träd. Även här är det viktigt med kontrollen 
			// som ligger i addAndConnectToMinimumSpanningTree detta för att inte bågen 
			// ska läggas till om de ligger i samma träd. Om kontrollen ej hade varit med
			// skulle det kunna uppstå cykler. 
			else if(tree.nodes.contains(tmp.getN1()) && tree.nodes.contains(tmp.getN2()))
			{
				addAndConnectToMinimumSpanningTree(tmp,tree);
			}
		}
		return tree;
	}

	/**
	 * Internal method for adding nodes and connect them. 
	 * Sorry for the long method name. 
	 * @param tmp the edge with all info that we need to use. 
	 * @param tree the forest we are building. 
	 */
	private void addAndConnectToMinimumSpanningTree(Edge<T> tmp, MyGraph<T> tree)
	{
		if(!tree.depthFirstSearch(tmp.getN1(), tmp.getN2()))
		{
			tree.addNode(tmp.getN1());
			tree.addNode(tmp.getN2());
			tree.connectNodes(tmp.getN1(), tmp.getN2(), tmp.getW());
		}
	}

	/**
	 * Depth-first search method.
	 * This method will return true if it finds a
	 * path between from-node and to-node
	 * @param from from-node
	 * @param to to-node
	 */
	private boolean depthFirstSearch(T from, T to)
	{
		Set<T> visited = new HashSet<T>();
		depthFirstSearch(from, visited);
		return visited.contains(to);
	}

	/**
	 * Internal method for depthFirstSearch(T, T).
	 * Building the HashSet with rekursion. 
	 * @param T from-node
	 * @param visited the hashset
	 */
	private void depthFirstSearch(T from, Set<T> visited)
	{
		visited.add(from);

		for(Edge<T> e : edges)
		{
			T tmp = null;

			// Kollar om någon av noderna är den noden som vi ska utgå från.
			// sedan sätter den destinationsnoden till tmp och gör ett rekursivt 
			// anrop.
			if(e.getN1().equals(from) || e.getN2().equals(from))
				tmp = e.getN1().equals(from) ? e.getN2(): e.getN1();

			if(!visited.contains(tmp))
				depthFirstSearch(tmp, visited);
		}
	}
}

