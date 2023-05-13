import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Project1 {
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(args[0]);
			Scanner sc = new Scanner(fis);
			BufferedWriter writerObj = new BufferedWriter(new FileWriter(args[1]));
			FactoryImpl factory = new FactoryImpl(null, null, 0);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] inputText = line.split(" ");

				switch (inputText[0]) {
				case "AF":
					Product productFirst = new Product(Integer.parseInt(inputText[1]), Integer.parseInt(inputText[2]));
					factory.addFirst(productFirst);
					break;
				case "AL":
					Product productLast = new Product(Integer.parseInt(inputText[1]), Integer.parseInt(inputText[2]));
					factory.addLast(productLast);
					break;
				case "A":
					try {
						Product productAdd = new Product(Integer.parseInt(inputText[2]),
								Integer.parseInt(inputText[3]));
						factory.add(Integer.parseInt(inputText[1]), productAdd);
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "RF":
					try {
						Product oldLast = factory.removeFirst();
						writerObj.append(oldLast.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;
				case "RL":
					try {
						Product oldLast = factory.removeLast();
						writerObj.append(oldLast.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "RI":
					try {
						Product removedProd = factory.removeIndex(Integer.parseInt(inputText[1]));
						writerObj.append(removedProd.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "RP":
					try {
						Product removedProduct = factory.removeProduct(Integer.parseInt(inputText[1]));
						writerObj.append(removedProduct.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "F":
					try {
						Product wantedProduct = factory.find(Integer.parseInt(inputText[1]));
						writerObj.append(wantedProduct.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "G":
					try {
						Product wantedProduct = factory.get(Integer.parseInt(inputText[1]));
						writerObj.append(wantedProduct.toString());
						writerObj.newLine();
					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "U":
					try {
						Product oldProduct = factory.update(Integer.parseInt(inputText[1]),
								Integer.parseInt(inputText[2]));
						writerObj.append(oldProduct.toString());
						writerObj.newLine();

					} catch (Exception e) {
						writerObj.append(e.getMessage());
						writerObj.newLine();
					}
					break;

				case "FD":
					int removedCount = factory.filterDuplicates();
					writerObj.append(Integer.toString(removedCount));
					writerObj.newLine();
					break;

				case "R":
					factory.reverse();
					String reversedFactoryLine = factory.print();
					writerObj.append(reversedFactoryLine);
					writerObj.newLine();
					break;
				case "P":
					String factoryLine = factory.print();
					writerObj.append(factoryLine);
					writerObj.newLine();
					break;
				}
			}
			writerObj.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}