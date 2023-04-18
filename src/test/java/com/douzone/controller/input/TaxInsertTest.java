package com.douzone.controller.input;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.douzone.entity.input.TaxInsertVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxInsertTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTaxInsertNormal() throws Exception {
        TaxInsertVO taxInsertVO = TaxInsertVO.builder()
                .earner_code("155230")
                .worker_id("yuchan2")
                .payment_ym(202209)
                .build();

        mockMvc.perform(post("/input/tax_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxInsertVO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tax_id").exists());
    }

    @Test
    public void testTaxInsertMissingEarnerCode() throws Exception {
        TaxInsertVO taxInsertVO = TaxInsertVO.builder()
                .worker_id("yuchan2")
                .payment_ym(202201)
                .build();

        mockMvc.perform(post("/input/tax_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxInsertVO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTaxInsertMissingWorkerId() throws Exception {
        TaxInsertVO taxInsertVO = TaxInsertVO.builder()
                .earner_code("123456")
                .payment_ym(202201)
                .build();

        mockMvc.perform(post("/input/tax_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxInsertVO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTaxInsertInvalidPaymentYm() throws Exception {
        TaxInsertVO taxInsertVO = TaxInsertVO.builder()
                .earner_code("123456")
                .worker_id("yuchan2")
                .payment_ym(199999)
                .build();

        mockMvc.perform(post("/input/tax_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxInsertVO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTaxInsertOutOfRangePaymentYm() throws Exception {
        TaxInsertVO taxInsertVO = TaxInsertVO.builder()
                .earner_code("123456")
                .worker_id("yuchan2")
                .payment_ym(300000)
                .build();

        mockMvc.perform(post("/input/tax_insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taxInsertVO)))
                .andExpect(status().isBadRequest());
    }
}
