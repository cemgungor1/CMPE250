import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Project3 {
	public static void main(String[] args) {
		try {

			// first created a hashmap to keep all the acc's and total time to keep track of
			// it
			FileInputStream fis = new FileInputStream(args[0]);

			Scanner sc = new Scanner(fis);
			String filePath = args[1];
			BufferedWriter writerObj = new BufferedWriter(new FileWriter(filePath));
			Map<String, ACC> ACCList = new HashMap<String, ACC>();
			ArrayList<String> outputStrings = new ArrayList<String>();
			int totalTime = 0;

			// got input of number of accs and flights
			int accNo, flightNo;
			String[] firstLine = sc.nextLine().split(" ");
			accNo = Integer.parseInt(firstLine[0]);
			flightNo = Integer.parseInt(firstLine[1]);

			// created the acc's and added airports into them
			for (int i = 0; i < accNo; i++) {
				String[] line = sc.nextLine().split(" ");
				ACC newAcc = new ACC(line[0]);
				ACCList.put(line[0], newAcc);
				for (int j = 1; j < line.length; j++) {
					Airport airport = new Airport(line[j], newAcc);
				}
			}
			// created the flights and added flights to the acclists flight list
			for (int i = 0; i < flightNo; i++) {
				String[] line = sc.nextLine().split(" ");
				Flight flight = new Flight(Integer.parseInt(line[0]), ACCList.get(line[2]).getAirport(line[3]),
						ACCList.get(line[2]).getAirport(line[4]), line[1]);
				flight.addDuration(line);
				ACCList.get(line[2]).addFlight(flight);
			}
			// created empty size to check if the flightlist is empty
			int emptySize = 0;
			int acclistSize = ACCList.size();

			while (true) {

				Flight polledFlight = null;
				// since an exception is thrown when an object is removed from something while
				// in a for loop
				// created a arraylist for acc's that will be deleted
				ArrayList<String> ACCFinish = new ArrayList<String>();

				for (ACC acc : ACCList.values()) {
					// if one of the acc's are done, then added the necessary output to the
					// arraylist of my strings
					// and incremented empty size while adding the acc's code to accfinish
					if (acc.getFlightListLength() == 0) {
						String line = acc.getCode() + " " + totalTime + " " + acc.printNames();
						outputStrings.add(line);
						emptySize += 1;
						ACCFinish.add(acc.getCode());
					}

					if (acc.getReadyQueue().size() > 0) {
						// the algorithm for ready queue
						// if its the last second then send the flight to the necessary queue
						// created polled flight object so that a flight doesn't do 2 tick of work in 1
						// tick
						int timeOfready = acc.getReadyQueue().peek().getLastDur();

						if (timeOfready == 1) {

							acc.getReadyQueue().peek().decrementDur();

							int indx1 = acc.getReadyQueue().peek().getIndex();
							// checked the indexes to decide which queue will the flight be added
							if (indx1 == 1 || indx1 == 11) {
								polledFlight = acc.getReadyQueue().poll();
								polledFlight.timeRunReset();
								acc.getWaitingQueue().add(polledFlight);
							} else if (indx1 == 3) {
								polledFlight = acc.getReadyQueue().poll();
								polledFlight.timeRunReset();
								polledFlight.setQueuePutTime(totalTime);
								polledFlight.addATCdeparture(polledFlight);
							} else if (indx1 == 13) {
								polledFlight = acc.getReadyQueue().poll();
								polledFlight.timeRunReset();
								polledFlight.setQueuePutTime(totalTime);
								polledFlight.addATClanding(polledFlight);
							} else if (indx1 == 21) {
								acc.deleteFlight(acc.getReadyQueue().poll());
							}

						} else {
							acc.getReadyQueue().peek().incrementTimeRun();

							acc.getReadyQueue().peek().decrementDur();
						}
						if (acc.getReadyQueue().size() > 0) {
							acc.getReadyQueue().peek().timeRunCheck(totalTime);
						}
					}
					// same as the accfinish
					ArrayList<Flight> WaitingFinish = new ArrayList<Flight>();
					for (Flight f : acc.getWaitingQueue()) {
						int timeOfWaiting = f.getLastDur();

						if (f != polledFlight) {
							if (timeOfWaiting == 1) {
								f.decrementDur();
								// before adding to the queue updated the queueputtime because it has the most
								// priority
								f.setQueuePutTime(totalTime);
								acc.getReadyQueue().add(f);
								WaitingFinish.add(f);
							} else {

								f.decrementDur();
							}
						}
					}
					for (Flight f : WaitingFinish) {
						acc.getWaitingQueue().remove(f);
					}

					ArrayList<Flight> AdmissionFinish = new ArrayList<Flight>();
					for (Flight f : acc.getAdmissionQueue()) {
						// admission queue just decrements the time in each tick, if it finishes adds
						// the flight to ready queue
						int timeOfAdmission = f.getAdmissionTime();

						if (timeOfAdmission == 1) {
							f.decrementAdmission();
							AdmissionFinish.add(f);
							f.setQueuePutTime(totalTime);
							acc.getReadyQueue().add(f);

						} else {
							f.decrementAdmission();
						}
					}
					for (Flight f : AdmissionFinish) {
						acc.getAdmissionQueue().remove(f);
					}
					// did the same process for each airport and its atc
					for (Airport airport : acc.getAirportList().values()) {

						if (airport.getAtc().getReadyQueue().size() > 0) {

							int timeOfReady = airport.getAtc().getReadyQueue().peek().getLastDur();

							if (timeOfReady == 1) {

								airport.getAtc().getReadyQueue().peek().decrementDur();

								int indx3 = airport.getAtc().getReadyQueue().peek().getIndex();
								if (indx3 == 4 || indx3 == 6 || indx3 == 8 || indx3 == 14 || indx3 == 16
										|| indx3 == 18) {
									polledFlight = airport.getAtc().getReadyQueue().poll();
									airport.getAtc().addWaitingQueue(polledFlight);
								} else if (indx3 == 10 || indx3 == 20) {
									Flight f = airport.getAtc().getReadyQueue().poll();
									f.setQueuePutTime(totalTime);
									acc.getReadyQueue().add(f);
								}
							} else {
								if (airport.getAtc().getReadyQueue().peek() != polledFlight) {

									airport.getAtc().getReadyQueue().peek().decrementDur();

								}
							}
						}

						ArrayList<Flight> atcWaitingFinish = new ArrayList<Flight>();
						for (Flight f : airport.getAtc().getWaitingQueue()) {

							int timeOfWaiting = f.getLastDur();
							if (f != polledFlight) {
								if (timeOfWaiting == 1) {

									f.decrementDur();
									int indx4 = f.getIndex();
									if (indx4 == 5 || indx4 == 7 || indx4 == 9) {
										atcWaitingFinish.add(f);
										f.setQueuePutTime(totalTime);

										f.addATCdeparture(f);
									} else if (indx4 == 15 || indx4 == 17 || indx4 == 19) {
										atcWaitingFinish.add(f);

										f.setQueuePutTime(totalTime);

										f.addATClanding(f);
									}
								} else {

									f.decrementDur();
								}
							}
						}
						for (Flight f : atcWaitingFinish) {
							airport.getAtc().getWaitingQueue().remove(f);
						}
					}

				}
				// checked if the emptied flight list size is equal to acclist size
				// if so break out of while loop.

				if (emptySize == acclistSize) {
					break;
				}
				for (String removedCode : ACCFinish) {
					ACCList.remove(removedCode);
				}
				// incremented total time.
				totalTime += 1;
			}

			for (String line : outputStrings) {
				writerObj.append(line);
			}

			writerObj.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
