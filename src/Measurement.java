
public class Measurement {

	private String rdfId, subRdf, name;
	private double value;
	
	public Measurement (String rdfId, String name, double value, String subRdf) {
		this.rdfId=rdfId;
		this.name=name;
		this.value=value;
		this.subRdf=subRdf;
	}
	
}
