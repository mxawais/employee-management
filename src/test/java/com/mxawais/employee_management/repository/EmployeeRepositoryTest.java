package com.mxawais.employee_management.repository;

import com.mxawais.employee_management.entity.Employee;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveEmployee() {

        Employee employee = new Employee();

        employee.setFirstName("Muhammad");
        employee.setLastName("Awais");
        employee.setEmail("awais@example.com");
        employee.setDepartment("DevOps");
        employee.setSalary(70000);

        Employee saved = employeeRepository.save(employee);

        assertNotNull(saved.getId());
        assertEquals("Muhammad", saved.getFirstName());
    }

    @Test
    void testFindAllEmployees() {

        Employee employee = new Employee();

        employee.setFirstName("Ali");
        employee.setLastName("Khan");
        employee.setEmail("ali@example.com");
        employee.setDepartment("IT");
        employee.setSalary(50000);

        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findAll();

        assertFalse(employees.isEmpty());
    }

    @Test
    void testDeleteEmployee() {

        Employee employee = new Employee();

        employee.setFirstName("Sara");
        employee.setLastName("Ahmed");
        employee.setEmail("sara@example.com");
        employee.setDepartment("HR");
        employee.setSalary(60000);

        Employee saved = employeeRepository.save(employee);

        employeeRepository.delete(saved);

        assertFalse(employeeRepository.findById(saved.getId()).isPresent());
    }

}
