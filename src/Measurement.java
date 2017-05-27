
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
	
}
