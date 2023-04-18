package com.douzone.controller.input;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.douzone.entity.input.TaskDeleteVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskDeleteTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testTaskDeleteNormal() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		earner_codes.add("123456");
		earner_codes.add("789012");

		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(202202)
				.earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isOk());
	}

	@Test
	public void testTaskDeleteMissingWorkerId() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		earner_codes.add("123456");
		earner_codes.add("789012");

		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().payment_ym(202201).earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}

	@Test
	public void testTaskDeleteInvalidPaymentYm() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		earner_codes.add("123456");
		earner_codes.add("789012");

		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(199999)
				.earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}

	@Test
	public void testTaskDeleteOutOfRangePaymentYm() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		earner_codes.add("123456");
		earner_codes.add("789012");

		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(300000)
				.earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}

	@Test
	public void testTaskDeleteMissingEarnerCodes() throws Exception {
		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(202201).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}

	@Test
	public void testTaskDeleteEmptyEarnerCodes() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(202201)
				.earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}

	@Test
	public void testTaskDeleteInvalidEarnerCode() throws Exception {
		List<String> earner_codes = new ArrayList<>();
		earner_codes.add("12345a");
		earner_codes.add("789012");
		TaskDeleteVO taskDeleteVO = TaskDeleteVO.builder().worker_id("yuchan2").payment_ym(202201)
				.earner_codes(earner_codes).build();

		mockMvc.perform(delete("/input/task_delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDeleteVO))).andExpect(status().isBadRequest());
	}
}
