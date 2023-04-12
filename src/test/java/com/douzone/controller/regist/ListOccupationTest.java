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
public class ListOccupationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListOccupationNormal() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("earner_type", "일반");
        params.put("search_value", "소프트");
      
        mockMvc.perform(post("/regist/list_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.occupation_list").exists());
    }

    @Test
    public void testListOccupationMissingParameter() throws Exception {
        Map<String, String> params = new HashMap<>();
       
        mockMvc.perform(post("/regist/list_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListOccupationInvalidEarnerType() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("earner_type", "invalid");
        params.put("search_value", "소프트");
       
        mockMvc.perform(post("/regist/list_occupation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}