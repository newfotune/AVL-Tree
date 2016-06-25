package tcss342;
// File: AVLTree.java
import java.util.*;

/**
 * Implementation of an AVL Tree (once you fill in the missing code).
 *  BST code has been taken from the course textbook: Cormen, Leiserson, Rivest, Stein:
 * Introduction to Algorithm (2nd edition). McGraw-Hill (2001), ISBN: 0070131511
 */

public class AVLTree {
	/** Root of the AVL tree. */
	protected Node root;

	/**
	 * The constructor creating a binary search tree with just a
	 * <code>null</code>, which is the root.
	 */
	public AVLTree() {
		root = null;
	}

	/**
	 * Searches the tree for a node with a given key. If such a node exists,
	 * prints the value of that node.
	 *
	 * @param k
	 *            The key being searched for.
	 * @return The data under the key k An exception NoSuchElementException is
	 *         thrown if no node exists.
	 */
	public int search(int k) {
		Node node = search(root, k);
		System.out.println(" " + node.toString());
		return node.key;
	} // search

	/**
	 * Searches the subtree rooted at a given node for a node with a given key.
	 * 
	 * @param node
	 *            Root of the subtree.
	 * @param k
	 *            The key being searched for.
	 * @return A reference to a <code>Node</code> object with key <code>k</code>
	 *         if such a node exists. An exception NoSuchElementException is
	 *         thrown if no node exists.
	 */
	protected Node search(Node node, int k) {
		while ((node != null) && (k != node.key)) {
			if (k < node.key)
				node = node.left;
			else
				node = node.right;
		} // while

		if (node != null) {
			return node;
		} else {
			throw new NoSuchElementException("Not Found");
		}
	} // search

	/**
	 * Returns the node with the minimum key in the subtree rooted at a node.
	 *
	 * @param x
	 *            Root of the subtree.
	 * @return A <code>Node</code> object with the minimum key in the tree, or
	 *         the sentinel <code>nil</code> if the tree is empty.
	 */
	protected Node treeMinimum(Node x) {
		while (x.left != null)
			x = x.left;

		return x;
	} // treeMinimum

	/**
	 * Returns the successor of a given node in an inorder walk of the tree.
	 *
	 * @param node
	 *            The node whose successor is returned.
	 * @return If <code>node</code> has a successor, it is returned. Otherwise,
	 *         return the sentinel <code>null</code>.
	 */
	protected Node successor(Node node) {
		Node x = node;

		if (x.right != null)
			return treeMinimum(x.right);

		Node y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}

		return y;
	} // successor

	/**
	 * Performs single left rotation.
	 * 
	 * @param A
	 *            Node around which the rotation takes place
	 */
	protected void singleLeftRotation(Node theNode) {
		Node oldRight = theNode.right;
		Node newRight = theNode.right.left;

		oldRight.parent = theNode.parent;
		oldRight.left = theNode;

		if (theNode.parent != null)
			theNode.parent.left = oldRight;//fixed

		theNode.parent = oldRight;
		theNode.right = newRight;
		theNode.height -= 2;

		if (newRight != null)
			newRight.parent = theNode;
	}// singleLeftRotation

	/**
	 * Performs single right rotation.
	 * 
	 * @param A
	 *            Node around which the rotation takes place
	 */
	protected void singleRightRotation(Node theNode) {
		Node oldLeft = theNode.left;
		Node newLeft = theNode.left.right;

		oldLeft.parent = theNode.parent;
		oldLeft.right = theNode;

		if (theNode.parent != null)
			theNode.parent.right = oldLeft;

		theNode.parent = oldLeft;
		theNode.left = newLeft;
		theNode.height -= 2;

		if (newLeft != null)
			newLeft.parent = theNode;

	}// singleRightRotation

	/**
	 * Performs double right left rotation.
	 * 
	 * @param A
	 *            Node around which the rotation takes place
	 */
	protected void doubleRightLeftRotation(Node theNode) {
		Node oldLeft = theNode.left;
		Node newLeft = theNode.left.right;

		oldLeft.parent = theNode.parent;
		oldLeft.right = theNode;
		oldLeft.height += 1;
		oldLeft.parent.right = oldLeft;

		theNode.left = newLeft;
		theNode.parent = oldLeft;
		theNode.height -= 1;

		if (newLeft != null)
			newLeft.parent = theNode;
	}// doubleRightLeftRotation

	/**
	 * Performs double left right rotation.
	 * 
	 * @param A
	 *            Node around which the rotation takes place
	 */
	protected void doubleLeftRightRotation(Node theNode) {
		Node oldRight = theNode.right;
		Node newRight = theNode.right.left;

		oldRight.parent = theNode.parent;
		oldRight.left = theNode;
		oldRight.height += 1;
		oldRight.parent.left = oldRight;

		theNode.right = newRight;
		theNode.parent = oldRight;
		theNode.height -= 1;

		if (newRight != null)
			newRight.parent = theNode;
	}// doubleLeftRightRotation

	private boolean isViolation(Node node) {
		int leftHeight = -1;
		int rightHeight = -1;
		// node is currently the root
		if (node.left != null)
			leftHeight = node.left.height;
		if (node.right != null)
			rightHeight = node.right.height;

		return Math.abs(leftHeight - rightHeight) > 1;
	}
	/**
	 * Check is the violation is from the left sub tree
	 * 
	 * @param node
	 * @return
	 */
	private boolean isLeftViolation(Node node) {
		if (node.right == null && node.left != null)
			return true;
		else if (node.right != null && node.left != null)
			return node.left.height - node.right.height >= 1;
		else
			return false;
	}

	/**
	 * Balances a right-heavy or left-heavy unbalanced tree. This function
	 * should check for the nature of the imbalance and call the appropriate
	 * rotation functions. In addition, after rotations are performed, it should
	 * update the balance factors of the rotated nodes accordingly.
	 * 
	 * @param A
	 *            The root of the unbalanced tree.
	 * @return The new root of the tree
	 */
	protected Node balance(Node node) {
		while (node.parent != null) { // while we haven't gone up to the root.
			node.parent.height = getHeight(node.parent);
			if (isViolation(node.parent)) { // there is a violation
				if (isLeftViolation(node.parent)) {// case 1 and 2

					if (isLeftViolation(node)) { // case 1 --single left
													// violation
						singleRightRotation(node.parent);
						continue;
					} else { // case 2 --double left violation
						// print();
						doubleLeftRightRotation(node);
					}
				} else {
					if (isLeftViolation(node)) { // case 3 --double right
													// violation
						// print();
						doubleRightLeftRotation(node);
					} else { // case 4 --single right violation
						singleLeftRotation(node.parent);
						continue;
					}
				}
			}
			node = node.parent;
		}
		return node;
	}
	private int getHeight(Node node) {
		int leftHeight = -1;
		int rightHeight = -1;
		// node is currently the root
		if (node.left != null)
			leftHeight = node.left.height;
		if (node.right != null)
			rightHeight = node.right.height;

		return 1 + Math.max(leftHeight, rightHeight);
	}

	/**
	 * Inserts a key and a data item into the tree, creating a new node for this
	 * key and data pair.
	 *
	 * @param key
	 *            , data: Key and Data to be inserted into the tree.
	 */
	public void insert(int key) {
		Node z = new Node(key);
		treeInsert(z);
	} // insert

	/**
	 * Inserts a node into the tree.
	 *
	 * @param z
	 *            The node to insert.
	 */
	protected void treeInsert(Node z) {
		Node parent = null;
		Node current = root;

		while (current != null) {
			parent = current;
			if (z.key <= current.key)
				current = current.left;
			else
				current = current.right;
		} // while

		z.parent = parent;
		if (parent == null) {
			root = z; // the tree had been empty
		} else {
			if (z.key <= parent.key)
				parent.left = z;
			else
				parent.right = z;

			root = balance(z);
		}// else

	} // treeInsert

	/**
	 * Removes a node with the given key from the tree. It is assumed that there
	 * is at most one node with the given key present in the tree.
	 *
	 * @param k
	 *            The key to be removed.
	 */
	public void deleteKey(int k) {
		Node node = (Node) search(root, k);
		if (node != null)
			delete(node);
		else
			throw new NoSuchElementException("Not Found");
	} // deleteKey

	protected void delete(Node node) {

		Node parent = node.parent;
		if (parent == null) {
			if (node.isLeaf()) { //only one node in tree
				root = null;
			} else {
				node.key = successor(node).key;
				delete(successor(node));
			}
		} else {
			if (parent.left == node) { // node to delete is a left child
				if (node.isLeaf()) {
					node.parent = null;
					parent.left = null;
				} else if (node.left != null) { // has only left child
					parent.left = node.left;
					node.left.parent = parent;
				} else if (node.right != null) { // has only right child
					parent.left = node.right;
					node.right.parent = parent;
				} else { // has both children
					node.key = successor(node).key;
					delete(successor(node));
				}
				parent.height = getHeight(parent);
				root = balance (parent);
			} else if (parent.right == node) { // node to delete is a right	child
				if (node.isLeaf()) {
					node.parent = null;
					parent.right = null;
				} else if (node.left != null) { // has only left child
					parent.right = node.left;
					node.left.parent = parent;
				} else if (node.right != null) { // has only right child
					parent.right = node.right;
					node.right.parent = parent;
				} else { // has both children
					node.key = successor(node).key;
					delete(successor(node));
				}
				parent.height = getHeight(parent);
				root = balance (parent);
			}
		}
	}

	public void printInOrder() {
		printInOrder(root);
	}
	private void printInOrder(Node theNode) {
		
		if (theNode != null) {
			printInOrder(theNode.left);
			System.out.println(theNode.key);
			printInOrder(theNode.right);
		}
	}
	
	/**
	 * Print the whole tree.
	 *
	 */
	public void print() {
		printHelper(root, "");
		System.out.print("\n");
	} // print

	/**
	 * Print the tree rooted at <code>root</code>, with indent preceding all
	 * output lines. The tree is printed rotated by 90 degrees. If there is no
	 * node at a given position of the tree, the character 'x' is printed.
	 *
	 * @param root
	 *            The root of the tree to be printed.
	 * @param indent
	 *            The number of spaces to go before output lines.
	 *
	 *            WARNING: DO NOT MODIFY THIS METHOD!!!
	 */
	protected static void printHelper(Node n, int indent) {

		String blanks = "";
		for (int i = 0; i < indent; ++i)
			blanks = blanks + " ";

		if (n == null) {
			System.out.println(blanks + "x");
			return;
		}

		printHelper(n.right, indent + n.toString().length());
		System.out.println(blanks + n.toString());
		printHelper(n.left, indent + n.toString().length());

	}// printHelper

	/**
	 * Print the tree rooted at <code>root</code>, with indent preceding all
	 * output lines. The tree is printed rotated by 90 degrees. Bars ("|")are
	 * added to indicate the parent node edges. It is memory-intensive, so for
	 * large trees the integer version is recommended. If there is no node at a
	 * given position of the tree, the character 'x' is printed.
	 *
	 * @param root
	 *            The root of the tree to be printed.
	 * @param indent
	 *            The number of spaces to go before output lines.
	 *
	 *            WARNING: DO NOT MODIFY THIS METHOD!!!
	 */
	private static void printHelper(Node n, String indent) {

		if (n == null) {
			System.out.println(indent + "x");
			return;
		}

		String blanks = "";
		for (int i = 0; i < n.toString().length(); ++i)
			blanks = blanks + " ";
		String barandblanks = "|";
		for (int i = 0; i < n.toString().length() - 1; ++i)
			barandblanks = barandblanks + " ";

		if (n.parent == null) // root
		{
			printHelper(n.right, indent + blanks);
			System.out.println(indent + "*" + n.toString());
			printHelper(n.left, indent + blanks);
			return;
		}

		if (n.parent.left == n) {
			printHelper(n.right, indent + barandblanks);
			System.out.println(indent + "|" + n.toString());
			printHelper(n.left, indent + blanks);
		} else {
			printHelper(n.right, indent + blanks);
			System.out.println(indent + "|" + n.toString());
			printHelper(n.left, indent + barandblanks);
		}
	}// printHelper

}