import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNN {
	
	List<Point> pointList;
	

	public KNN() {
		this.pointList = new ArrayList<Point>();
	}
	
	public void initialize () {
		
		
	}
	
	public void go(Point testPoint) {
		
		// Create object Result that contains distance to a specific flower and
		// flower’s type
		ArrayList<Distance> distanceList = new ArrayList<Distance>();

		for (Point point : this.pointList) {
			// Calculate the variable distance – square of Euclidean distance from the test flower
			double distance = Point.euclideanDist(point, testPoint);
			distanceList.add(new Distance(distance, point.getClusterNumber()));
		}

		Collections.sort(distanceList, new DistanceComparator());
		int k = 4;
		for (int x = 0; x < k; x++) {
			System.out.println(distanceList.get(x).getNCluster() + "…" + distanceList.get(x).getDistance());
		}

		// Get the K neighbors and assign the type to the test flower
		
		//test.setType(getType(resultList, k));

		//System.out.printf("The result is: \n The flower is: " + test.getType());
	}
	
	private void assignCluster() {
		
	}
}
