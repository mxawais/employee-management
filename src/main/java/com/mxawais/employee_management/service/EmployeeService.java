package com.mxawais.employee_management.service;

import com.mxawais.employee_management.dto.EmployeeDTO;
import com.mxawais.employee_management.entity.Employee;
import com.mxawais.employee_management.repository.EmployeeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ===============================
    // ENTITY -> DTO
    // ===============================
    private EmployeeDTO toDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());

        return dto;
    }

    // ===============================
    // DTO -> ENTITY
    // ===============================
    private Employee toEntity(EmployeeDTO dto) {

        Employee employee = new Employee();

        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());

        return employee;
    }

    // ===============================
    // GET ALL
    // ===============================
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===============================
    // GET BY ID
    // ===============================
    public Optional<EmployeeDTO> getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .map(this::toDTO);
    }

    // ===============================
    // SAVE
    // ===============================
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        Employee employee = toEntity(dto);

        Employee saved = employeeRepository.save(employee);

        return toDTO(saved);
    }

    // ===============================
    // UPDATE
    // ===============================
    public EmployeeDTO updateEmployee(EmployeeDTO dto) {

        Employee employee = toEntity(dto);

        Employee updated = employeeRepository.save(employee);

        return toDTO(updated);
    }

    // ===============================
    // DELETE
    // ===============================
    public void deleteEmployee(Long id) {

        employeeRepository.deleteById(id);

    }

}
