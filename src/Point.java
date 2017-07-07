
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {

	private List<Double> voltage, angle;
	private List<Measurement> measList;
	private double time;
	private int clusterNumber;

	public Point(List<Measurement> measList, int clusterNumber, double time) {

		this.clusterNumber = clusterNumber;
		this.time = time;
		this.measList = measList;

		this.voltage = new ArrayList<Double>();
		this.angle = new ArrayList<Double>();

		for (Measurement meas : this.measList) {
			if (meas.getName().contains("VOLT")) {
				this.voltage.add(meas.getValue());
			}

			if (meas.getName().contains("ANG")) {
				this.angle.add(meas.getValue());
			}
		}
	}

	public Point(List<Double> voltage, List<Double> angle) {
		this.voltage = voltage;
		this.angle = angle;
	}

	public List<Double> getVoltage() {
		return voltage;
	}

	public List<Double> getAngle() {
		return angle;
	}
	
	public double getTime(){
		return this.time;
	}

	public int getClusterNumber() {
		return clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

	public List<Measurement> getMeasList() {
		return this.measList;
	}

	// Calculates the distance between two points.
	protected static double euclideanDist(Point object1, Point object2) {
		double distance = 0.0;
		int maxSize1 = Math.max(object1.getAngle().size(), object1.getVoltage().size());
		int maxSize2 = Math.max(object2.getAngle().size(), object2.getVoltage().size());

		if (maxSize1 == maxSize2) {
			for (int i = 0; i < maxSize1; i++) {
				double voltage1, voltage2, angle1, angle2;

				// Deal with 1 dimensionality in the below formula
				if (object1.getAngle().isEmpty()) {
					angle1 = 0;
				} else {
					angle1 = object1.getAngle().get(i);
				}

				if (object1.getVoltage().isEmpty()) {
					voltage1 = 0;
				} else {
					voltage1 = object1.getVoltage().get(i);
				}

				if (object2.getAngle().isEmpty()) {
					angle2 = 0;
				} else {
					angle2 = object2.getAngle().get(i);
				}

				if (object2.getVoltage().isEmpty()) {
					voltage2 = 0;
				} else {
					voltage2 = object2.getVoltage().get(i);
				}

				distance += Math.pow(voltage1 - voltage2, 2) + Math.pow(angle2 - angle1, 2);
			}
		} else {
			System.out.println("There is an error in the database");
		}
		return Math.sqrt(distance);
	}

	// Method to calculate the average voltage and angle
	public static double getAverage(List<Double> measure) {
		double average = 0;

		for (int i = 0; i < measure.size(); i++) {
			average += measure.get(i);
		}

		return average / measure.size();

	}
}