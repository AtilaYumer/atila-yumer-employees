package sirma.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import sirma.model.Employee;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static DateFormat format = new SimpleDateFormat(DATE_FORMAT);
	private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
	
	private File file;
	private List<Employee> employees = new ArrayList<Employee>();
	private Scanner fileReader;

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() {
		setSize(400, 400);
		JButton btnOpen = new JButton("Search");
		add(btnOpen, BorderLayout.NORTH);

		String[] headers = { "EmpID", "ProjectID", "DateFrom", "DateTo" };
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(headers);
		JTable table = new JTable();
		table.setModel(tableModel);
		JScrollPane sp = new JScrollPane(table);
		add(sp);

		btnOpen.addActionListener(e -> {
			try {
				selectFile(tableModel);
			} catch (FileNotFoundException | ParseException e1) {
				e1.printStackTrace();
			}
		});

		setVisible(true);
	}

	private void selectFile(DefaultTableModel model) throws FileNotFoundException, ParseException {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			fileReader = new Scanner(file);
			employees.removeAll(employees);
			while (fileReader.hasNextLine()) {
				String[] line = fileReader.nextLine().split(", ");
				int employeeId = Integer.parseInt(line[0]);
				int pojectId = Integer.parseInt(line[1]);
				Date dateFrom = format.parse(line[2]);
				Date dateTo;
				if (line[3].equals("NULL")) {
					dateTo = null;
				} else {
					dateTo = format.parse(line[3]);
				}

				Employee employee = new Employee(employeeId, pojectId, dateFrom, dateTo);
				employees.add(employee);
			}
			fileReader.close();
			if (model.getRowCount() > 0) {
			    for (int i = model.getRowCount() - 1; i > -1; i--) {
			        model.removeRow(i);
			    }
			}
			
			List<Employee> bestEmployees = findEmployees(employees);
			String[][] data = new String[2][4];
			if (bestEmployees.size() > 0) {
				for (int i = 0; i < data.length; i++) {
					data[i][0] = String.valueOf(bestEmployees.get(i).getEmplId());
					data[i][1] = String.valueOf(bestEmployees.get(i).getProjectId());
					data[i][2] = SDF.format(bestEmployees.get(i).getDateFrom());
					data[i][3] = SDF.format(bestEmployees.get(i).getDateTo());
					model.addRow(data[i]);
				}
			}
			for (Employee employee : bestEmployees) {
				System.out.println(employee);
			}
		} else {

		}
	}

	private List<Employee> findEmployees(List<Employee> employees2) throws ParseException {
		long bestTime = 0;
		List<Employee> bestEmployees = new ArrayList<Employee>();

		for (int i = 0; i < employees.size(); i++) {
			Employee employee = employees.get(i);
			for (int j = i + 1; j < employees.size(); j++) {
				Employee employee2 = employees.get(j);
				if (employee.getProjectId() == employee2.getProjectId()) {
					if (isEmployeesWorkTogether(employee, employee2)) {
						Date startDate;
						Date endDate;
						if (employee.getDateFrom().compareTo(employee2.getDateFrom()) < 0) {
							startDate = employee2.getDateFrom();
						} else {
							startDate = employee.getDateFrom();
						}
						if (employee.getDateTo().compareTo(employee2.getDateTo()) < 0) {
							endDate = employee.getDateTo();
						} else {
							endDate = employee2.getDateTo();
						}

						if (Math.abs(endDate.getTime() - startDate.getTime()) > bestTime) {
							bestTime = Math.abs(endDate.getTime() - startDate.getTime());
							bestEmployees.removeAll(bestEmployees);
							bestEmployees.add(employee);
							bestEmployees.add(employee2);
						}
					}
				}
			}
		}
		return bestEmployees;
	}

	private boolean isEmployeesWorkTogether(Employee employee, Employee employee2) throws ParseException {
		if (employee.getDateTo() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String now = sdf.format(new Date());
			employee.setDateTo(sdf.parse(now));
		}
		if (employee2.getDateTo() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String now = sdf.format(new Date());
			employee2.setDateTo(sdf.parse(now));
		}
		if (employee.getDateFrom().before(employee2.getDateTo())
				&& employee2.getDateFrom().before(employee.getDateTo())) {
			return true;
		}
		return false;
	}

}
