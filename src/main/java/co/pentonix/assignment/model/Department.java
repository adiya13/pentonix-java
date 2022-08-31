package co.pentonix.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPT")
public class Department {
    @Id
    @Column
    private int DNO;
    @Column
    private String DNAME;

    public int getDNO() {
        return DNO;
    }

    public void setDNO(int DNO) {
        this.DNO = DNO;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }
}
