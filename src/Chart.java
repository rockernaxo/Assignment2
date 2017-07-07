import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JDialog;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@SuppressWarnings("serial")
public class Chart extends JDialog {

	public Chart(List<Cluster> clusters, List<Point> classifiedPoints, String title, String subtitle) {
		super();
		final JFreeChart chart = ChartFactory.createScatterPlot(subtitle, "Average voltage", "Average angle",
				data2Plot(clusters, classifiedPoints));

		// force aliasing of the rendered content..
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setTitle(title);

		final ChartPanel panel = new ChartPanel(chart, true);

		panel.setPreferredSize(new java.awt.Dimension(500, 270));
		// panel.setHorizontalZoom(true);
		// panel.setVerticalZoom(true);
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);

		setContentPane(panel);
	}

	private static XYDataset data2Plot(List<Cluster> clusters, List<Point> classifiedPoints) {
		XYSeriesCollection data = new XYSeriesCollection();

		for (Cluster cluster : clusters) {
			XYSeries series = new XYSeries(cluster.getLabel());
			for (Point point : classifiedPoints) {
				if (cluster.getId() == point.getClusterNumber()) {
					series.add(Point.getAverage(point.getVoltage()), Point.getAverage(point.getAngle()));
				}
			}
			data.addSeries(series);
		}

		return data;
	}
}
