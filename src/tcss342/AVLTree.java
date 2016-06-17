/*
 *Nwoke Fortune Chiemeziem
 *AVLTrees- TCSS Home work 3 
 */
package tcss342;

public class AVLTree<T extends Comparable<T>>
{
	private int size;

	private Node<T> root;

	public boolean add(T item)
	{
		if (item == null)
			throw new IllegalArgumentException();

		if (root == null)
		{
			root = new Node<T>(item);
			size++;
			return true;
		} else
			return add(root, item);
	}

	private boolean add(Node<T> n, T item)
	{
		int cmp = n.item.compareTo(item);
		if (cmp > 0)
			// Add child to the left.
			if (n.left == null)
			{
				// n.height = getheight(n);
				n.left = new Node<T>(item, n);
				resetHeight(n.left);

				size++;

				return true;
			} else
			{
				return add(n.left, item);
			}

		else if (cmp < 0)
			// Add child to the right.
			if (n.right == null)
			{
				n.right = new Node<T>(item, n);
				resetHeight(n.right);
				size++;

				return true;
			} else
			{
				return add(n.right, item);
			}

		else
			return false;
	}

	public void resetHeight(Node<T> node)
	{
		while (node.parent != null)
		{

			if (node.parent.left == null && node.parent.right == node)
			{
				node.parent.height += 1;

				if (isVoilation(node.parent))
				{

					// double left right
					if (node.right == null)
					{
						node.parent = doubleLeftRight(node);
					} else
					{

						node.parent.parent.right = rotateLeft(node.parent);
					}
				}
			} else if (node.parent.right == null && node.parent.left == node)
			{
				node.parent.height += 1;
				if (isVoilation(node.parent))
				{

					// double right left
					if (node.left == null)
					{

						node.parent.parent.right = doubleRightLeft(node);
					} else
					{

						node.parent.parent.left = rotateRight(node.parent);
					}
				}
			}
			if (node.parent.left != null && node.parent.right != null)
			{
				node.parent.height = Math.max(node.parent.left.height,
						node.parent.right.height) + 1;

				if (isVoilation(node.parent))
				{

					if (node.parent.left.height - node.parent.right.height == 2)
					{

						node.right = rotateRootRight(node.parent);
						continue;
					} else
					{

						node.left = rotateRootLeft(node.parent);
						continue;
					}

				}
			}
			node = node.parent;
		}
		root = node;

		if (root.left != null && root.right != null)
			root.height = Math.max(root.left.height, root.right.height) + 1;
	}

	private Node<T> rotateRootRight(Node<T> node)
	{ // node == 54
		Node<T> a = node;// 70
		Node<T> b = node.left; // 54
		Node<T> c = node.left.right; // 55

		b.parent = a.parent;
		b.right = a;
		b.left.right = null;

		a.parent = b;
		a.height -= 2;
		a.left = c;

		c.parent = a;

		return a;
	}

	private Node<T> doubleLeftRight(Node<T> node)
	{

		Node<T> a = node;// 70
		Node<T> b = node.parent; // 50
		Node<T> c = node.left; // 55

		a.height += 1;
		a.parent = b.parent;

		b.height -= 2;
		b.parent = c;
		b.right = null;

		c.left = b;
		c.height += 1;

		return rotateRight(a);

	}

	private Node<T> doubleRightLeft(Node<T> node)
	{

		Node<T> a = node;// 73
		Node<T> b = node.parent; // 75
		Node<T> c = node.right; // 74

		a.height += 1;
		a.parent = b.parent;

		b.height -= 2;
		b.parent = c;
		b.left = null;

		c.right = b;
		c.height += 1;

		return rotateLeft(a);

	}

	/**
	 * Rotates node a to the left, making its right child into its parent.
	 * 
	 * @param a the former parent
	 *            
	 * @return the new parent (formerly a's right child)
	 */
	private Node<T> rotateLeft(Node<T> a)
	{
		a.height -= 1;
		Node<T> b = a.right;
		b.parent = a.parent;
		a.right = b.left;

		a.height -= 1;
		b.left = a;
		a.parent = b;
		return b;

	}

	private Node<T> rotateRight(Node<T> a)
	{
		a.height -= 1;
		Node<T> b = a.left;
		b.parent = a.parent;

		a.height -= 1;
		a.left = b.right;

		b.right = a;
		a.parent = b;

		return b;
	}

	private Node<T> rotateRootLeft(Node<T> node)
	{
		// node == 54
		Node<T> a = node;// 70
		Node<T> b = node.right; // 54
		Node<T> c = node.right.left; // 55

		b.parent = a.parent;
		b.left = a;
		b.right.left = null;

		a.parent = b;
		a.height -= 2;
		a.right = c;

		c.parent = a;

		return a;
	}

	private boolean isVoilation(Node<T> node)
	{
		boolean v = false;
		if (node.left == null && node.right != null
				&& (node.right.height == 1 || node.right.height == -1))
			v = true;
		else if (node.right == null && node.left != null
				&& (node.left.height == 1 || node.left.height == -1))
			v = true;
		else if (node.left != null && node.right != null)
		{
			if (node.left.height - node.right.height == -2
					|| node.left.height - node.right.height == 2)
				v = true;
		}
		return v;
	}

	public boolean remove(T item)
	{
		if (item == null)
			throw new IllegalArgumentException();

		return remove(root, item);
	}

	private boolean remove(Node<T> n, T item)
	{
		int cmp = n.item.compareTo(item);
		if (cmp > 0)
			// Search to the left.
			if (n.left == null)
				return false;
			else
				return remove(n.left, item);
		else if (cmp < 0)
			// Search to the right.
			if (n.right == null)
				return false;
			else
				return remove(n.right, item);
		else
		{
			Node<T> x;   
		    
		    if (n.left == null){  
		      x = n.right;
		    }
		    else if (n.right == null) 
		      x = n.left;
		    else {                 
		      x = n.getSuccessor();  
		      remove(x.item); 
		     
		      x.left = n.left; 
		      x.right = n.right;
		      if (x.left != null) x.left.parent = x;
		      if (x.right != null) x.right.parent = x;
		    } 
		    
		    
		    if (x != null)
		      x.parent = n.parent;
		    if (root == n)
		      root = x;
		    else{
		      if (n == n.parent.left)
		          n.parent.left = x;
		      else
		          n.parent.right = x;
		    } 
			size--;
			resetHeight(n);
			return true;
		}
	}
	

	public boolean contains(T item)
	{
		if (item == null)
			throw new IllegalArgumentException();

		if (root == null)
			return false;
		else
			return contains(root, item);
	}
	
	private boolean contains(Node<T> n, T item)
	{
		int cmp = n.item.compareTo(item);
		if (cmp > 0)
			// Search to the left
			if (n.left == null)
				return false;
			else
				return contains(n.left, item);
		else if (cmp < 0)
			// Search to the right
			if (n.right == null)
				return false;
			else
				return contains(n.right, item);
		else
			return true;
	}

	public void clear()
	{
		size = 0;
		root = null;
	}
	
	public int size()
	{
		return size;
	}
	
	public void hasValidStructure()
	{
		if (root != null)
		{
			assert root.parent == null;
			hasValidStructure(root);
		}
	}

	private void hasValidStructure(Node<T> n)
	{
		if (n.left != null)
		{
			assert n.left.item.compareTo(n.item) < 0;
			hasValidStructure(n.left);
		}
		if (n.right != null)
		{
			assert n.right.item.compareTo(n.item) > 0;
			hasValidStructure(n.right);
		}
	}
	
	public static void main(final String... args) {
		
		Integer[] a = {3,2,8,1,5,9,4,7,6};
		
		AVLTree<Integer> m = new AVLTree<>();
		
		for (Integer n : a) {
			m.add(n);
		}
		System.out.println(m.size());
	}
	public static class Node<T>
	{

		public T item;
		public Node<T> right;
		public Node<T> left;
		public Node<T> parent;

		public int height;

		public Node(T item)
		{
			this(item, null);
		}

		public Node(T item, Node<T> parent)
		{
			this.item = item;
			this.parent = parent;
		}
		
		public Node<T> getSuccessor()
		{
			if (right != null)
			{
				Node<T> successor = right;
				while (successor.left != null)
					successor = successor.left;
				return successor;
			} else
			{
				Node<T> successor = this;
				while (successor.parent != null
						&& successor.parent.right == successor)
					successor = successor.parent;
				successor = successor.parent;
				return successor;
			}
		}

		public String toString()
		{
			return item + "";
		}

	}

}
