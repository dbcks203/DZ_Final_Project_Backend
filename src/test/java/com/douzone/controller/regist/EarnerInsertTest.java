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
public class EarnerInsertTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEarnerInsertCustom() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000135");
        params.put("earner_name", "마성일");
        params.put("div_code", "940903");
        params.put("div_name", "학원강사");
        params.put("div_type", "일반");
        params.put("personal_no", "1234567890");
        params.put("is_native", "내");
        params.put("is_default", 0);
     
        mockMvc.perform(post("/regist/earner_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code_count").exists());
    }
    
    @Test
    public void testEarnerInsertDefault() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "155229");
        params.put("earner_name", "마성일");
        params.put("div_code", "940903");
        params.put("div_name", "학원강사");
        params.put("div_type", "일반");
        params.put("personal_no", "1234567890");
        params.put("is_native", "내");
        params.put("is_default", 1);
      
        mockMvc.perform(post("/regist/earner_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code_count").exists());
    }
    
    

    @Test
    public void testEarnerInsertMissingParameter() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan12");
        params.put("earner_code", "123456");
        // earner_name
        params.put("div_code", "940903");
        params.put("div_name", "학원강사");
        params.put("div_type", "일반");
        params.put("personal_no", "1234567890");
        params.put("is_native", "내");
        params.put("is_default", 1);
        
        mockMvc.perform(post("/regist/earner_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
    
    
    @Test
    public void testEarnerInsertIsExist() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
        params.put("earner_name", "마성일");
        params.put("div_code", "940903");
        params.put("div_name", "학원강사");
        params.put("div_type", "일반");
        params.put("personal_no", "1234567890");
        params.put("is_native", "내");
        params.put("is_default", 1);
       
        mockMvc.perform(post("/regist/earner_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isInternalServerError());
    }

    
    @Test
    public void testEarnerInsertInvalidEarnerCode() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan12");
        params.put("earner_code", "12345"); // Invalid earner_code
        params.put("earner_name", "마성일");
        params.put("div_code", "940903");
        params.put("div_name", "학원강사");
        params.put("div_type", "일반");
        params.put("personal_no", "1234567890");
        params.put("is_native", "내");
        params.put("is_default", 1);
        
        mockMvc.perform(post("/regist/earner_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}