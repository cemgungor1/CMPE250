import java.util.PriorityQueue;

public class ATC {
	// has its own atc,airport and code, moreover waiting and ready queue both of
	// which made up of priority queues
	private ACC acc;
	private Airport airport;
	private String ATCcode;
	private PriorityQueue<Flight> waitingQueue;
	private PriorityQueue<Flight> readyQueue;

	public ATC(ACC acc, Airport airport) {
		// constructor, creating the 2 queues using comparators defined in flight class
		this.acc = acc;
		this.airport = airport;
		this.readyQueue = new PriorityQueue<Flight>(new FlightATCComparator());
		this.waitingQueue = new PriorityQueue<Flight>(new FlightWaitingComparator());
		// hashing so that none of the names are hashed into the wrong index
		Hashing();
	}

	public void Hashing() {
		// i first got the codes, then summed up to total using the ascivalue * 31^index
		int total = 0;
		String accCode = acc.getCode();
		String airportCode = airport.getCode();

		for (int i = 0; i < airportCode.length(); i++) {
			char character = airportCode.charAt(i);
			int asciiValue = character;
			total += (asciiValue * Math.pow(31, i));
		}
		// then took mod/1000 since the hash table has size 1000
		total = total % 1000;
		// found the location using acc's addatc method
		// the method returns the index in the array
		// if the index of such element is taken then it ups by 1
		int location = acc.addATC(total, airportCode);
		this.ATCcode = airportCode + String.format("%03d", location);
	}

	// getter methods for queues
	public PriorityQueue<Flight> getWaitingQueue() {
		return this.waitingQueue;
	}

	public PriorityQueue<Flight> getReadyQueue() {
		return this.readyQueue;
	}

	// adding methods for the queues
	public void addReadyQueue(Flight flight) {
		readyQueue.add(flight);
	}

	public void addWaitingQueue(Flight flight) {
		waitingQueue.add(flight);
	}

	public String getATCcode() {
		return this.ATCcode;
	}
}
