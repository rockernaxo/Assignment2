import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import demo.FastScatterPlotDemo;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private JTextField txtDatabase;
	private JTextField txtTest;
	private JTextField txtLearn;
	private List<Measurement> learnSet, testSet;
	private KMeans kmeans;

	private final CardLayout cl = new CardLayout();
	private final JButton btnLoad = new JButton("Load");
	private final JPanel panel = new JPanel();
	private final JPanel sql = new JPanel();
	private final JPanel main = new JPanel();
	private final JLabel label = new JLabel("");
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JLabel lblKMeans = new JLabel("KNN");
	private final JLabel lblKnn = new JLabel("K means");
	private final JButton btnLearn = new JButton("Learn");
	private final JButton btnTest = new JButton("Test");
	private final JTextField txtClusters = new JTextField();
	private final JTextField txtNeighbor = new JTextField();
	private final JLabel lblNumberOfNeighbors = new JLabel("Number of clusters");
	private final JLabel lblNumberOfClusters = new JLabel("Number of neighbors");
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JPanel panel_6 = new JPanel();

	private SQLdatabase sqldb;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Window("hola");
			}

		});
	}

	public Window(final String title) {
		panel.setLayout(cl);

		JLabel lblSqlDatabase = new JLabel("SQL Database");
		lblSqlDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblSqlDatabase.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// Add objects to the panel container
		this.panel.add(this.sql, "1");
		sql.setLayout(new GridLayout(3, 8, 0, 0));
		sql.add(lblSqlDatabase);

		sql.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 6, 0, 0));

		JLabel lblDatabase = new JLabel("Database");
		panel_1.add(lblDatabase);

		txtDatabase = new JTextField();
		panel_1.add(txtDatabase);
		txtDatabase.setText("subtables");
		txtDatabase.setColumns(10);

		JLabel lblNewLabel = new JLabel("Test set table");
		panel_1.add(lblNewLabel);

		txtTest = new JTextField();
		panel_1.add(txtTest);
		txtTest.setText("analog_values");
		txtTest.setColumns(10);

		JLabel lblLearningTestTable = new JLabel("Learning set table");
		panel_1.add(lblLearningTestTable);

		txtLearn = new JTextField();
		panel_1.add(txtLearn);
		txtLearn.setText("measurements");
		txtLearn.setColumns(10);

		sql.add(panel_2);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// The values are taken from the database
				buildDatabase(txtDatabase.getText(), txtLearn.getText(), txtTest.getText());
			}
		});
		panel_2.add(btnLoad);

		panel.add(label, "2");
		this.panel.add(this.main, "2");
		main.setLayout(new GridLayout(3, 2, 0, 0));
		lblKnn.setHorizontalAlignment(SwingConstants.CENTER);
		lblKnn.setFont(new Font("Tahoma", Font.PLAIN, 30));

		main.add(lblKnn);
		lblKMeans.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblKMeans.setHorizontalAlignment(SwingConstants.CENTER);

		main.add(lblKMeans);

		main.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));
		panel_5.add(lblNumberOfNeighbors);
		txtClusters.setHorizontalAlignment(SwingConstants.CENTER);
		txtClusters.setEditable(false);
		txtClusters.setEnabled(false);
		panel_5.add(txtClusters);
		txtClusters.setText("4");
		txtClusters.setColumns(10);
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setVgap(25);

		main.add(panel_6);
		panel_6.add(lblNumberOfClusters);
		txtNeighbor.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(txtNeighbor);
		txtNeighbor.setText("4");
		txtNeighbor.setColumns(10);

		main.add(panel_3);
		btnLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				call2Kmeans(Integer.parseInt(txtClusters.getText()));
			}
		});
		panel_3.add(btnLearn);

		main.add(panel_4);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				call2KNN(Integer.parseInt(txtNeighbor.getText()));
			}
		});
		panel_4.add(btnTest);

		// Add the container to the Jframe
		cl.show(this.panel, "1");
		getContentPane().add(this.panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private void buildDatabase(String database, String learning, String test) {
		this.sqldb = new SQLdatabase(database, learning, test);
		this.cl.show(this.panel, "2");
		this.learnSet = new ArrayList<Measurement>(this.sqldb.getMeas());
		this.testSet = new ArrayList<Measurement>(this.sqldb.getMeasTest());

	}

	private void call2Kmeans(int clusters) {
		this.kmeans = new KMeans(SQLdatabase.splitByTime(this.learnSet));
		// Display the results in a new window
		Chart chart = new Chart("K Means results", this.kmeans.getClusters());
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);

	}

	private void call2KNN(int neighbors) {
		KNN knn = new KNN(SQLdatabase.splitByTime(testSet), kmeans.getClassifiedPoints(), neighbors);
		List<Point> results = knn.getResults();
		
		JFrame hola = new JFrame();
		JTextArea txtResult = new JTextArea();
		   
	     for (Point point : results) {
	        txtResult.append("The point belongs to cluster " + point.getClusterNumber()+"\n");
	     }
   
	   
	   hola.add(txtResult);
	   
	   hola.setDefaultCloseOperation(EXIT_ON_CLOSE);
	   hola.pack();
	   hola.setVisible(true);
	   
	   
	}
}