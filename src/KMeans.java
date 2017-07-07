import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class KMeans {

	// Number of Clusters. This metric should be related to the number of points
	private static final int k = 4;
	private List<Point> pointList, classifiedPoints;
	private List<Cluster> clusters;
	// This boolean indicates if it is the system of the exercise or another
	private boolean label;

	public KMeans(List<Point> pointList, boolean label) {
		// Instantiate variables
		this.pointList = pointList;
		this.clusters = new ArrayList<Cluster>();
		this.classifiedPoints = new ArrayList<Point>();
		this.label = label;

		// Initialize the k clusters
		initializeClusters();
		// Classify the set of measurements
		play();
		// Identify clusters with predefined tags
		identify();
	}

	// This method creates K Clusters choosing randomly its centroids
	private void initializeClusters() {
		int[] randomNumbers = uniqueRandomNumbers(k);
		for (int i = 0; i < randomNumbers.length; i++) {
			this.clusters.add(new Cluster(i, this.pointList.get(randomNumbers[i])));
		}
	}

	// This method calls to the kMeans calculation and sort the points into the
	// cluster list
	private void play() {

		calculate();

		// Once the KMeans is done, we create the final list of points in where
		// the cluster has been updated

		for (int i = 0; i < this.clusters.size(); i++) {
			for (int j = 0; j < this.clusters.get(i).getPoints().size(); j++) {
				Point point = this.clusters.get(i).getPoints().get(j);
				point.setClusterNumber(i);
				this.classifiedPoints.add(point);
			}
		}
		System.out.printf("\n\rEnd Kmeans clustering\n\n\r");

	}

	// This method is the core of the KMeans calculation.
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

			// Store new centroids in a list
			List<Point> currentCentroids = getCentroids();

			// Calculates total distance between new and old Centroids
			double distance = 0;

			// Iterate over the previous centroids and calculate the Euclidean
			// distance to the new centroids.
			for (int i = 0; i < lastCentroids.size(); i++) {
				distance += Point.euclideanDist(lastCentroids.get(i), currentCentroids.get(i));
			}

			// Printing operations to see the results in the console
			System.out.println("#################");
			for (Cluster cluster : this.clusters) {
				System.out.println("Cluster nº :" + cluster.getId() + " has " + cluster.getPoints().size() + " points");
			}
			System.out.println("Iteration: " + iteration);
			System.out.println("Centroid distances: " + distance);

			// When the distance between the last and the current centroids is
			// zero, the classification has finished.
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
				System.out.println("Distance to centroid " + this.clusters.get(i).getCentroid().getClusterNumber() + " "
						+ distance);
				if (distance < min) {
					// Update the minimum distance and the cluster the point
					// belongs to.
					min = distance;
					nCluster = i;
				}
			}
			// The point is added to the closest cluster
			point.setClusterNumber(nCluster);
			this.clusters.get(nCluster).addPoint(point);
		}
	}

	// Method to calculate the new centroids
	private void calculateCentroids() {
		// Iterate over the newly populated clusters
		for (Cluster cluster : this.clusters) {
			List<Point> pointList = cluster.getPoints();
			int nPoints = pointList.size();

			if (nPoints > 0) {
				// Initialize voltage and angle list of the centroid with all
				// zeroes
				int nMeas = pointList.get(0).getVoltage().size();
				List<Double> voltage = new ArrayList<Double>(Collections.nCopies(nMeas, 0.0));
				List<Double> angle = new ArrayList<Double>(Collections.nCopies(nMeas, 0.0));

				// Iterate over all the points of the cluster to calculate the
				// new
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
		return this.classifiedPoints;
	}

	public List<Cluster> getClusters() {
		return this.clusters;
	}

	// This method assigns the correspondent labels to the clusters.
	// If we are using the system with 4 states, as it was stated on the
	// exercise, the labeling is done depending on the conditions of the cluster
	// as it is explained on the attached Word document. Otherwise, it just
	// assigns a number as a label
	private void identify() {
		if (this.label && (this.clusters.get(0).getPoints().get(0).getAngle().isEmpty()
				|| this.clusters.get(0).getPoints().get(0).getVoltage().isEmpty())) {
			this.label = false;
			JOptionPane.showMessageDialog(null, "Only use specific labeling for the exercise's database");
		}
		if (this.label) {
			try {
				// Create a new variable list
				List<Cluster> newClusters = new ArrayList<Cluster>(this.clusters);

				double maxVolt = 0.0;
				double maxAng = 0.0;
				int clNumber = 0;

				// Identify the line disconnected
				for (int i = 0; i < this.clusters.size(); i++) {
					double avVolt = Point.getAverage(this.clusters.get(i).getCentroid().getVoltage());
					double avAng = Point.getAverage(this.clusters.get(i).getCentroid().getAngle());
					if (avVolt > maxVolt && avAng > maxAng) {
						maxVolt = avVolt;
						maxAng = avAng;
						clNumber = i;
					}
				}

				newClusters.get(clNumber).setLabel("Line disconnected");

				// Identify the low load rate
				double minVolt = Double.MAX_VALUE;
				double minAng = Double.MAX_VALUE;
				int clNumber2 = 0;

				// Identify the line disconnected
				for (int i = 0; i < this.clusters.size(); i++) {
					double avVolt = Point.getAverage(this.clusters.get(i).getCentroid().getVoltage());
					double avAng = Point.getAverage(this.clusters.get(i).getCentroid().getAngle());
					if (avVolt < minVolt && avAng < minAng) {
						minVolt = avVolt;
						minAng = avAng;
						clNumber2 = i;
					}
				}

				newClusters.get(clNumber2).setLabel("Low load rate");

				// Remove already tagged clusters
				newClusters.remove(this.clusters.get(clNumber2));
				newClusters.remove(this.clusters.get(clNumber));

				// Identify the remaining 2 by means of the power flows
				List<Double> flowList = new ArrayList<Double>();
				double flow;
				double E1, E4, E6, E3, E2, E8;
				double A1, A4, A6, A3, A2, A8;

				// Calculate the flow for each of the target situations
				for (int i = 0; i < newClusters.size(); i++) {
					E1 = newClusters.get(i).getCentroid().getVoltage().get(0);
					E4 = newClusters.get(i).getCentroid().getVoltage().get(3);
					E6 = newClusters.get(i).getCentroid().getVoltage().get(5);
					E3 = newClusters.get(i).getCentroid().getVoltage().get(2);
					E2 = newClusters.get(i).getCentroid().getVoltage().get(1);
					E8 = newClusters.get(i).getCentroid().getVoltage().get(8);
					A1 = newClusters.get(i).getCentroid().getAngle().get(0);
					A4 = newClusters.get(i).getCentroid().getAngle().get(3);
					A6 = newClusters.get(i).getCentroid().getAngle().get(5);
					A3 = newClusters.get(i).getCentroid().getAngle().get(2);
					A2 = newClusters.get(i).getCentroid().getAngle().get(1);
					A8 = newClusters.get(i).getCentroid().getAngle().get(8);

					flow = (Math.abs(E1 * E4 * Math.sin(Math.toRadians(E1 - E4)))
							+ Math.abs(E6 * E3 * Math.sin(Math.toRadians(E6 - E3)))
							+ Math.abs(E2 * E8 * Math.sin(Math.toRadians(E2 - E8)))) / 3;
					flowList.add(flow);
				}

				newClusters.get(flowList.indexOf(Collections.max(flowList))).setLabel("High Load rate");
				newClusters.get(flowList.indexOf(Collections.min(flowList))).setLabel("Generator shutdown");
			} catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "Only use specific labeling for the exercise's database");
				for (int i = 0; i < this.clusters.size(); i++) {
					this.clusters.get(i).setLabel(Integer.toString(i));
				}
			}
		} else {
			for (int i = 0; i < this.clusters.size(); i++) {
				this.clusters.get(i).setLabel(Integer.toString(i));
			}
		}
	}

	// This method generates k different random numbers
	// Credits:
	// https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java
	private int[] uniqueRandomNumbers(int k) {
		int[] result = new int[k];
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 1; i < this.pointList.size(); i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);

		for (int i = 0; i < k; i++) {
			result[i] = list.get(i);
		}

		return result;
	}
}
