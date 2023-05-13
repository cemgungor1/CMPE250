import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			//took inputs and created 2 output files and writer objects
			FileInputStream fis = new FileInputStream(args[0]);
			Scanner sc = new Scanner(fis);
			String filePath = args[1];
			BufferedWriter AvlWriter = new BufferedWriter(new FileWriter(filePath + "_AVL.txt"));

			BufferedWriter BstWriter = new BufferedWriter(new FileWriter(filePath + "_BST.txt"));
			BstInternet BstInternet = new BstInternet();
			AvlInternet AvlInternet = new AvlInternet();
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] inputText = line.split(" ");
				if (inputText.length != 1) {
					switch (inputText[0]) {
					// for other than first line added deleted and sended messages depending on the input line
					case "ADDNODE":
						AvlInternet.AddNode(inputText[1]);
						BstInternet.AddNode(inputText[1]);
						break;
					case "DELETE":
						AvlInternet.deleteNode(inputText[1]);
						BstInternet.deleteNode(inputText[1]);
						break;
					case "SEND":
						AvlInternet.sendMessage(inputText[1], inputText[2]);
						BstInternet.sendMessage(inputText[1], inputText[2]);
						break;
					}
				} else {
					// for the first line just added the node
					AvlInternet.AddNode(inputText[0]);
					BstInternet.AddNode(inputText[0]);
				}
			}
			// appended to files and closed them
			AvlWriter.append(AvlInternet.allOutput);
			AvlWriter.close();
			BstWriter.append(BstInternet.allOutput);
			BstWriter.close();
			sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
