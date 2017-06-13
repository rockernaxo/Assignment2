import java.awt.RenderingHints;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

public class Chart extends ApplicationFrame {

	public Chart(String title, List<Cluster> clusters) {
		super(title);
		final JFreeChart chart = ChartFactory.createScatterPlot("Kmeans", "Average voltage", "Average angle",
				data2Plot(clusters));

		// force aliasing of the rendered content..
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
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
