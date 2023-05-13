import java.util.ArrayList;

public class BstInternet {
	// ı have root alloutput which is an output string and
	// receiver and sender arraylists
	private static Node root;
	public String allOutput;
	private ArrayList<String> receiver;
	private ArrayList<String> sender;


	public BstInternet() {
		root = null;
		allOutput = "";
		receiver = new ArrayList<String>();
		sender = new ArrayList<String>();
	}

	// recursive add node fct
	public void AddNode(String IpAddress) {
		root = AddNode(root, IpAddress);
	}

	public Node AddNode(Node root, String IpAddress) {
		// if root is null just create a new node and return that
		if (root == null) {
			root = new Node(IpAddress);
			return root;
		}
		if (IpAddress.compareTo(root.getIP()) < 0) { 
			// insert in the left subtree and update output file
			allOutput += root.getIP() + ": New node being added with IP:" + IpAddress + "\n";
			// since its recursive  just I just call addnode again
			Node leftNode = AddNode(root.getLeftNode(), IpAddress);
			// and since im going down in left side update the current nodes parent and parents left node
			root.setLeftNode(leftNode);
			leftNode.setParentNode(root);

		} else if (IpAddress.compareTo(root.getIP()) > 0) {
			// insert in the right subtree, which has the same algorithm as above
			allOutput += root.getIP() + ": New node being added with IP:" + IpAddress + "\n";
			
			Node rightNode = AddNode(root.getRightNode(), IpAddress);
			root.setRightNode(rightNode);
			rightNode.setParentNode(root);
		}

		return root;
	}

	// recursive delete fct
	public void deleteNode(String IpAddress) {
		root = deleteNode(root, IpAddress, true);

	}

	// this delete function takes a parameter boolean to determine if the delete 
	// is non leaf or not, since outputs depend on it
	public Node deleteNode(Node root, String IpAddress, boolean nonLeafCheck) {

		if (root == null) {
			return root;
		}

		if (IpAddress.compareTo(root.getIP()) < 0) {
			// traverse left subtree, if we have to delete the left then as in add node just go to left
			root.setLeftNode(deleteNode(root.getLeftNode(), IpAddress, nonLeafCheck));
			if (root.getLeftNode() != null) {
				// if left node is not null then set parent of left node
				root.getLeftNode().setParentNode(root);
			}
		} else if (IpAddress.compareTo(root.getIP()) > 0) {
			// right subtree traversing, same algorithm as abouve
			root.setRightNode(deleteNode(root.getRightNode(), IpAddress, nonLeafCheck));
			if (root.getRightNode() != null) {
				root.getRightNode().setParentNode(root);
			}
		} else {
			// when ıp == root.ip then we check if its leaf node, sinngle childede node or non leaf node
			if (root.getLeftNode() == null && root.getRightNode() == null) {
				// this is where I use nonLeafCheck, if it's false then we are deleting a non leaf node
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Leaf Node Deleted: " + IpAddress + "\n";
				}

				return null;
			} else if (root.getLeftNode() == null) {
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Node with single child Deleted: " + root.getIP()
							+ "\n";
				}

				return root.getRightNode();
			} else if (root.getRightNode() == null) {
				if (nonLeafCheck == true) {
					allOutput += root.getParentNode().getIP() + ": Node with single child Deleted: " + root.getIP()
							+ "\n";
				}

				return root.getLeftNode();
			}
			// if code can come here then non leaf check is false then non leaf node is deleted so I give false to it 
			allOutput += root.getParentNode().getIP() + ": Non Leaf Node Deleted; removed: " + root.getIP()
					+ " replaced: " + minValue(root.getRightNode()) + "\n";
			root.setIP(minValue(root.getRightNode()));
			root.setRightNode(deleteNode(root.getRightNode(), root.getIP(), false));
		}
		return root;
	}
	// fct to find minvalue to the left since we have to replace with that node if we delete a non leaf node
	public String minValue(Node root) {
		String minValue = root.getIP();
		while (root.getLeftNode() != null) {
			//its just a while loop, go as left as possible
			minValue = root.getLeftNode().getIP();
			root = root.getLeftNode();
		}
		return minValue;
	}

	public Node search(Node root, String IpAddress, boolean receiverOrNot) {
		// this search function is used in send message, I keep the path in a arraylist, thats why have a boolean receiver or not
		if (receiverOrNot) {
			receiver.add(root.getIP());
		} else {
			sender.add(root.getIP());
		}
		// its just a recursive algorithm to find a certain ipAddressed node
		if (root == null || root.getIP().compareTo(IpAddress) == 0) {
			return root;
		}

		if (root.getIP().compareTo(IpAddress) < 0) {

			return search(root.getRightNode(), IpAddress, receiverOrNot);
		}
		return search(root.getLeftNode(), IpAddress, receiverOrNot);

	}

	public void sendMessage(String senderIp, String receiverIp) {
		receiver.clear();
		sender.clear();
		// I first clear the arraylists then create them again with the given IP's
		search(root, receiverIp, true);
		search(root, senderIp, false);
		allOutput += senderIp + ": Sending message to: " + receiverIp + "\n";
		int curPos = 0;
		// this algorithm uses both arraylists, compares them to each other, and stops when one element is contained 
		// in the receivers array list
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
		// then goes on from the other side, I keep a curPos variable, to not print the same output twice
		for (int i = curPos + 1; i < receiver.size() - 1; i++) {
			allOutput += receiver.get(i) + ": Transmission from: " + receiver.get(i - 1) + " receiver: " + receiverIp
					+ " sender:" + senderIp + "\n";
		}
		// after all is finished output string is updated.
		allOutput += receiverIp + ": Received message from: " + senderIp + "\n";
	}


}
