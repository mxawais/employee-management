package com.mxawais.employee_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxawais.employee_management.dto.EmployeeDTO;
import com.mxawais.employee_management.service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO getEmployee() {

        EmployeeDTO employee = new EmployeeDTO();

        employee.setId(1L);
        employee.setFirstName("Muhammad");
        employee.setLastName("Awais");
        employee.setEmail("awais@example.com");
        employee.setDepartment("DevOps");
        employee.setSalary(70000);

        return employee;
    }

    @Test
    void testGetAllEmployees() throws Exception {

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(getEmployee()));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Muhammad"))
                .andExpect(jsonPath("$[0].department").value("DevOps"));
    }

    @Test
    void testGetEmployeeById() throws Exception {

        when(employeeService.getEmployeeById(1L))
                .thenReturn(Optional.of(getEmployee()));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("awais@example.com"));
    }

    @Test
    void testCreateEmployee() throws Exception {

        EmployeeDTO employee = getEmployee();

        when(employeeService.saveEmployee(any(EmployeeDTO.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Muhammad"));
    }

    @Test
    void testUpdateEmployee() throws Exception {

        EmployeeDTO employee = getEmployee();

        employee.setDepartment("Cloud");

        // Controller first checks whether employee exists
        when(employeeService.getEmployeeById(1L))
                .thenReturn(Optional.of(getEmployee()));

        // Then performs update
        when(employeeService.updateEmployee(any(EmployeeDTO.class)))
                .thenReturn(employee);

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.department").value("Cloud"));
    }

    @Test
    void testDeleteEmployee() throws Exception {

        // Controller first checks whether employee exists
        when(employeeService.getEmployeeById(1L))
                .thenReturn(Optional.of(getEmployee()));

        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }

}
