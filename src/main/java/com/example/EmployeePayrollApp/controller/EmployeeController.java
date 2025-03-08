package com.example.EmployeePayrollApp.controller;

import com.example.EmployeePayrollApp.dto.EmployeePayrollIDTO;
import com.example.EmployeePayrollApp.model.Employee;
import com.example.EmployeePayrollApp.services.EmployeeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j

@RestController
@RequestMapping("/employeepayrollservice")
public class EmployeeController {

    @Autowired
    EmployeeServiceInterface employeeServiceInterface;

    @GetMapping("/")

    public List<Employee> getAll() {
        log.info("Getting all the employees");
        return employeeServiceInterface.getAllEmployees();
    }

    @GetMapping("/get/{id}")
    public Employee getById(@PathVariable Long id) {
        log.info("Getting the employees by id");
        return employeeServiceInterface.getEmployeeById(id);
    }

    @PostMapping("/create")
    public Employee add(@RequestBody EmployeePayrollIDTO emp) {
        log.info("Creating a new Emmployee");
        return employeeServiceInterface.addEmployee(emp);
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable Long id, @RequestBody EmployeePayrollIDTO emp) {
        log.info("Update an employees");
        return employeeServiceInterface.updateEmployee(id, emp);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete an employees");
        employeeServiceInterface.deleteEmployee(id);

    }
}
