package com.douzone.controller.input;

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
public class EarnerSearchTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchEarnerNormal() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("search_value", "000");
        params.put("payment_ym", 202202);
        
        mockMvc.perform(post("/input/earner_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk());
    }
    @Test
    public void testSearchEarnerEmptySearchValue() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("search_value", "");
        params.put("payment_ym", 202202);
   
        mockMvc.perform(post("/input/earner_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
        		.andExpect(status().isOk());
    }

    @Test
    public void testSearchEarnerMissingWorkerId() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("search_value", "testValue");
        params.put("payment_ym", 202207);
       
        mockMvc.perform(post("/input/earner_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchEarnerMissingSearchValue() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "yuchan2");
        params.put("payment_ym", 202207);
       
        mockMvc.perform(post("/input/earner_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchEarnerEmptyWorkerId() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("worker_id", "");
        params.put("payment_ym", 202207);
        params.put("search_value", "testValue");
      
        mockMvc.perform(post("/input/earner_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}
