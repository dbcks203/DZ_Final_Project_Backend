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

import com.douzone.entity.input.SumTaskVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SumTaskTest {
 @Autowired
 private MockMvc mockMvc;

 @Test
 public void testSumTaskNormal() throws Exception {
     SumTaskVO getTaskVO = SumTaskVO.builder()
             .worker_id("yuchan2")
             .payment_ym(202205)
             .build();

     mockMvc.perform(post("/input/sum_task")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(getTaskVO)))
             .andExpect(status().isOk());
 }

 @Test
 public void testSumTaskMissingWorkerId() throws Exception {
     SumTaskVO getTaskVO = SumTaskVO.builder()
             .payment_ym(202201)
             .build();

     mockMvc.perform(post("/input/sum_task")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(getTaskVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaskInvalidPaymentYm() throws Exception {
     SumTaskVO getTaskVO = SumTaskVO.builder()
             .worker_id("yuchan2")
             .payment_ym(199999)
             .build();

     mockMvc.perform(post("/input/sum_task")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(getTaskVO)))
             .andExpect(status().isBadRequest());
 }

 @Test
 public void testSumTaskOutOfRangePaymentYm() throws Exception {
     SumTaskVO getTaskVO = SumTaskVO.builder()
             .worker_id("yuchan2")
             .payment_ym(300000)
             .build();

     mockMvc.perform(post("/input/sum_task")
             .contentType(MediaType.APPLICATION_JSON)
             .content(new ObjectMapper().writeValueAsString(getTaskVO)))
             .andExpect(status().isBadRequest());
 }
}
