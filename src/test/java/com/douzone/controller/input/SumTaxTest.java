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

import com.douzone.entity.input.SumTaxVO;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class SumTaxTest {
 @Autowired
 private MockMvc mockMvc;

 @Test
 public void testSumTaxNormal() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .earner_code("000001")
             .worker_id("yuchan2")
             .payment_ym(202209)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.sum_tax").exists());
 }

 @Test
 public void testSumTaxMissingEarnerCode() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .worker_id("yuchan2")
             .payment_ym(202201)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaxInvalidEarnerCode() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .earner_code("12345a")
             .worker_id("yuchan2")
             .payment_ym(202201)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaxMissingWorkerId() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .earner_code("123456")
             .payment_ym(202201)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaxInvalidPaymentYm() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .earner_code("123456")
             .worker_id("yuchan2")
             .payment_ym(199999)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaxOutOfRangePaymentYm() throws Exception {
     SumTaxVO sumTaxVO = SumTaxVO.builder()
             .earner_code("123456")
             .worker_id("yuchan2")
             .payment_ym(300000)
             .build();

     mockMvc.perform(post("/input/sum_tax")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(sumTaxVO)))
             .andExpect(status().isBadRequest());
 }
}
