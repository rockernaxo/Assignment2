import java.util.ArrayList;
import java.util.List;

public class Labelling {
	private List<Cluster> cluster;
	private List<Double> flowList;
	private double flow;
	private double E1, E4, E6, E3, E2, E8;
	private double A1, A4, A6, A3, A2, A8;

	public Labelling (List<Cluster> cluster) {
		List<Double> flowList = new ArrayList<Double>();
		double flow;
		double E1, E4, E6, E3, E2, E8;
		double A1, A4, A6, A3, A2, A8;
		
		for (int i = 0; i < cluster.size(); i++) {
			E1=cluster.get(i).getCentroid().getVoltage().get(0);
			E4=cluster.get(i).getCentroid().getVoltage().get(3);
			E6=cluster.get(i).getCentroid().getVoltage().get(5);
			E3=cluster.get(i).getCentroid().getVoltage().get(2);
			E2=cluster.get(i).getCentroid().getVoltage().get(1);
			E8=cluster.get(i).getCentroid().getVoltage().get(8);
			A1=cluster.get(i).getCentroid().getAngle().get(0);
			A4=cluster.get(i).getCentroid().getAngle().get(3);
			A6=cluster.get(i).getCentroid().getAngle().get(5);
			A3=cluster.get(i).getCentroid().getAngle().get(2);
			A2=cluster.get(i).getCentroid().getAngle().get(1);
			A8=cluster.get(i).getCentroid().getAngle().get(8);
		
			flow=(Math.abs(E1*E4*Math.sin(Math.toRadians(E1-E4)))+ Math.abs(E6*E3*Math.sin(Math.toRadians(E6-E3)))
					+Math.abs(E2*E8*Math.sin(Math.toRadians(E2-E8))))/3;
			flowList.add(flow);
		}
		
		System.out.print(flowList);
		// TODO Auto-generated method stub

	}
}
