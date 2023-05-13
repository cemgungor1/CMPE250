
public class NodeAvl {
	// a node object for avl, nearly the same as node 
	// yet it has height and a setheight method
	
	String IP;
	NodeAvl leftNode;
	NodeAvl rightNode;
	NodeAvl parentNode;
	int height;

	public NodeAvl(String IP) {
		this.IP = IP;
		this.leftNode = null;
		this.rightNode = null;
		this.parentNode = null;
		this.height = 0;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public NodeAvl getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(NodeAvl leftNode) {
		this.leftNode = leftNode;
	}
	
	public NodeAvl getParentNode() {
		return parentNode;
	}

	public void setParentNode(NodeAvl parentNode) {
		this.parentNode = parentNode;
	}

	public NodeAvl getRightNode() {
		return rightNode;
	}

	public void setRightNode(NodeAvl rightNode) {
		this.rightNode = rightNode;
	}

	public int getHeight(NodeAvl node) {
		if (node == null) {
			return -1;
		} else {
			return node.height;
		}
	}
	// a set height method to find height for nodes
	public void setHeight(NodeAvl node) {
		int leftChildHeight = getHeight(node.getLeftNode());
		int rightChildHeight = getHeight(node.getRightNode());
		node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
	}

	
}
