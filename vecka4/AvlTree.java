// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>>
{
	/**
	 * Construct the tree.
	 */
	public AvlTree( )
	{
		root = null;
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 */
	public void remove( AnyType x )
	{
		if(isEmpty())
			System.out.println("The tree is empty");
		else
			root = remove(x, root);
	}

	/**
	 * Internal method for remove.
	 * @param x the item(element) to remove.
	 * @param node the root node. 
	 * @return the root node after all changes. 
	 */
	public AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> node )
	{
	  if(node == null)
			return null;
		else if(x.compareTo(node.element) < 0) //Noden är mindre då går vi ner i vänstra barnet.
		{
			//Anropa remove med vänsterbarnet och sätt nodens vänsterbarn till det som remove kommer returnera. 
			node.left = remove(x, node.left); 

			//Om trädet är obalanserat så kollar vi vilken sida som är den tunga sidan och gör rotationerna.
			//Antingen är den "höger höger tung" eller så är den "höger vänster tung". 
			if(height(node.right) - height(node.left) == 2)
				if(height(node.right.right) >= height(node.right.left))
					node = rotateWithRightChild(node);
				else
					node = doubleWithRightChild(node);
				
			//Uppdatera vikten i noden. 
			node.height = maxHeight(node); 
		}
		else if(x.compareTo(node.element) > 0) //Noden är större då går vi ner i högra barnet.
		{
			//Anropa remove med högerbarnet och sätt det som returneras till noden högerbarn.
			node.right = remove(x, node.right);

			//Om trädet är obalanserat så kollar vi vilken sida som är den tunga sidan och gör rotationerna. 
			//Antingen är den "vänster vänster tung" eller så är den "vänster höger tung"
			if(height(node.left) - height(node.right) == 2)
				if(height(node.left.left) >= height(node.left.right)) 
					node = rotateWithLeftChild(node);
				else
					node = doubleWithLeftChild(node);
			
			//uppdatera vikten i noden. 
			node.height = maxHeight(node); 
		}
		else //Rätt nod är hittad. GRATTIS
			if(node.right == null) // Om den inte har något högerbarn ta det vänstra barnet. 
			{
				AvlNode<AnyType> tmpReturn = node.left;
				node = null; // Ta bort den noden vi inte ska ha kvar. 
				return tmpReturn; //returnerar det subträdet som ska kopplas. 
			}
			else if(node.left == null) // Om den inte har något vänsterbarn ta det högra barnet. 
			{
				AvlNode<AnyType> tmpReturn = node.right;
				node = null;
				return tmpReturn;
			}
			else // Noden har två barn. 
			{
				AvlNode<AnyType> tmpReturn = findMax(node.left); // Hämta den högsta noden i vänstra barnet. 
				remove(tmpReturn.element, node); //remove på findMax noden.
				node.element = tmpReturn.element;  //sätter nodens element till vänstra barnets högsta element. 
			}
		return node;
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin( )
	{
		if( isEmpty( ) )
			throw new UnderflowException( );
		return findMin( root ).element;
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax( )
	{
		if( isEmpty( ) )
			throw new UnderflowException( );
		return findMax( root ).element;
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if x is found.
	 */
	public boolean contains( AnyType x )
	{
		return contains( x, root );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree( )
	{
		if( isEmpty( ) )
			System.out.println( "Empty tree" );
		else
			printTree( root );
	}

	/**
	 * Return size of the tree.
	 */
	public int size()
	{
		return size(root);
	}

	/**
	 * Internal method to get the size.
	 * @param node the node that roots the subtree.
	 * @return the size of the tree (int).
	 */
	private int size(AvlNode<AnyType> node)
	{
		if(node == null)
			return 0;
		else
			return size(node.left) + size(node.right) + 1;
	}

	/**
	 * Return the max height of the tree.
	 */
	public int maxHeight()
	{
		return maxHeight(root);
	}

	/**
	 * Internal method to get the max height of the tree.
	 * @param node the node that roots the subtree.
	 * @return the max height of the tree. 
	 */
	private int maxHeight(AvlNode<AnyType> node)
	{
		int height = 0;
		
		if(node == null)
			return -1;
		else
			//returnera det högsta av det vänstra och det högra subträdet. 
			return Math.max(maxHeight(node.left)+1, maxHeight(node.right)+1);
	}

	/**
	 * See if the tree is a binary tree.
	 */
	public boolean isSearchTree()
	{
		return isSearchTree(root);
	}

	/**
	 * Internal method to control if the tree is a binary tree.
	 * @param node the node that roots the subtree.
	 * @return true if the tree is binary else false.
	 *
	 * Undersöker om trädet är ett sökträd genom att se att
	 * högerbarn har högre värde och vänsterbarn har lägre värde
	 * Ifall trädet är tomt returneras true.
	 * Ifall det inte finns ett vänsterbarn sätts boolean l till true.
	 * Annars jämförs elementets värde med vänsterbarnets värde.
	 * Om vänsterbarnet är lägre kallas isSearchTree rekursivt
	 * på vänsterbarnet och sätter det till dess returvärde.
	 * Annars blir l false.
	 * Sedan görs motsvarande för högerbarnet.
	 * Ifall både boolean l och boolean r satts till true
	 * returneras true.
	 */
	private boolean isSearchTree(AvlNode<AnyType> node)
	{
		boolean l, r;

		// Basecase
		if(isEmpty())
			return true;

		if(node.left == null)
			l = true;
		else
		{
			int left = node.element.compareTo(node.left.element);
			if(left > 0)
				l = isSearchTree(node.left);
			else
				l = false;
		}

		if(node.right == null)
			r = true;
		else
		{
			int right = node.element.compareTo(node.right.element);
			if(right < 0)
				r = isSearchTree(node.right);
			else
				r = false;
		}
		return l && r;
	}

	/**
	 * Controll if the height of the tree is correct.
	 */
	public boolean hasCorrectHeightInfo()
	{
		return hasCorrectHeightInfo(root);
	}

	/**
	 * Internal method to controll the height info
	 * @param node the node that we want to check
	 * @return true or false 
	 *
	 * Är absolut värdet av differansen för de två barnen 
	 * är större än 1 så är det något av subträden som 
	 * inte har korrekt höjd värde
	 * Annars, Om det barnnoden med högst vikt inte är 
	 * ett mindre än nodens höjd så har den fel höjd info
	 * Annars, returnera sant ifall de båda subträden har korrekt höjd
	 */
	private boolean hasCorrectHeightInfo(AvlNode<AnyType> node)
	{
		if(node == null)
			return true;

		int diff = height(node.left) - height(node.right);

		if(Math.abs(diff)>1)
			return false;
		else if(node.height - Math.max(height(node.left), height(node.right)) != 1)
			return false;
		else 
			return hasCorrectHeightInfo(node.right) && hasCorrectHeightInfo(node.left);
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x )
	{
		root = insert( x, root );
	}

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
	{
		if( t == null )
			return new AvlNode<AnyType>( x, null, null );

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
		{
			t.left = insert( x, t.left );
			if( height( t.left ) - height( t.right ) == 2 )
				if( x.compareTo( t.left.element ) < 0 )
					t = rotateWithLeftChild( t );
				else
					t = doubleWithLeftChild( t );
		}
		else if( compareResult > 0 )
		{
			t.right = insert( x, t.right );
			if( height( t.right ) - height( t.left ) == 2 )
				if( x.compareTo( t.right.element ) > 0 )
					t = rotateWithRightChild( t );
				else
					t = doubleWithRightChild( t );
		}
		else
			;  // Duplicate; do nothing
		t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.left != null )
			t = t.left;
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private AvlNode<AnyType> findMax( AvlNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.right != null )
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return true if x is found in subtree.
	 */
	private boolean contains( AnyType x, AvlNode<AnyType> t )
	{
		while( t != null )
		{
			int compareResult = x.compareTo( t.element );

			if( compareResult < 0 )
				t = t.left;
			else if( compareResult > 0 )
				t = t.right;
			else
				return true;    // Match
		}

		return false;   // No match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the tree.
	 */
	private void printTree( AvlNode<AnyType> t )
	{
		if( t != null )
		{
			printTree( t.left );
			System.out.println( t.element );
			printTree( t.right );
		}
	}

	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height( AvlNode<AnyType> t )
	{
		return t == null ? -1 : t.height;
	}

	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 )
	{
		AvlNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.left ), k2.height ) + 1;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 )
	{
		AvlNode<AnyType> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
		k2.height = Math.max( height( k2.right ), k1.height ) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 )
	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 )
	{
		k1.right = rotateWithLeftChild( k1.right );
		return rotateWithRightChild( k1 );
	}

	/**
	 * Class for thorwing exception. 
	 * No biggi
	 */
	private static class UnderflowException extends RuntimeException
	{
		public UnderflowException()
		{
		}

		public UnderflowException(String str)
		{
			super(str);
		}
	}

	private static class AvlNode<AnyType>
	{
		// Constructors
		AvlNode( AnyType theElement )
		{
			this( theElement, null, null );
		}

		AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
		{
			element  = theElement;
			left     = lt;
			right    = rt;
			height   = 0;
		}

		AnyType           element;      // The data in the node
		AvlNode<AnyType>  left;         // Left child
		AvlNode<AnyType>  right;        // Right child
		int               height;       // Height
	}


	/** The tree root. */
	private AvlNode<AnyType> root;


	// Test program
	public static void main( String [ ] args )
	{
		AvlTree<Integer> t = new AvlTree<Integer>( );
		final int NUMS = 4000;
		final int GAP  =   37;

		System.out.println("Checking...(...)");

		for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
			t.insert( i );

		if( NUMS < 40 )
			t.printTree( );
		if( t.findMin( ) != 1 || t.findMax( ) != NUMS - 1 )
			System.out.println( "FindMin or FindMax error!" );

		for( int i = 1; i < NUMS; i++ )
			if( !t.contains( i ) )
				System.out.println( "Find error1!" );

//		Vår egna lilla test del i main. 
//		System.out.println("--Start of tree--");
//		t.printTree( );
//		System.out.println("--End of tree--");
//		System.out.println("\n");
//		System.out.println("Max height : " + t.maxHeight());
//		System.out.println("Size of the tree (node count): " + t.size());
//		System.out.println("Is this tree a searchTree: " + t.isSearchTree());
//		System.out.println("Is the Height correct : " + t.hasCorrectHeightInfo());
	}
}
