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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class GetEarnerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEarnerNormal() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000001");
        log.info("start Normal");
        mockMvc.perform(post("/regist/get_earner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.earner_info").exists());
    }

    @Test
    public void testGetEarnerNotExist() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "123123");
        log.info("start NotExist");
        mockMvc.perform(post("/regist/get_earner")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isNotFound());
           
    }

    @Test
    public void testGetEarnerMissingParameter() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        log.info("start MissingParam");
        mockMvc.perform(post("/regist/get_earner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEarnerRangeOverflow() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", "000000000000000222");
        log.info("start overflow");
        mockMvc.perform(post("/regist/get_earner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEarnerWrongType() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("earner_code", -1234);
        log.info("start WrongType");
        mockMvc.perform(post("/regist/get_earner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}