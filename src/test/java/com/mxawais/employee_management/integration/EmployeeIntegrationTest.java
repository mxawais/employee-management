package com.mxawais.employee_management.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxawais.employee_management.dto.EmployeeDTO;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetEmployee() throws Exception {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setFirstName("Integration");
        dto.setLastName("Test");
        dto.setEmail("integration@test.com");
        dto.setDepartment("QA");
        dto.setSalary(60000);

        String response = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Integration"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmployeeDTO created =
                objectMapper.readValue(response, EmployeeDTO.class);

        mockMvc.perform(get("/employees/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("integration@test.com"));
    }

}
