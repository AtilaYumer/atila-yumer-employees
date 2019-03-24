package sirma.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
	
	private int emplId;
	private int projectId;
	private Date dateFrom;
	private Date dateTo;

	public Employee() {
	}

	public Employee(int emplId, int projectId, Date dateFrom, Date dateTo) throws ParseException {
		this.setEmplId(emplId);
		this.setProjectId(projectId);
		this.setDateFrom(dateFrom);
		this.setDateTo(dateTo);
	}

	public int getEmplId() {
		return emplId;
	}

	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("| ")
		.append(this.getEmplId())
		.append(" | ")
		.append(this.getProjectId())
		.append(" | ")
		.append(SDF.format(this.getDateFrom()))
		.append(" | ").
		append(SDF.format(this.getDateTo()))
		.append(" |");

		return sb.toString();
	}

}
