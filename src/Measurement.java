
public class Measurement {

	private String rdfId, subRdf, name;
	private double value, time;
	
	public Measurement (String rdfId, String name, double value, String subRdf, double time) {
		this.rdfId=rdfId;
		this.name=name;
		this.value=value;
		this.subRdf=subRdf;
		this.time=time;
	}

	public String getRdfId() {
		return rdfId;
	}

	public String getSubRdf() {
		return subRdf;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public double getTime() {
		return time;
	}
	
}
