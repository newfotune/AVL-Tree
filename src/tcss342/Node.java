package tcss342;

/**
 * CSC B63 Assignment #2 Programming component Class for storing a node of an
 * AVL tree
 *
 * DO NOT SUBMIT THIS FILE
 **/

public class Node {
	/** The key stored in the node. */
	public int key;

	/** The node's parent. */
	public Node parent;
	
	/** The node's left child. */
	public Node left;

	/** The node's right child. */
	public Node right;
	
	/** The Node's height. */
	public int height;

	/**
	 * Initializes a node with the key and the data and makes other pointers
	 * null. The Balance Factor is initialized to be 0.
	 *
	 * @param key
	 *            Key of the new node.
	 * @param data
	 *            Data to save in the node.
	 */
	public Node(int key) {
		this.key = key;
		//this.data = data;
		this.parent = null;
		this.left = null;
		this.right = null;
	} // Node

	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	/**
	 * Returns the node as a <code>String</code>.
	 */
	@Override
	public String toString() {
		return key+ " ("+height+")";
	}// printNode
}