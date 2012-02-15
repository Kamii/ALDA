import java.util.*;
/**
 * Leon Hennings
 * Kamyar Sajjadi
 */

public class MyGraph<T extends Comparable<? super T>> implements MiniGraph<T> {
    private HashSet<T> nodes = new HashSet<T>(); 
    private ArrayList<Edge<T>> edges= new ArrayList<Edge<T>>();
    
    private static class Edge<T> implements Comparable<Edge<T>>{
        private T node1;
        private T node2;
        private int weight;
        public int getW(){
            return weight;
        }
        public T getN1(){
            return node1;
        }
        public T getN2(){
            return node2;
        }
        public int compareTo(Edge other){
            if(other.weight < weight)
                return 1;
            else if (other.weight < weight)
                return -1;
            else
                return 0;
        }

        public Edge(T n1, T n2, int w){
            node1 = n1;    
            node2 = n2;    
            weight = w;
        }
    }
	/**
	 * Method for adding a node to the graph. Silently ignores any duplicates
	 * @param n
	 *            The node to add to the graph.
	 */
	public void addNode(T n){
        if(!nodes.contains(n))
            nodes.add(n);
    }
	/**
	 * Method for removing a node from the graph. Before the node is removed,
	 * all edges associated with the node must be removed. Silently ignores any
	 * nodes not already in the graph or nodes that still have edges associated
	 * with them.
	 */
	public void removeNode(T n){}
	/**
	 * Method for creating an unidirectional edge between two nodes. Does
	 * nothing if the cost is negative, the edges are already connected, or if
	 * one or more of the nodes doesn't exists.
	 * @param n1
	 *            The first node to create an edge between
	 * @param n2
	 *            The second node to create an edge between
	 * @param weight
	 *            The cost for traversing the edge
	 */
	public void connectNodes(T n1, T n2, int weight){
        if(!(weight<0))
            if(nodes.contains(n1) && nodes.contains(n2))
                if(!edgeExistsBetween(n1, n2)){
                    Edge<T> e = new Edge<T>(n1, n2, weight);
                    edges.add(e);
                }
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
	 * @param n1
	 *            The first node that identifies the edge.
	 * @param n2
	 *            The second node that identifies the edge.
	 * @return true if and edge exists between n1 and n2, otherwise false.
	 */
	public boolean edgeExistsBetween(T n1, T n2){
        for(Edge e : edges){
            if(e.node1 == n1 && e.node2 == n2
                ||e.node1 == n2 && e.node2 == n1)
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
        int tot = 0;
        for(Edge e : edges){
            tot += e.weight;
        }
        return tot;
    }

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
	public MyGraph<T> generateMinimumSpanningTree(){
        MyGraph<T> tree = new MyGraph<T>();
        PriorityQueue<Edge> deck = new PriorityQueue<Edge>(edges);
        
            int i=0;
        while(!deck.isEmpty()){
            Edge<T> tmp = deck.poll();
            i++;
            System.out.println("n1:  " + tmp.getN1()+" n2: "+ tmp.getN2()+" w:  "+ tmp.getW());
            System.out.println(edgeExistsBetween(tmp.getN1(), tmp.getN2()));
            if(tree.nodes.contains(tmp.getN1()) && tree.nodes.contains(tmp.getN2()) && !tree.edgeExistsBetween(tmp.getN1(), tmp.getN2())){
            
                System.out.println(" Added 1");
                tree.addNode(tmp.getN1());
                tree.addNode(tmp.getN2());
                tree.connectNodes(tmp.getN1(), tmp.getN2(), tmp.getW());
            }
            else if( !tree.nodes.contains(tmp.getN1()) || !tree.nodes.contains(tmp.getN2())){
                System.out.println(" Added 2");
                tree.addNode(tmp.getN1());
                tree.addNode(tmp.getN2());
                tree.connectNodes(tmp.getN1(), tmp.getN2(), tmp.getW());
            }
        }
        return tree;
    }
}
