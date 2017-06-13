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

	public Chart(List<Cluster> clusters) {
		super();
		final JFreeChart chart = ChartFactory.createScatterPlot("Kmeans", "Average voltage", "Average angle",
				data2Plot(clusters));

		// force aliasing of the rendered content..
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final ChartPanel panel = new ChartPanel(chart, true);

		// Title to the dialog
		this.setTitle("K means classification");
		
		panel.setPreferredSize(new java.awt.Dimension(500, 270));
		// panel.setHorizontalZoom(true);
		// panel.setVerticalZoom(true);
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);

		setContentPane(panel);
	}

	private static XYDataset data2Plot(List<Cluster> clusters) {
		XYSeriesCollection data = new XYSeriesCollection();

		for (Cluster cluster : clusters) {
			XYSeries series = new XYSeries(cluster.getLabel());
			for (Point point : cluster.getPoints()) {
				series.add(Point.getAverage(point.getVoltage()), Point.getAverage(point.getAngle()));
			}
			data.addSeries(series);
		}

		return data;
	}
}
