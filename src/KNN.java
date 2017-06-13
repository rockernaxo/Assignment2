import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KNN {

	private List<Point> pointList, testPointList;
	private int k;

	public KNN(List<Point> testPointList, List<Point> pointList, int k) {
		this.testPointList = testPointList;
		this.pointList = pointList;
		this.k = k;

		for (Point point : testPointList) {
			go(point);
		}
	}

	public void go(Point testPoint) {

		// Create object Distance that contains distance to a specific point and
		// the cluster number
		ArrayList<Distance> distanceList = new ArrayList<Distance>();

		for (Point point : this.pointList) {
			// Calculate the distance – Euclidean distance
			double distance = Point.euclideanDist(point, testPoint);
			distanceList.add(new Distance(distance, point.getClusterNumber()));
		}

		// Sort the distance List in increasing order
		Collections.sort(distanceList, new DistanceComparator());

		// Print the distance for the 4 closest neighbors
		for (int x = 0; x < this.k; x++) {
			System.out.println("Cluster nº " + distanceList.get(x).getNCluster() + " distance "
					+ distanceList.get(x).getDistance());
		}

		// Assign the test point to the most common cluster
		testPoint.setClusterNumber(assignCluster(distanceList, k));

		System.out.printf("The result is: \n The point belongs to cluster: " + testPoint.getClusterNumber() + "\n");
		System.out.println("###############");
	}

	private int assignCluster(ArrayList<Distance> distanceList, int k) {

		int nCluster = -1;

		ArrayList<Integer> count = new ArrayList<Integer>(Collections.nCopies(k, 0));

		// Iterate over the K closest neighbors
		for (int i = 0; i < k; i++) {
			int clusterIndex = distanceList.get(i).getNCluster();
			count.set(clusterIndex, count.get(clusterIndex) + 1);
		}

		// Return the most common
		int maxCount = 0;
		for (int i = 0; i < count.size(); i++) {
			if (count.get(i) > maxCount) {
				maxCount = count.get(i);
				nCluster = i;
			}
		}

		return nCluster;
	}

	public List<Point> getResults() {
		return this.testPointList;
	}
}
