
public class Airport {
	// airport class having its own code, atc, and acc
	private String airportCode;
	private ATC airportAtc;
	private ACC areaControlCenter;

	public Airport(String code, ACC acc) {
		//constructor, adding itself to airportlist of its acc
		this.airportCode = code;
		this.areaControlCenter = acc;
		this.areaControlCenter.addAirport(this);
		// creating its atc in here since each airport has one and only one atc
		ATC atc = new ATC(areaControlCenter, this);		
		this.airportAtc = atc;
	}

	//not much functions just getters because most operations are in atc
	
	public String getCode() {
		return this.airportCode;
	}

	public ACC getAcc() {
		return areaControlCenter;
	}

	public ATC getAtc() {
		return this.airportAtc;
	}
}
