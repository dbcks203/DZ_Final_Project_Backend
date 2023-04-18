package com.douzone.controller.input;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class UpdateTaxInfoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateTaxInfoNormal() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("total_payment", 1000000);
        params.put("tax_rate", 5.0f);
        params.put("tax_id", 264);

        mockMvc.perform(patch("/input/update_taxinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.earner_tax").exists());
    }

    @Test
    public void testUpdateTaxInfoMissingFields() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("total_payment", 10000);
        params.put("tax_rate", 5.0f);

        mockMvc.perform(patch("/input/update_taxinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTaxInfoInvalidValues() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("total_payment", -10000);
        params.put("tax_rate", 50.0f);
        params.put("tax_id", -1);

        mockMvc.perform(patch("/input/update_taxinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(params)))
                .andExpect(status().isBadRequest());
    }
}
