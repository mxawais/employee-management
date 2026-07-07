package com.mxawais.employee_management.controller;

import com.mxawais.employee_management.dto.EmployeeDTO;
import com.mxawais.employee_management.exception.EmployeeNotFoundException;
import com.mxawais.employee_management.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ======================================
    // GET ALL EMPLOYEES
    // ======================================
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {

        return employeeService.getAllEmployees();

    }

    // ======================================
    // GET EMPLOYEE BY ID
    // ======================================
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {

        return employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with ID: " + id));

    }

    // ======================================
    // CREATE EMPLOYEE
    // ======================================
    @PostMapping
    public EmployeeDTO createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.saveEmployee(employeeDTO);

    }

    // ======================================
    // UPDATE EMPLOYEE
    // ======================================
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with ID: " + id));

        employeeDTO.setId(id);

        return employeeService.updateEmployee(employeeDTO);

    }

    // ======================================
    // DELETE EMPLOYEE
    // ======================================
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with ID: " + id));

        employeeService.deleteEmployee(id);

        return "Employee deleted successfully.";

    }

}
