import java.util.ArrayList;

public class AvlInternet {

	// starts as the same as bst tree
	// I wont comment if something is the same as bst to not repeat myself twice
	public static NodeAvl root;
	public String allOutput;
	private ArrayList<String> receiver;
	private ArrayList<String> sender;

	public AvlInternet() {
		root = null;
		allOutput = "";
		receiver = new ArrayList<String>();
		sender = new ArrayList<String>();
	}

	public void AddNode(String IpAddress) {
		root = AddNode(root, IpAddress, root);
	}

	public NodeAvl AddNode(NodeAvl root, String IpAddress, NodeAvl parent) {
		if (root == null) {
			root = new NodeAvl(IpAddress);

		} else if (IpAddress.compareTo(root.getIP()) < 0) {
			allOutput += root.getIP() + ": New node being added with IP:" + IpAddress + "\n";
			// since a node is inserted I have to update height and rebalance the tree if
			// needed
			root.setLeftNode(AddNode(root.getLeftNode(), IpAddress, root));
			root.setHeight(root);
			root = rebalance(root);

		} else if (IpAddress.compareTo(root.getIP()) > 0) {
			// same applies for here also
			allOutput += root.getIP() + ": New node being added with IP:" + IpAddress + "\n";
			root.setRightNode(AddNode(root.getRightNode(), IpAddress, root));
			root.setHeight(root);
			root = rebalance(root);
		}
		root.setHeight(root);
		return root;
	}

	public void deleteNode(String IpAddress) {
		root = deleteNode(root, IpAddress, true);
		return;
	}

	public NodeAvl deleteNode(NodeAvl root, String IpAddress, boolean nonLeafCheck) {
		// this is a little trick I used to update the parents, While searching I update
		// everynodes parent
		// so when I have to delete a node I just search it first to be sure the parents
		// are in correct order
		search(root, IpAddress, true);
		receiver.clear();
		if (root == null) {
			return root;
		}
		if (IpAddress.compareTo(root.getIP()) < 0) {
			// traverse left subtree
			root.setLeftNode(deleteNode(root.getLeftNode(), IpAddress, nonLeafCheck));

		} else if (IpAddress.compareTo(root.getIP()) > 0) {
			// traverse right subtree
			root.setRightNode(deleteNode(root.getRightNode(), IpAddress, nonLeafCheck));

		} else {
			// same as bstInternet
			if (root.getLeftNode() == null && root.getRightNode() == null) {
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Leaf Node Deleted: " + IpAddress + "\n";
				}
				// this time not returning, returning is at end since I have to do other things
				// at last
				root = null;

			} else if (root.getLeftNode() == null) {
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Node with single child Deleted: " + root.getIP()
							+ "\n";
				}
				root = root.getRightNode();
			} else if (root.getRightNode() == null) {
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Node with single child Deleted: " + root.getIP()
							+ "\n";
				}
				root = root.getLeftNode();
			} else {
				// same algorithm to delete the non leaf node
				if (!(root.getIP().compareTo(this.root.getIP()) == 0)) {
					allOutput += root.getParentNode().getIP() + ": Non Leaf Node Deleted; removed: " + root.getIP()
							+ " replaced: " + minValue(root.getRightNode()) + "\n";
					String minVal = minValue(root.getRightNode());
					NodeAvl rightNode = deleteNode(root.getRightNode(), minVal, false);
					root.setRightNode(rightNode);
					root.setIP(minVal);
				}
			}
		}
		if (root == null) {
			return root;
		}
		// if root is not null, meaning some nodes changed their places, then I
		// setheight and rebalance
		root.setHeight(root);
		root = rebalance(root);
		return root;
	}

	public String minValue(NodeAvl root) {
		String minValue = root.getIP();
		while (root.getLeftNode() != null) {
			minValue = root.getLeftNode().getIP();
			root = root.getLeftNode();
		}
		return minValue;
	}

	// rotating right algorithm, use a doubleornot boolean so that I can check if
	// its a double rotation or not
	// if its not double rotation then I can update output file
	private NodeAvl rotateRight(NodeAvl node, boolean doubleOrNot) {
		NodeAvl leftChild = node.getLeftNode();
		NodeAvl leftRightChild = leftChild.getRightNode();
		if (!doubleOrNot) {
			allOutput += "Rebalancing: right rotation\n";

		}

		leftChild.setRightNode(node);
		node.setLeftNode(leftRightChild);

		node.setHeight(node);
		leftChild.setHeight(leftChild);

		return leftChild;
	}

	// same as above just rotation to left
	private NodeAvl rotateLeft(NodeAvl node, boolean doubleOrNot) {
		if (node == null) {
			return node;
		}
		NodeAvl rightChild = node.getRightNode();
		NodeAvl rightLeftChild = rightChild.getLeftNode();

		if (!doubleOrNot) {
			allOutput += "Rebalancing: left rotation\n";
		}

		rightChild.setLeftNode(node);
		node.setRightNode(rightLeftChild);

		node.setHeight(node);
		rightChild.setHeight(rightChild);

		return rightChild;
	}

	// since balancefactor is important in avl, I find it using a fct
	private int balanceFactor(NodeAvl node) {
		if (node == null) {
			return 0;
		}
		// by getting left and right nodes height
		return node.getHeight(node.getLeftNode()) - node.getHeight(node.getRightNode());
	}

	private NodeAvl rebalance(NodeAvl node) {
		int balanceFactor = balanceFactor(node);

		if (balanceFactor > 1) {
			// if balance factor is greater than 1 then theres a problem in the tree
			if (balanceFactor(node.getLeftNode()) >= 0) {
				// if just one rtation is enough then I give false as a parameter, so its not a
				// double rotation
				node = rotateRight(node, false);
			} else {
				// else I give true to not output a wrong string
				allOutput += "Rebalancing: left-right rotation\n";
				node.setLeftNode(rotateLeft(node.getLeftNode(), true));
				node = rotateRight(node, true);
			}
		}
		if (balanceFactor < -1) {
			// same as above, just other sided
			if (balanceFactor(node.getRightNode()) <= 0) {
				node = rotateLeft(node, false);
			} else {
				allOutput += "Rebalancing: right-left rotation\n";
				node.setRightNode(rotateRight(node.getRightNode(), true));
				node = rotateLeft(node, true);
			}
		}
		return node;
	}

	// search algorithm same as bst, but this time the parents are updated as we go
	// to make sure each nodes parents are right
	public NodeAvl search(NodeAvl root, String IpAddress, boolean receiverOrNot) {
		if (receiverOrNot) {
			receiver.add(root.getIP());
		} else {
			sender.add(root.getIP());
		}
		if (root == null || root.getIP().compareTo(IpAddress) == 0) {
			return root;
		}
		if (root.getIP().compareTo(IpAddress) < 0) {
			root.getRightNode().setParentNode(root);
			return search(root.getRightNode(), IpAddress, receiverOrNot);
		}
		root.getLeftNode().setParentNode(root);
		return search(root.getLeftNode(), IpAddress, receiverOrNot);
	}

	public void sendMessage(String senderIp, String receiverIp) {
		receiver.clear();
		sender.clear();
		search(root, receiverIp, true);
		search(root, senderIp, false);
		// send message is the same also
		allOutput += senderIp + ": Sending message to: " + receiverIp + "\n";
		int curPos = 0;
		if (!(receiver.contains(sender.get(sender.size() - 1)))) {
			for (int i = sender.size() - 1; i > 0; i--) {
				if (sender.get(i - 1).compareTo(receiverIp) == 0) {
					curPos = receiver.indexOf(sender.get(i - 1));
					break;
				}
				allOutput += sender.get(i - 1) + ": Transmission from: " + sender.get(i) + " receiver: " + receiverIp
						+ " sender:" + senderIp + "\n";
				if (receiver.contains(sender.get(i - 1))) {
					curPos = receiver.indexOf(sender.get(i - 1));
					break;
				}
			}
		} else {
			curPos = receiver.indexOf(sender.get(sender.size() - 1));
		}
		for (int i = curPos + 1; i < receiver.size() - 1; i++) {
			allOutput += receiver.get(i) + ": Transmission from: " + receiver.get(i - 1) + " receiver: " + receiverIp
					+ " sender:" + senderIp + "\n";
		}
		allOutput += receiverIp + ": Received message from: " + senderIp + "\n";
	}
}
