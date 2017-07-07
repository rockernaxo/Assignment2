import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class Window {
	private JTextField txtDatabase;
	private JTextField txtTest;
	private JTextField txtLearn;
	private List<Measurement> learnSet, testSet;
	private List<Cluster> clusters;
	private List<Point> classifiedPoints;

	private final static int frameWidth = 600;
	private final static int frameLength = 700;
	private final static Font titleFont = new Font("SansSerif", Font.BOLD, 40);
	private final static Font textFont = new Font("SansSerif", Font.PLAIN, 15);
	private final static Font textFontBold = new Font("SansSerif", Font.BOLD, 15);
	private final static Font buttonFont = new Font("SansSerif", Font.PLAIN, 20);
	private final static Color darkBlue = new Color(51, 153, 255, 255);
	private final CardLayout cl = new CardLayout();
	private final JButton btnLoad = new JButton("Load");
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JPanel panel_6 = new JPanel();
	private final JPanel panel_7 = new JPanel();
	private final JButton btnTest = new JButton("Test");

	private SQLdatabase sqldb;
	private final JRadioButton tableShow = new JRadioButton("Table");
	private final JRadioButton plotShow = new JRadioButton("Plot ");
	private final JRadioButton customOp = new JRadioButton("General labeling");
	private final JRadioButton normalOp = new JRadioButton("Specific labeling");
	private final JLabel lblOptions = new JLabel("Options");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup2 = new ButtonGroup();

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Window();
			}

		});
	}

	public Window() {

		JFrame window = new JFrame();
		window.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 45));
		createPanels();

		// Add the container to the Jframe
		cl.show(this.panel, "1");

		window.getContentPane().add(this.panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(frameWidth, frameLength));
		window.setTitle("Assignment 2");
		window.setResizable(false);
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);

	}

	// This method creates the panels of the CardLayout: SQL and Main
	private void createPanels() {
		this.panel.setLayout(cl);

		this.panel.add(createSQLMenu(), "1");
		this.panel.add(createMainMenu(), "2");
	}

	// This method creates the first card of the CardLayout, called S
	private JPanel createSQLMenu() {

		JPanel sql = new JPanel(new GridBagLayout());

		// Set the constraints for the layout
		GridBagConstraints gblayoutCons = new GridBagConstraints();
		gblayoutCons.fill = GridBagConstraints.BOTH;

		// Title label for the SQL screen
		JLabel lblSqlDatabase = new JLabel("SQL Database");
		lblSqlDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblSqlDatabase.setFont(titleFont);
		gblayoutCons.gridy = 0;
		gblayoutCons.gridx = 1;
		gblayoutCons.insets = new Insets(0, 0, 50, 0);
		sql.add(lblSqlDatabase, gblayoutCons);

		// Create a new panel to store the input textfields and their labels
		gblayoutCons.gridy = 1;
		gblayoutCons.anchor = GridBagConstraints.CENTER;
		sql.add(panel_1, gblayoutCons);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		// Group layout to align labels with textboxes
		GroupLayout glayout = new GroupLayout(this.panel_1);
		this.panel_1.setLayout(glayout);
		// Turn on automatically adding gaps between components
		glayout.setAutoCreateGaps(true);
		// Create a sequential group for the horizontal axis.
		GroupLayout.SequentialGroup hGroup = glayout.createSequentialGroup();
		GroupLayout.Group yLabelGroup = glayout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		hGroup.addGroup(yLabelGroup);
		GroupLayout.Group yFieldGroup = glayout.createParallelGroup();
		hGroup.addGroup(yFieldGroup);
		glayout.setHorizontalGroup(hGroup);
		// Create a sequential group for the vertical axis.
		GroupLayout.SequentialGroup vGroup = glayout.createSequentialGroup();
		glayout.setVerticalGroup(vGroup);

		int p = GroupLayout.PREFERRED_SIZE;
		// add the components to the groups
		JLabel lblDatabase = new JLabel("Database: ");
		JLabel lblLearningTestTable = new JLabel("Learning set table: ");
		JLabel lblTestSetTable = new JLabel("Test set table: ");
		lblDatabase.setFont(textFontBold);
		lblLearningTestTable.setFont(textFontBold);
		lblTestSetTable.setFont(textFontBold);
		yLabelGroup.addComponent(lblDatabase);
		yLabelGroup.addComponent(lblLearningTestTable);
		yLabelGroup.addComponent(lblTestSetTable);

		txtDatabase = new JTextField("testDB");
		txtDatabase.setText("subtables");
		txtLearn = new JTextField("test");
		txtLearn.setText("measurements");
		txtTest = new JTextField("test");
		txtTest.setText("analog_values");
		txtDatabase.setFont(textFont);
		txtLearn.setFont(textFont);
		txtTest.setFont(textFont);
		yFieldGroup.addComponent(txtDatabase, p, p, 100);
		yFieldGroup.addComponent(txtLearn, p, p, 100);
		yFieldGroup.addComponent(txtTest, p, p, 100);

		vGroup.addGroup(glayout.createParallelGroup().addComponent(lblDatabase).addComponent(txtDatabase, p, p, p));
		vGroup.addGroup(
				glayout.createParallelGroup().addComponent(lblLearningTestTable).addComponent(txtLearn, p, p, p));
		vGroup.addGroup(glayout.createParallelGroup().addComponent(lblTestSetTable).addComponent(txtTest, p, p, p));

		// Add a second panel to store the button on it
		gblayoutCons.gridy = 2;
		sql.add(panel_2, gblayoutCons);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		panel_2.setLayout(gbl_panel_2);
		btnLoad.setFont(buttonFont);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// The values are taken from the database
				if (buildDatabase(txtDatabase.getText(), txtLearn.getText(), txtTest.getText())) {
					cl.show(panel, "2");
				}
			}
		});

		GridBagConstraints gbc_btnLoad = new GridBagConstraints();
		gbc_btnLoad.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoad.gridx = 9;
		gbc_btnLoad.gridy = 1;
		panel_2.add(btnLoad, gbc_btnLoad);

		return sql;
	}

	// This method creates the second card of the CardLayout, called Main
	private JPanel createMainMenu() {
		JPanel main = new JPanel(new GridBagLayout());

		GridBagConstraints gblayoutCons = new GridBagConstraints();
		gblayoutCons.fill = GridBagConstraints.BOTH;

		// Add objects to the card
		JLabel lblKMeans = new JLabel("KNN");
		JLabel lblKnn = new JLabel("K means");
		lblKMeans.setFont(titleFont);
		lblKnn.setFont(titleFont);
		lblKnn.setHorizontalAlignment(SwingConstants.CENTER);
		lblKnn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, darkBlue));
		lblKMeans.setHorizontalAlignment(SwingConstants.CENTER);
		lblKMeans.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, darkBlue));

		gblayoutCons.gridx = 1;
		gblayoutCons.gridy = 0;
		gblayoutCons.weighty = 0.75;
		main.add(lblKnn, gblayoutCons);
		gblayoutCons.gridy = 4;
		main.add(lblKMeans, gblayoutCons);

		gblayoutCons.weighty = 0;
		gblayoutCons.insets = new Insets(0, 0, 0, 0);
		gblayoutCons.fill = GridBagConstraints.BOTH;
		gblayoutCons.gridy = 2;
		gblayoutCons.weighty = 0.75;
		main.add(panel_5, gblayoutCons);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));

		JLabel lblNumberOfNeighbors = new JLabel("Number of clusters");
		lblNumberOfNeighbors.setFont(textFont);
		panel_5.add(lblNumberOfNeighbors);

		JTextField txtClusters = new JTextField();
		txtClusters.setHorizontalAlignment(SwingConstants.CENTER);
		txtClusters.setEditable(false);
		txtClusters.setColumns(10);
		txtClusters.setText("4");
		txtClusters.setFont(textFont);
		panel_5.add(txtClusters);

		gblayoutCons.gridy = 6;
		gblayoutCons.weighty = 0.75;
		main.add(panel_6, gblayoutCons);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));

		JLabel lblNumberOfClusters = new JLabel("Number of neighbors");
		lblNumberOfClusters.setFont(textFont);
		panel_6.add(lblNumberOfClusters);

		JTextField txtNeighbor = new JTextField();
		txtNeighbor.setHorizontalAlignment(SwingConstants.CENTER);
		txtNeighbor.setText("4");
		txtNeighbor.setColumns(10);
		txtNeighbor.setFont(textFont);
		panel_6.add(txtNeighbor);

		// Title of panel 3
		gblayoutCons.gridy = 8;
		gblayoutCons.weighty = 0.5;
		gblayoutCons.insets = new Insets(20, 0, 0, 0);
		lblOptions.setFont(titleFont);
		lblOptions.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, darkBlue));
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(lblOptions, gblayoutCons);

		// Panel 3, contains the representation options
		gblayoutCons.gridy = 9;
		gblayoutCons.weighty = 0.5;
		gblayoutCons.insets = new Insets(20, 0, 0, 0);
		main.add(panel_3, gblayoutCons);
		panel_3.setLayout(new GridLayout(3, 2, 0, 0));
		// Add radio buttons
		plotShow.setSelected(true);
		normalOp.setSelected(true);
		plotShow.setFont(textFont);
		tableShow.setFont(textFont);
		customOp.setFont(textFont);
		normalOp.setFont(textFont);
		buttonGroup.add(plotShow);
		buttonGroup.add(tableShow);
		buttonGroup2.add(normalOp);
		buttonGroup2.add(customOp);
		panel_3.add(plotShow);
		panel_3.add(tableShow);
		panel_3.add(normalOp);
		panel_3.add(customOp);

		// Learn button: executes kMeans algorithm
		JButton btnLearn = new JButton("Learn");
		gblayoutCons.gridy = 3;
		gblayoutCons.weighty = 0.5;
		gblayoutCons.insets = new Insets(0, 0, 30, 0);
		gblayoutCons.fill = GridBagConstraints.NONE;
		main.add(btnLearn, gblayoutCons);
		btnLearn.setFont(buttonFont);
		btnLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean system;
				boolean label;
				// Check the radio buttons state
				if (plotShow.isSelected()) {
					system = true;
				} else {
					system = false;
				}

				if (normalOp.isSelected()) {
					label = true;
				} else {
					label = false;
				}

				call2Kmeans(Integer.parseInt(txtClusters.getText()), system, label);
			}
		});

		// Test button: executes kNN algorithm
		gblayoutCons.gridy = 7;
		gblayoutCons.insets = new Insets(0, 0, 0, 0);
		gblayoutCons.weighty = 0.75;
		main.add(btnTest, gblayoutCons);
		btnTest.setEnabled(false);
		btnTest.setFont(buttonFont);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean system;
				// Check the radio button state
				if (plotShow.isSelected()) {
					system = true;
				} else {
					system = false;
				}

				call2KNN(Integer.parseInt(txtNeighbor.getText()), clusters, classifiedPoints, system);
			}
		});

		// Back button
		gblayoutCons.gridy = 11;
		gblayoutCons.insets = new Insets(0, 0, 30, 0);
		gblayoutCons.weighty = 0.75;
		gblayoutCons.anchor = GridBagConstraints.EAST;
		JButton btnBack = new JButton(new AbstractAction("\u22b2Back") {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.previous(panel);
			}
		});
		btnBack.setFont(buttonFont);
		main.add(btnBack, gblayoutCons);

		/*
		 * // Division between the two menus JPanel line = new JPanel();
		 * line.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
		 * null)); line.setBackground(lightBlue); gblayoutCons.insets = new
		 * Insets(0, 20, 0, 20); gblayoutCons.anchor =
		 * GridBagConstraints.PAGE_START; gblayoutCons.gridheight = 5;
		 * gblayoutCons.gridx = 1; gblayoutCons.gridy = 0; gblayoutCons.fill =
		 * GridBagConstraints.VERTICAL; main.add(line, gblayoutCons);
		 */

		return main;
	}

	private boolean buildDatabase(String database, String learning, String test) {
		this.sqldb = new SQLdatabase(database, learning, test);
		this.learnSet = new ArrayList<Measurement>(this.sqldb.getMeas());
		this.testSet = new ArrayList<Measurement>(this.sqldb.getMeasTest());
		if (this.learnSet.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	// This method calls to the kMeans algorithm and invokes the display
	private void call2Kmeans(int clusters, boolean system, boolean label) {
		KMeans kmeans = new KMeans(SQLdatabase.splitByTime(this.learnSet), label);
		this.clusters = kmeans.getClusters();
		this.classifiedPoints = kmeans.getClassifiedPoints();

		displayResults(system, this.clusters, this.classifiedPoints, "Results of the kMeans", "kMeans");

		// Enable kNN button
		this.btnTest.setEnabled(true);
	}

	private void call2KNN(int neighbors, List<Cluster> clusters, List<Point> classifiedPoints, boolean system) {
		KNN knn = new KNN(SQLdatabase.splitByTime(testSet), classifiedPoints, neighbors);
		List<Point> results = knn.getResults();

		displayResults(system, this.clusters, results, "Results of the kNN", "kNN");
	}

	// This method display the results from kMeans and kNN classification in
	// either a table or a 2D plot.
	private void displayResults(boolean system, List<Cluster> clusterList, List<Point> classifiedPoints, String title,
			String subtitle) {
		if (system) {
			if (clusterList.get(0).getPoints().get(0).getAngle().isEmpty()
					|| clusterList.get(0).getPoints().get(0).getVoltage().isEmpty()) {
				JOptionPane.showMessageDialog(null, "One dimensional data cannot be represented in a 2D plot");
				this.tableShow.setSelected(true);
			} else {
				// Two dimension representation in a plot
				Chart chart = new Chart(clusterList, classifiedPoints, title, subtitle);

				chart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
			}
		} else {
			// Only one dimension (either angle or voltage). Table
			// representation
			ResultsTable results = new ResultsTable(clusterList, classifiedPoints, title);
			results.pack();
			RefineryUtilities.centerFrameOnScreen(results);
			results.setVisible(true);

		}
	}
}