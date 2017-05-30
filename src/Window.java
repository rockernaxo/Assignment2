import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	public static void main(String[] args) {
		// The values are taken from the database
		SQLdatabase info = new SQLdatabase();
		// The points of the system are created from the measurements of the
		// database.
		// Note that one point has 18 attributes (9 voltage values and 9 angle
		// values) which determine one state of the system during a certain time
		// The values of the database are ordered following an order in space
		// and time which will be used to define a point
		
		int k = 6;
		List<Measurement> learnSet = new ArrayList<Measurement>(info.getMeas());
		List<Measurement> testSet = new ArrayList<Measurement>(info.getMeasTest());
		
		KMeans kmeans = new KMeans(SQLdatabase.splitByTime(learnSet));
		KNN knn  =new KNN (SQLdatabase.splitByTime(testSet), kmeans.getClassifiedPoints(), k);
	}

}