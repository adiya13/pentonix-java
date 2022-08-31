package co.pentonix.assignment.repository;

import co.pentonix.assignment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Employee,Integer> {
}
