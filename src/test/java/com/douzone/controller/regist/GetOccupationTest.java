package com.douzone.controller.regist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
public class GetOccupationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetOccupationNormal() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("earner_type", "일반");
        params.put("occupation_code", "960");
        
        mockMvc.perform(post("/regist/get_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.occupation").exists());
    }

    @Test
    public void testGetOccupationInvalidEarnerType() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("earner_type", "오류");
        params.put("occupation_code", "960");
       
        mockMvc.perform(post("/regist/get_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOccupationInvalidOccupationCode() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("earner_type", "일반");
        params.put("occupation_code", "96");
       
        mockMvc.perform(post("/regist/get_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}
