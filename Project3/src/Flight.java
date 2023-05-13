import java.util.Comparator;

public class Flight {

	private Airport departure;
	private Airport landing;
	private ACC acc;
	private String flightCode;
	// kept added again to check the priority, time run to check the 30 tick
	// and queueputTime to check the priority again
	private int timeRun;
	private boolean addedAgain;
	private int queuePutTime;

	// index is used to keep the event
	private int index;
	private int[] durationOfSteps;
	private int admissionTime;

	public Flight(int admission, Airport departure, Airport landing, String flightCode) {
		this.index = 0;
		this.addedAgain = false;
		this.timeRun = 0;
		this.admissionTime = admission;
		this.departure = departure;
		this.landing = landing;
		this.flightCode = flightCode;
		this.acc = landing.getAcc();
		this.durationOfSteps = new int[21];
	}

	public void addDuration(String[] durations) {
		// its i-5 because it should start from 0 index
		// i starts from 5 because the durations given is the line splitted and it has
		// useless things
		for (int i = 5; i < durations.length; i++) {
			durationOfSteps[i - 5] = Integer.parseInt(durations[i]);
		}
	}

	public void addATCdeparture(Flight f) {
		departure.getAtc().addReadyQueue(f);
	}

	public void addATClanding(Flight f) {
		landing.getAtc().addReadyQueue(f);
	}

	public Airport getLandingAirport() {
		return this.landing;
	}

	public Airport getDepartureAirport() {
		return this.departure;
	}

	// decrementing duration, if it's going to finish, changing added again to false
	// since its finished and incrementing index
	public void decrementDur() {
		if (durationOfSteps[index] == 1) {
			durationOfSteps[index] = durationOfSteps[index] - 1;
			this.addedAgain = false;
			this.index += 1;
		} else {
			durationOfSteps[index] = durationOfSteps[index] - 1;
		}
	}

	public int getIndex() {
		return index;
	}

	public int getLastDur() {
		return durationOfSteps[getIndex()];
	}

	public void timeRunReset() {
		this.timeRun = 0;
	}

	public void incrementTimeRun() {
		this.timeRun += 1;

	}

	// checking if flight has run more than 30 seconds, if so remove the flight,
	// change added again to true, and put it back again
	// time is taken as an argument not to lose the queuePutTime
	public void timeRunCheck(int time) {
		if (this.timeRun == 30) {
			if (this.acc.getReadyQueue().contains(this)) {
				this.acc.getReadyQueue().remove(this);
				this.addedAgain = true;
				this.timeRunReset();
				this.setQueuePutTime(time);
				this.acc.getReadyQueue().add(this);
			}
		}
	}

	public void changeAddedAgain(boolean change) {
		this.addedAgain = change;
	}

	public void decrementAdmission() {
		this.admissionTime -= 1;
	}

	public int getAdmissionTime() {
		return admissionTime;
	}

	public String getFlightCode() {
		return this.flightCode;
	}

	public boolean getAddedAgain() {
		return this.addedAgain;
	}

	public int getRunTime() {
		return this.timeRun;
	}

	public int getQueuePutTime() {
		return this.queuePutTime;
	}

	public void setQueuePutTime(int time) {
		this.queuePutTime = time;
	}
}

//comparator classes
class FlightWaitingComparator implements Comparator<Flight> {
	// waiting comparator just checks if last duration is smaller, then it goes to
	// front
	// if last duration is equal then check flight codes
	public int compare(Flight flight1, Flight flight2) {

		if (flight1.getLastDur() < flight2.getLastDur()) {
			return 1;
		} else if (flight1.getLastDur() < flight2.getLastDur()) {
			return -1;
		} else {
			if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) < 0) {
				return -1;
			} else if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) > 0) {
				return 1;
			}
			return 0;
		}
	}
}

class FlightRunningComparator implements Comparator<Flight> {
	// running comparator first checks queue put time, since its very important in
	// priority
	// if its the same checks if one of the flights are added back again, if its not
	// the case
	// only then checks for flightCodes
	public int compare(Flight flight1, Flight flight2) {

		if (flight1.getQueuePutTime() < flight2.getQueuePutTime()) {

			return -1;
		} else if (flight1.getQueuePutTime() > flight2.getQueuePutTime()) {

			return 1;
		} else {
			if (flight1.getAddedAgain() == true & flight2.getAddedAgain() == false) {
				return 1;
			} else if (flight1.getAddedAgain() == false & flight2.getAddedAgain() == true) {
				return -1;
			} else {
				if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) < 0) {
					return -1;
				} else if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) > 0) {
					return 1;
				}
			}
		}
		return 0;

	}

}

class FlightATCComparator implements Comparator<Flight> {
	// atcComparator is like the running comparator but since no flight is added
	// back again to the queue
	// it doesn't check for it
	public int compare(Flight flight1, Flight flight2) {

		if (flight1.getQueuePutTime() < flight2.getQueuePutTime()) {
			return -1;
		} else if (flight1.getQueuePutTime() > flight2.getQueuePutTime()) {
			return 1;
		} else {

			if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) < 0) {
				return -1;
			} else if (flight1.getFlightCode().compareTo(flight2.getFlightCode()) > 0) {
				return 1;
			}

		}
		return 0;
	}

}
