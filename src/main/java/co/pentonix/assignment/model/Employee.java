package co.pentonix.assignment.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMP")
public class Employee {
    @Id
    @Column
    private int ENO;
    @Column
    private String ENAME;
    @Column
    private int DNO;
    @Column
    private int Salary;

    public int getENO() {
        return ENO;
    }

    public void setENO(int ENO) {
        this.ENO = ENO;
    }

    public String getENAME() {
        return ENAME;
    }

    public void setENAME(String ENAME) {
        this.ENAME = ENAME;
    }

    public int getDNO() {
        return DNO;
    }

    public void setDNO(int DNO) {
        this.DNO = DNO;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        this.Salary = salary;
    }
}
