package co.pentonix.assignment.controller;

import co.pentonix.assignment.model.Department;
import co.pentonix.assignment.model.Employee;
import co.pentonix.assignment.repository.DeptRepository;
import co.pentonix.assignment.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiController {
    @Autowired
    DeptRepository departmentRepository;

    @Autowired
    EmpRepository empRepository;

    @GetMapping(path = "/emp")
    public ResponseEntity<?> getData(@RequestParam String DNAME){
        List<Department> deptTbl = (List<Department>) departmentRepository.findAll();
        int dno = 0;
        for( Department department : deptTbl ){
            if( department.getDNAME().equals(DNAME) ){
                dno = department.getDNO();
            }
        }
        List<Employee> empTbl = (List<Employee>) empRepository.findAll();
        List<Employee> empList = new ArrayList<>();
        for( Employee employee : empTbl ){
            if( employee.getDNO() == dno ){
                empList.add(employee);
            }
        }
        return new ResponseEntity<List>(empList,HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<?> getEmpData(@RequestParam int eno){
        List<Employee> deptTbl = (List<Employee>) empRepository.findAll();
        Employee target = new Employee();
        for( Employee employee : deptTbl ){
            if( employee.getENO() == eno ) {
                target.setDNO(employee.getDNO());
                target.setENAME(employee.getENAME());
                target.setENO(employee.getENO());
                target.setSalary(employee.getSalary());
            }
        }
        return new ResponseEntity<Employee>(target, HttpStatus.OK);
    }
}
