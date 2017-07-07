import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ResultsTable extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JTable table = new JTable();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private String[] columnIdentifiers;
	private final Font tableTitleFont = new Font("SansSerif", Font.PLAIN, 20);
	private final Font tableFont = new Font("SansSerif", Font.PLAIN, 15);
	private List<Cluster> clusterList;
	private List<Point> classifiedPoints;
	private boolean dimensions;

	/**
	 * Create the dialog.
	 */

	public ResultsTable(List<Cluster> clusterList, List<Point> classifiedPoints, String title) {
		// Get the classification results
		this.clusterList = clusterList;
		this.classifiedPoints = classifiedPoints;
		this.setTitle(title);
		checkDimensions();
		setInterface();
	}

	// This method sets the interface of the results table
	private void setInterface() {
		setBounds(100, 100, 894, 539);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JLabel lblSelectHereThe = new JLabel("Select here the cluster");
			lblSelectHereThe.setFont(new Font("SansSerif", Font.PLAIN, 18));
			contentPanel.add(lblSelectHereThe);
		}
		{
			JComboBox comboBox = new JComboBox();
			// Update the table when we select another cluster
			comboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED) {
						tableModel.setDataVector(fillTableData(comboBox.getSelectedIndex()), columnIdentifiers);
						adjustTableLayout();
					}
				}
			});
			comboBox.setModel(new DefaultComboBoxModel(getClusterLabels()));
			comboBox.setMaximumRowCount(10);
			contentPanel.add(comboBox);
		}
		{
			// Add an scroll panel to allow scrolling on the table data
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportBorder(null);

			getContentPane().add(scrollPane);
			{
				// Create the results table and fill it with the classified
				// points
				tableModel.setDataVector(fillTableData(0), columnIdentifiers);
				table.setModel(tableModel);
				scrollPane.setViewportView(table);
				table.setRowHeight(table.getRowHeight() + tableFont.getSize());
				adjustTableLayout();

			}
		}
	}

	// This method get the labels of the clusters to write them on the combo box
	private String[] getClusterLabels() {
		String[] labels = new String[this.clusterList.size()];
		for (int i = 0; i < this.clusterList.size(); i++) {
			labels[i] = this.clusterList.get(i).getLabel();
		}

		return labels;
	}

	// This method is used to fill the table with the data coming from the
	// KMeans classification. It cast some of the fields of the points stored in
	// an ArrayList<Point> to an Object[][] array, which is the input format
	// of a table
	private Object[][] fillTableData(int index) {
		// Add all the classified points to the table
		ArrayList<Point> finalList = new ArrayList<Point>();

		for (Point point : this.classifiedPoints) {
			if (point.getClusterNumber() == index) {
				finalList.add(point);
			}
		}

		// Define the size of the Object array
		Object[][] data = new Object[finalList.size()][this.columnIdentifiers.length];

		if (dimensions) {
			// For one dimension points
			for (int i = 0; i < finalList.size(); i++) {
				Point point = finalList.get(i);
				for (int j = 0; j < point.getMeasList().size(); j++) {
					// Fill the table with the measurement fields
					Measurement meas = point.getMeasList().get(j);
					data[i + j][0] = meas.getRdfId();
					data[i + j][1] = meas.getName();
					data[i + j][2] = meas.getTime();
					data[i + j][3] = meas.getValue();
					data[i + j][4] = meas.getSubRdf();
				}
			}
		} else {
			// For N dimension points
			for (int i = 0; i < finalList.size(); i++) {
				Point point = finalList.get(i);

				data[i][0] = point.getTime();

				for (int j = 0; j < point.getAngle().size(); j++) {
					data[i][j + 1] = point.getAngle().get(j);
				}
				for (int j = 0; j < point.getVoltage().size(); j++) {
					data[i][j + 1 + point.getAngle().size()] = point.getVoltage().get(j);
				}
			}
		}

		return data;

	}

	// Method to change the table width so it adapts to the content
	// Obtained from here:
	// https://tips4java.wordpress.com/2008/11/10/table-column-adjuster/
	private void adjustTableWidth(JTable table) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				// We've exceeded the maximum width, no need to check other rows

				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			// Set the cell width and add a small gap (Font size)
			tableColumn.setPreferredWidth(preferredWidth + tableFont.getSize());
		}
	}

	private void adjustTableLayout() {
		// Set the fonts for the table
		table.setFont(tableFont);
		table.getTableHeader().setFont(tableTitleFont);
		// Adjust table size and viewport size
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		adjustTableWidth(table);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
	}

	// This method check the number of dimensions in the database to plot the
	// results accordingly
	private void checkDimensions() {
		Point point = this.classifiedPoints.get(0);
		if (point.getAngle().isEmpty() || point.getVoltage().isEmpty()) {
			// We have a one dimensional system
			columnIdentifiers = new String[] { "rdfid", "type", "time", "value", "sub_rdfid" };
			dimensions = true;
		} else {
			// There are Nx2 dimensions
			columnIdentifiers = new String[point.getAngle().size() * 2 + 1];
			columnIdentifiers[0] = "time";
			for (int i = 0; i < point.getAngle().size(); i++) {
				columnIdentifiers[i+1] = "ang_" + i;
			}
			for (int i = 0; i < point.getVoltage().size(); i++) {
				columnIdentifiers[i + 1 + point.getAngle().size()] = "volt" + i;
			}
			dimensions = false;
		}
	}
}
