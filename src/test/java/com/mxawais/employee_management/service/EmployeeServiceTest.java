package com.mxawais.employee_management.service;

import com.mxawais.employee_management.dto.EmployeeDTO;
import com.mxawais.employee_management.entity.Employee;
import com.mxawais.employee_management.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {

        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setFirstName("Muhammad");
        emp1.setLastName("Awais");
        emp1.setEmail("awais@example.com");
        emp1.setDepartment("DevOps");
        emp1.setSalary(70000);

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setFirstName("Ali");
        emp2.setLastName("Khan");
        emp2.setEmail("ali@example.com");
        emp2.setDepartment("IT");
        emp2.setSalary(55000);

        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(emp1, emp2));

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());

        assertEquals("Muhammad", result.get(0).getFirstName());
        assertEquals("Ali", result.get(1).getFirstName());

        verify(employeeRepository, times(1)).findAll();
    }

}
