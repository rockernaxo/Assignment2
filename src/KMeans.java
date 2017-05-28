import java.util.ArrayList;
import java.util.List;
public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
    private int k = 4;      
    private List<Point> points;
    private static List<Cluster> clusters;
    
    public KMeans() {
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();     	
    }
    
    public static void main(String[] args) {
    	
    	KMeans kmeans = new KMeans();    	
    	kmeans.initialize();
    	kmeans.calculate();
    	
    	//Once the KMeans is done, we create the final list of points in where the cluster has been updated
    	ArrayList<Point> class_points= new ArrayList<Point>();
    	for (int i=0; i<clusters.size();i++){
    		for (int j=0; j<clusters.get(i).getPoints().size();j++){
    			Point p = (Point)clusters.get(i).getPoints().get(j);
    			p.setCluster(i);
    			class_points.add(p);
    	}
    	}    	
    	System.out.println("End");
    	
    }
    
    //Initializes the process
    public void initialize() {
    	//The values are taken from the database
    	SQLdatabase info = new SQLdatabase();
    	//The points of the system are created from the measurements of the database. 
    	//Note that one point has 18 attributes (9 voltage values and 9 angle values) which determine one state of the system during a certain time
    	//The values of the database are ordered following an order in space and time which will be used to define a point
    	for (int i=0; i< info.getMeas().size();i=i+18){
    		int x = 0;
    		Point p = new Point(info.getMeas().get(i).getValue(), info.getMeas().get(i+1).getValue(), info.getMeas().get(i+2).getValue(), info.getMeas().get(i+3).getValue()
    				, info.getMeas().get(i+4).getValue(), info.getMeas().get(i+5).getValue(), info.getMeas().get(i+6).getValue(), info.getMeas().get(i+7).getValue()
    				, info.getMeas().get(i+8).getValue(), info.getMeas().get(i+9).getValue(), info.getMeas().get(i+10).getValue(), info.getMeas().get(i+11).getValue()
    				, info.getMeas().get(i+12).getValue(), info.getMeas().get(i+13).getValue(), info.getMeas().get(i+14).getValue(), info.getMeas().get(i+15).getValue()
    				, info.getMeas().get(i+16).getValue(), info.getMeas().get(i+17).getValue(),x);
    		points.add(p);
    	}
    	    	
    	//Create Clusters. For the first iteration the centroids are randomly selected as the first 4 points
    	for (int i = 0; i < k; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = points.get(i);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	    	
    }

	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state
        	clearClusters();        	
        	List<Point> lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List<Point> currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List getCentroids() {
    	List centroids = new ArrayList(k);
    	for(Cluster cluster : clusters) {
    		int x = 0;
    		Point aux = cluster.getCentroid();
    		Point point = new Point(aux.getV1(),aux.getV2(),aux.getV3(),aux.getV4(),aux.getV5(),aux.getV6(),aux.getV7(),aux.getV8(),aux.getV9()
    				,aux.getA1(),aux.getA2(),aux.getA3(),aux.getA4(),aux.getA5(),aux.getA6(),aux.getA7(),aux.getA8(),aux.getA9(),x);
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	min = max;
            for(int i = 0; i < k; i++) {
            	Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumV1 = 0;
            double sumV2 = 0;
            double sumV3 = 0;
            double sumV4 = 0;
            double sumV5 = 0;
            double sumV6 = 0;
            double sumV7 = 0;
            double sumV8 = 0;
            double sumV9 = 0;
            double sumA1 = 0;
            double sumA2 = 0;
            double sumA3 = 0;
            double sumA4 = 0;
            double sumA5 = 0;
            double sumA6 = 0;
            double sumA7 = 0;
            double sumA8 = 0;
            double sumA9 = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();
            
            for(Point point : list) {
            	sumV1 += point.getV1();
            	sumV2 += point.getV2();
            	sumV3 += point.getV3();
            	sumV4 += point.getV4();
            	sumV5 += point.getV5();
            	sumV6 += point.getV6();
            	sumV7 += point.getV7();
            	sumV8 += point.getV8();
            	sumV9 += point.getV9();
            	sumA1 += point.getA1();
            	sumA2 += point.getA2();
            	sumA3 += point.getA3();
            	sumA4 += point.getA4();
            	sumA5 += point.getA5();
            	sumA6 += point.getA6();
            	sumA7 += point.getA7();
            	sumA8 += point.getA8();
            	sumA9 += point.getA9();
            }
            
            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
            	double newV1 = sumV1 / n_points;
            	double newV2 = sumV2 / n_points;
            	double newV3 = sumV3 / n_points;
            	double newV4 = sumV4 / n_points;
            	double newV5 = sumV5 / n_points;
            	double newV6 = sumV6 / n_points;
            	double newV7 = sumV7 / n_points;
            	double newV8 = sumV8 / n_points;
            	double newV9 = sumV9 / n_points;
            	double newA1 = sumA1 / n_points;
            	double newA2 = sumA2 / n_points;
            	double newA3 = sumA3 / n_points;
            	double newA4 = sumA4 / n_points;
            	double newA5 = sumA5 / n_points;
            	double newA6 = sumA6 / n_points;
            	double newA7 = sumA7 / n_points;
            	double newA8 = sumA8 / n_points;
            	double newA9 = sumA9 / n_points;
                centroid.setA1(newA1);
                centroid.setA2(newA2);
                centroid.setA3(newA3);
                centroid.setA4(newA4);
                centroid.setA5(newA5);
                centroid.setA6(newA6);
                centroid.setA7(newA7);
                centroid.setA8(newA8);
                centroid.setA9(newA9);
                centroid.setV1(newV1);
                centroid.setV2(newV2);
                centroid.setV3(newV3);
                centroid.setV4(newV4);
                centroid.setV5(newV5);
                centroid.setV6(newV6);
                centroid.setV7(newV7);
                centroid.setV8(newV8);
                centroid.setV9(newV9);
            }
        }
    }
}