class Employee{
    public String fullName;
    public float salary;
}

public class Main {

    public static void main(String[] args) {
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();

        emp1.fullName = "Vasya Mar";
        emp1.salary = (float) 100;
        emp2.fullName = "Serg Killer";
        emp2.salary = (float) 200;

        Employee[] empls = {emp1, emp2};

        String employeesInfo = "[";
        for(Employee emp : empls){
            String sal = Float.toString(emp.salary);
            sal = sal.replace(',','.');
            employeesInfo += String.format("{fullName: \"%s\", salary: %s}, ", emp.fullName, sal);
        }
        employeesInfo = employeesInfo.substring(0, employeesInfo.length() - 2);
        employeesInfo += "]";
        System.out.println(employeesInfo);
    }
}
