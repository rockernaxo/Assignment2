

import java.util.ArrayList;
import java.util.List;

//import com.sun.javafx.scene.paint.GradientUtils.Point;

public class Cluster {
	
	public List points;
	public Point centroid;
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList();
		this.centroid = null;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public List getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}

	public void setPoints(List points) {
		this.points = points;
	}



	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	

}