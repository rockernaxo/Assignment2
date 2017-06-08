
import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private List<Point> points;
	private Point centroid;
	private int id;
	private String label;

	// Creates a new Cluster
	public Cluster(int id, Point centroid) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = centroid;
		this.label="";
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public List<Point> getPoints() {
		return points;
	}

	public int getId() {
		return id;
	}
	
	public void addPoint(Point point) {
		this.points.add(point);
	}
	
	public void setLabel(String label){
		this.label=label;
	}
	
	public String getLabel(){
		return this.label;
	}
}