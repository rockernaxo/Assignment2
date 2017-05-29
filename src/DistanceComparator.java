import java.util.Comparator;

public class DistanceComparator implements Comparator<Distance> {
	    @Override
	    public int compare(Distance a, Distance b) {
	       return a.getDistance() < b.getDistance() ? -1 : a.getDistance() == b.getDistance() ? 0 : 1;
	    }
}
