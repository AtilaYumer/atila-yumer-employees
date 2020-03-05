package sirma.model;

import java.util.List;

public class LongestWorkedTogetherEmployees {

    private List<Employee> employees;
    private int days;

    public LongestWorkedTogetherEmployees() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
