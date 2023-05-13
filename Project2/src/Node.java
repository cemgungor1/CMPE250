
public class Node {

	// a node object having parent left and right child and ip and its getters setters
	private Node parentNode;
	private Node leftNode;
	private Node rightNode;
	private String IP;

	public Node(String IP) {
		this.parentNode = null;
		this.leftNode = null;
		this.rightNode = null;
		this.IP = IP;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

}

