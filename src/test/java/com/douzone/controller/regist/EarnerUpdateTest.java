package com.douzone.controller.regist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class EarnerUpdateTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEarnerUpdateNormal() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("param_name", "tel3");
        params.put("param_value", "2028");
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
       
        mockMvc.perform(patch("/regist/earner_update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk());
    }
    @Test
    public void testEarnerUpdateMissingParameter() throws Exception {
        Map<String, Object> params = new HashMap<>();
        // param_name
        params.put("param_value", "Updated Earner");
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
        
        mockMvc.perform(patch("/regist/earner_update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEarnerUpdateInvalidParam_name() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("param_name", "tel5");
        params.put("param_value", "1234");
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
       
        mockMvc.perform(patch("/regist/earner_update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testEarnerUpdateInvalidEmail() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("param_name", "email2");
        params.put("param_value", "가나다");
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
       
        mockMvc.perform(patch("/regist/earner_update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}