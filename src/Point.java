
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {

	private List<Double> voltage, angle;
	private double time;
	private int clusterNumber;

	public Point(List<Measurement> measList, int clusterNumber, double time) {

		this.clusterNumber = clusterNumber;
		this.time=time;

		this.voltage = new ArrayList<Double>();
		this.angle = new ArrayList<Double>();

		for (Measurement meas : measList) {
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

	public int getClusterNumber() {
		return clusterNumber;
	}
	
	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber=clusterNumber;
	}

	// Calculates the distance between two points.
	protected static double euclideanDist(Point object1, Point object2) {
		double distance = 0.0;
		if (object1.getVoltage().size() == object2.getVoltage().size()) {
			for (int i=0; i<object1.getVoltage().size(); i++) {
				distance+=Math.pow(object1.getVoltage().get(i)-object2.getVoltage().get(i),2)+
						Math.pow(object1.getAngle().get(i)-object1.getAngle().get(i),2);
			}
		} else {
			System.out.println("There is an error in the database");
		}
		return Math.sqrt(distance);
	}
	
	// Method to calculate the average voltage and angle
	public static double getAverage(List<Double> measure){
		double average=-1;
		
		for (int i=0; i<measure.size(); i++) {
			average+=measure.get(i);
		}
		
		return average/measure.size();
		
	}
}