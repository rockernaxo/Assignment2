import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KMeans {

	// Number of Clusters. This metric should be related to the number of points
	private static final int k = 4;
	private List<Point> points;
	private List<Cluster> clusters;

	public KMeans() {
		this.points = new ArrayList<Point>();
		this.clusters = new ArrayList<Cluster>();
		play();
	}

	public static void main(String[] args) {
		KMeans kmeans = new KMeans();
	}

	private void play() {

		initialize();
		calculate();

		// Once the KMeans is done, we create the final list of points in where
		// the cluster has been updated
		ArrayList<Point> class_points = new ArrayList<Point>();
		for (int i = 0; i < clusters.size(); i++) {
			for (int j = 0; j < clusters.get(i).getPoints().size(); j++) {
				Point p = (Point) clusters.get(i).getPoints().get(j);
				p.setClusterNumber(i);
				class_points.add(p);
			}
		}
		System.out.println("End");

	}

	// Initializes the process
	public void initialize() {
		// The values are taken from the database
		SQLdatabase info = new SQLdatabase();
		// The points of the system are created from the measurements of the
		// database.
		// Note that one point has 18 attributes (9 voltage values and 9 angle
		// values) which determine one state of the system during a certain time
		// The values of the database are ordered following an order in space
		// and time which will be used to define a point

		// Split by time
		List<Measurement> measList = info.getMeas();

		while (measList.size() != 0) {
			// Get time from the first element of the set
			double time = measList.get(0).getTime();
			// Create a placeholder which aggregates all measurements with the
			// same time
			List<Measurement> measTime = new ArrayList<Measurement>();
			// Iterate over all the measurements to group together those with
			// the same time
			for (Measurement meas : measList) {
				if (meas.getTime() == time) {
					measTime.add(meas);
				}
			}
			// Now there is a point with N+N voltage and angle measurements -
			// create a new point
			this.points.add(new Point(measTime, 0, time));
			// Remove the already added measurements from the list
			measList.removeAll(measTime);
		}

		// Create K Clusters. For the first iteration the centroids are randomly
		// selected as the first 4 points
		for (int i = 0; i < this.k; i++) {
			clusters.add(new Cluster(i, this.points.get(i)));
		}
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
				System.out.println("Cluster nº :"+cluster.getId()+" has "+cluster.getPoints().size()+" points");
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

		for (Point point : this.points) {
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
}