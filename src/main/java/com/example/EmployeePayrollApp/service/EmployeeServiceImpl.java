package com.example.EmployeePayrollApp.service;

import com.example.EmployeePayrollApp.model.Employee;
import com.example.EmployeePayrollApp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j  // ✅ Lombok annotation for logging
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        log.info("Adding new employee: Name={}, Email={}", employee.getName(), employee.getEmail());
        Employee savedEmployee = employeeRepository.save(employee);
        log.debug("Employee added successfully: {}", savedEmployee);
        return savedEmployee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        log.debug("Total employees found: {}", employees.size());
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee with ID {} not found!", id);
                    return new RuntimeException("Employee with ID " + id + " not found!");
                });
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        log.info("Updating employee with ID: {}", id);
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    log.debug("Existing Employee: {}", existingEmployee);
                    existingEmployee.setName(employee.getName());
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setSalary(employee.getSalary());
                    Employee updatedEmployee = employeeRepository.save(existingEmployee);
                    log.info("Employee with ID {} updated successfully", id);
                    log.debug("Updated Employee: {}", updatedEmployee);
                    return updatedEmployee;
                })
                .orElseThrow(() -> {
                    log.error("Failed to update: Employee with ID {} not found!", id);
                    return new RuntimeException("Employee with ID " + id + " not found!");
                });
    }

    @Override
    public void deleteEmployee(Long id) {
        log.warn("Attempting to delete employee with ID: {}", id);
        employeeRepository.findById(id).ifPresentOrElse(
                employee -> {
                    log.debug("Deleting Employee: {}", employee);
                    employeeRepository.delete(employee);
                    log.info("Employee with ID {} deleted successfully", id);
                },
                () -> log.error("Failed to delete: Employee with ID {} not found!", id)
        );
    }
}
