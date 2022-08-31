package co.pentonix.assignment.repository;

import co.pentonix.assignment.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Department,Integer> {
}
