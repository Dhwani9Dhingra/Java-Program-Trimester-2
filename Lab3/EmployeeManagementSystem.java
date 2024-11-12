import java.util.Scanner;

// Base class: Employee
class Employee {
    protected int employeeId;
    protected String employeeName;
    protected String designation;

    public Employee(int employeeId, String employeeName, String designation) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.designation = designation;
    }

    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + employeeName);
        System.out.println("Designation: " + designation);
    }

    public double calculateBonus() {
        return 0.0; // Base class has no bonus
    }
}

// Derived class: HourlyEmployee
class HourlyEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public HourlyEmployee(int employeeId, String employeeName, String designation, double hourlyRate, int hoursWorked) {
        super(employeeId, employeeName, designation);
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate must be non-negative.");
        }
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked must be non-negative.");
        }
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public double calculateWeeklySalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Hourly Rate: $" + hourlyRate);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Weekly Salary: $" + calculateWeeklySalary());
    }

    @Override
    public double calculateBonus() {
        return calculateWeeklySalary() * 0.05; // 5% bonus on weekly salary
    }
}

// Derived class: SalariedEmployee
class SalariedEmployee extends Employee {
    protected double monthlySalary;

    public SalariedEmployee(int employeeId, String employeeName, String designation, double monthlySalary) {
        super(employeeId, employeeName, designation);
        if (monthlySalary < 0) {
            throw new IllegalArgumentException("Monthly salary must be non-negative.");
        }
        this.monthlySalary = monthlySalary;
    }

    public double calculateWeeklySalary() {
        return monthlySalary / 4;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Monthly Salary: $" + monthlySalary);
        System.out.println("Weekly Salary: $" + calculateWeeklySalary());
    }

    @Override
    public double calculateBonus() {
        return calculateWeeklySalary() * 0.1; // 10% bonus on weekly salary
    }
}

// Derived class: ExecutiveEmployee
class ExecutiveEmployee extends SalariedEmployee {
    private double bonusPercentage;

    public ExecutiveEmployee(int employeeId, String employeeName, String designation, double monthlySalary, double bonusPercentage) {
        super(employeeId, employeeName, designation, monthlySalary);
        if (bonusPercentage < 0 || bonusPercentage > 100) {
            throw new IllegalArgumentException("Bonus percentage must be between 0 and 100.");
        }
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Bonus Percentage: " + bonusPercentage + "%");
    }

    @Override
    public double calculateBonus() {
        double annualSalary = monthlySalary * 12;
        return annualSalary * (bonusPercentage / 100); // bonus based on annual salary
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Employee Type (Hourly, Salaried, Executive): ");
        String employeeType = scanner.nextLine();

        System.out.println("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();  // consume newline

        System.out.println("Enter Employee Name: ");
        String employeeName = scanner.nextLine();

        System.out.println("Enter Designation: ");
        String designation = scanner.nextLine();

        Employee employee = null;

        switch (employeeType.toLowerCase()) {
            case "hourly":
                System.out.println("Enter Hourly Rate: ");
                double hourlyRate = scanner.nextDouble();

                System.out.println("Enter Hours Worked: ");
                int hoursWorked = scanner.nextInt();

                employee = new HourlyEmployee(employeeId, employeeName, designation, hourlyRate, hoursWorked);
                break;

            case "salaried":
                System.out.println("Enter Monthly Salary: ");
                double monthlySalary = scanner.nextDouble();

                employee = new SalariedEmployee(employeeId, employeeName, designation, monthlySalary);
                break;

            case "executive":
                System.out.println("Enter Monthly Salary: ");
                monthlySalary = scanner.nextDouble();

                System.out.println("Enter Bonus Percentage: ");
                double bonusPercentage = scanner.nextDouble();

                employee = new ExecutiveEmployee(employeeId, employeeName, designation, monthlySalary, bonusPercentage);
                break;

            default:
                System.out.println("Invalid employee type.");
                break;
        }

        if (employee != null) {
            employee.displayInfo();
            System.out.println("Bonus: $" + employee.calculateBonus());
        }

        scanner.close();
    }
}
