import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ACC {
	// acc has also an admission queue to keep the admissions of its flights
	// its the same as waiting queue
	private String accCode;
	private PriorityQueue<Flight> waitingQueue;
	private PriorityQueue<Flight> admissionQueue;
	private PriorityQueue<Flight> readyQueue;
	// atc names has the name of the airports in the hashed index
	private String[] atcNames = new String[1000];
	// array list of indexes to combine at the last part, while ordering
	private ArrayList<Integer> indexes = new ArrayList<Integer>();
	// list of flights in an acc, it is very important to know the flightlist size
	// since I check it to delete the acc
	public ArrayList<Flight> flightList;

	private LinkedHashMap<String, Airport> airportList;

	public ACC(String accCode) {
		this.accCode = accCode;
		this.airportList = new LinkedHashMap<String, Airport>();
		flightList = new ArrayList<Flight>();
		this.waitingQueue = new PriorityQueue<Flight>(new FlightWaitingComparator());
		this.readyQueue = new PriorityQueue<Flight>(new FlightRunningComparator());
		this.admissionQueue = new PriorityQueue<Flight>(new FlightWaitingComparator());
	}

	// flight adder method, if admission time > 0 then it adds to admission queue
	// else to ready queue
	public void addFlight(Flight flight) {
		flightList.add(flight);
		if (flight.getAdmissionTime() != 0) {
			admissionQueue.add(flight);
		} else {
			readyQueue.add(flight);
		}
	}

	public void deleteFlight(Flight flight) {
		flightList.remove(flight);
	}

	public int getFlightListLength() {
		return flightList.size();
	}

	public void addAirport(Airport airport) {
		airportList.put(airport.getCode(), airport);
	}

	public Airport getAirport(String AirportCode) {
		return airportList.get(AirportCode);
	}

	public Map<String, Airport> getAirportList() {
		return this.airportList;
	}

	public String[] getAtcNames() {
		return atcNames;
	}

	public String getCode() {
		return this.accCode;
	}

	public PriorityQueue<Flight> getReadyQueue() {
		return readyQueue;
	}

	public PriorityQueue<Flight> getWaitingQueue() {
		return waitingQueue;
	}

	public PriorityQueue<Flight> getAdmissionQueue() {
		return admissionQueue;
	}

	// name printer method
	// uses a string and sorted arraylist of indexes to reach to each atcName
	public String printNames() {
		String names = "";
		this.indexes.sort(null);
		for (int i : indexes) {
			names += atcNames[i] + String.format("%03d", i) + " ";
		}
		return names;
	}

	// adding an atc, checking if an index is full, if full then increment index by
	// 1, if indexoutouf bounds then
	// make index 0
	public int addATC(int total, String airportCode) {
		while (true) {
			if (atcNames[total] == null) {
				atcNames[total] = airportCode;
				indexes.add(total);
				break;
			} else {

				total += 1;

				total = total % 1000;
			}
		}
		return total;
	}

}
