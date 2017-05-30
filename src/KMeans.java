import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KMeans {

	// Number of Clusters. This metric should be related to the number of points
	private static final int k = 4;
	private List<Point> pointList, classifiedPoints;
	private List<Cluster> clusters;

	public KMeans(List<Point> pointList) {
		// Instantiate variables
		this.pointList = pointList;
		this.clusters = new ArrayList<Cluster>();
		this.classifiedPoints=new ArrayList<Point>();

		// Initialize the k clusters
		initializeClusters();
		// Classify the set of measurements
		play();
	}

	private void initializeClusters(){
		// Create K Clusters. For the first iteration the centroids are randomly
		// selected as the first k points
		for (int i = 0; i < k; i++) {
			this.clusters.add(new Cluster(i, this.pointList.get(i)));
		}
	}
	private void play() {
		
		calculate();

		// Once the KMeans is done, we create the final list of points in where
		// the cluster has been updated

		for (int i = 0; i < this.clusters.size(); i++) {
			for (int j = 0; j < this.clusters.get(i).getPoints().size(); j++) {
				Point p = (Point) this.clusters.get(i).getPoints().get(j);
				p.setClusterNumber(i);
				this.classifiedPoints.add(p);
			}
		}
		System.out.printf("\n\rEnd Kmeans clustering\n\n\r");

	}

	// The process to calculate the K Means, with iterating method.
	public void calculate() {

		boolean finish = false;
		int iteration = 0;

		// Add in new data, one at a time, recalculating centroids with each new
		// one.
		while (!finish) {
			iteration++;
			// Remove all the points from the clusters
			emptyClusters();

			// Save the current centroids in a list
			List<Point> lastCentroids = getCentroids();

			// Assign points to the closer cluster
			assignCluster();

			// Calculate new centroids.
			calculateCentroids();

			List<Point> currentCentroids = getCentroids();

			// Calculates total distance between new and old Centroids
			double distance = 0;

			for (int i = 0; i < lastCentroids.size(); i++) {
				distance += Point.euclideanDist(lastCentroids.get(i), currentCentroids.get(i));
			}

			System.out.println("#################");
			for (Cluster cluster : this.clusters) {
				System.out.println("Cluster nº :" + cluster.getId() + " has " + cluster.getPoints().size() + " points");
			}
			System.out.println("Iteration: " + iteration);
			System.out.println("Centroid distances: " + distance);

			if (distance == 0) {
				finish = true;
			}
		}
	}

	// Method to empty the clusters from the points they currently have
	private void emptyClusters() {
		for (Cluster cluster : clusters) {
			cluster.getPoints().clear();
		}
	}

	// Method to return the list of K centroids
	private List<Point> getCentroids() {
		List<Point> centroids = new ArrayList<Point>();
		for (Cluster cluster : this.clusters) {
			// int x = 0;
			// Point aux = cluster.getCentroid();
			// Point point = new
			// Point(aux.getV1(),aux.getV2(),aux.getV3(),aux.getV4(),aux.getV5(),aux.getV6(),aux.getV7(),aux.getV8(),aux.getV9()
			// ,aux.getA1(),aux.getA2(),aux.getA3(),aux.getA4(),aux.getA5(),aux.getA6(),aux.getA7(),aux.getA8(),aux.getA9(),x);
			centroids.add(cluster.getCentroid());
		}
		return centroids;
	}

	// Method to assign the points the existing clusters
	private void assignCluster() {

		for (Point point : this.pointList) {
			// Set a minimum distance of inf.
			double min = Double.MAX_VALUE;
			int nCluster = -1;

			for (int i = 0; i < this.clusters.size(); i++) {
				// Calculate the distance to the i cluster
				double distance = Point.euclideanDist(point, this.clusters.get(i).getCentroid());
				if (distance < min) {
					// Update the minimum distance and the cluster the point
					// belongs to.
					min = distance;
					nCluster = i;
				}
			}
			// The point is added to the closest cluster
			point.setClusterNumber(nCluster);
			this.clusters.get(nCluster).getPoints().add(point);
		}
	}

	// Method to calculate the new centroids
	private void calculateCentroids() {
		// Iterate over the newly populated clusters
		for (Cluster cluster : this.clusters) {
			List<Point> pointList = cluster.getPoints();
			int nPoints = pointList.size();

			// Initialize voltage and angle list of the centroid with all zeroes
			int nMeas = pointList.get(0).getVoltage().size();
			List<Double> voltage = new ArrayList<Double>(Collections.nCopies(nMeas, 0.0));
			List<Double> angle = new ArrayList<Double>(Collections.nCopies(nMeas, 0.0));

			// Iterate over all the points of the cluster to calculate the new
			// centroid
			for (Point point : pointList) {
				// Iterate over the N voltages and add them together
				for (int i = 0; i < point.getVoltage().size(); i++) {
					double newvolt = point.getVoltage().get(i);
					double lastvolt = voltage.get(i);
					voltage.set(i, lastvolt + newvolt);
				}

				// Iterate over the N voltages and add them together
				for (int i = 0; i < point.getVoltage().size(); i++) {
					angle.set(i, angle.get(i) + point.getAngle().get(i));
				}
			}

			// Normalize the quantities dividing by n
			if (nPoints > 0) {
				// Iterate over the N voltages and add them together
				for (int i = 0; i < voltage.size(); i++) {
					voltage.set(i, voltage.get(i) / nPoints);
				}

				// Iterate over the N voltages and add them together
				for (int i = 0; i < angle.size(); i++) {
					angle.set(i, angle.get(i) / nPoints);
				}
				// Set the new centroid of the cluster
				cluster.setCentroid(new Point(voltage, angle));
			}
		}
	}

	public List<Point> getClassifiedPoints() {
		return classifiedPoints;
	}
}