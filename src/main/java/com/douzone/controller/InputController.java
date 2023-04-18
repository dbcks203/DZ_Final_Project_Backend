package com.douzone.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.entity.input.EarnerSearchVO;
import com.douzone.entity.input.GetTaskVO;
import com.douzone.entity.input.GetTaxVO;
import com.douzone.entity.input.SumTaskVO;
import com.douzone.entity.input.SumTaxVO;
import com.douzone.entity.input.TaskDeleteVO;
import com.douzone.entity.input.TaskInsertVO;
import com.douzone.entity.input.TaxInsertVO;
import com.douzone.entity.input.UpdateTaxDateVO;
import com.douzone.entity.input.UpdateTaxInfoVO;
import com.douzone.handler.GlobalResponseHandler;
import com.douzone.service.InputService;

@RestController("inputController")
@CrossOrigin("*")
public class InputController {

	@Autowired
	InputService inputService;

	@Autowired
	GlobalResponseHandler gloabalResponseHandler;

	@PostMapping(value = "/input/earner_search")
	public Map<String, Object> earner_search(@Valid @RequestBody EarnerSearchVO earnerSearchVO) throws Exception{
		Map<String, Object> result = new HashMap<>();
		result.put("earner_list", inputService.earner_search(earnerSearchVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/get_task")
	public Map<String, Object> get_task(@Valid @RequestBody GetTaskVO getTaskVO) throws Exception  {
		Map<String, Object> result = new HashMap<>();
		
		int plz_errpr =  (int) result.get("plz Error");
		
		List<Map<String, Object>> task_list = inputService.get_task(getTaskVO);
		result.put("task_list", task_list);
		result.put("task_count", task_list.size());
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/sum_task")
	public Map<String, Object> sum_task(@Valid @RequestBody SumTaskVO sumTaskVO) {
		Map<String, Object> result = new HashMap<>();
		result.put("sum_task", inputService.sum_task(sumTaskVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/task_insert")
	public Map<String, Object> task_insert(@Valid @RequestBody TaskInsertVO taskInsertVO) {
		Map<String, Object> result = new HashMap<>();
		inputService.task_insert(taskInsertVO);
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/input/task_delete")
	public Map<String, Object> task_delete(@Valid @RequestBody TaskDeleteVO taskDeleteVO) {
		Map<String, Object> result = new HashMap<>();
		inputService.task_delete(taskDeleteVO);
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/sum_tax")
	public Map<String, Object> sum_tax(@Valid @RequestBody SumTaxVO sumTaxVO) {
		Map<String, Object> result = new HashMap<>();
		result.put("sum_tax", inputService.sum_tax(sumTaxVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/get_tax")
	public Map<String, Object> get_tax(@Valid @RequestBody GetTaxVO getTaxVO) {
		Map<String, Object> result = new HashMap<>();
		result.put("tax_list", inputService.get_tax(getTaxVO));
		result.put("select_date", inputService.get_calendar(getTaxVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value = "/input/tax_insert")
	public Map<String, Object> tax_insert(@Valid @RequestBody TaxInsertVO taxInsertVO) {
		Map<String, Object> result = new HashMap<>();
		result.put("tax_id", inputService.tax_insert(taxInsertVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PatchMapping(value = "/input/update_taxdate")
	public Map<String, Object> update_taxdate(@Valid @RequestBody UpdateTaxDateVO updateTaxDateVO) {
		Map<String, Object> result = new HashMap<>();
		inputService.update_taxdate(updateTaxDateVO);
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PostMapping(value="/input/calendar_insert")
	public Map<String, Object> calendar_insert(@Valid @RequestBody  GetTaxVO getTaxVO) {
		Map<String, Object> result = new HashMap<>();
		inputService.calendar_ins_del(getTaxVO);
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}

	@PatchMapping(value = "/input/update_taxinfo")
	public Map<String, Object> update_taxinfo(@Valid @RequestBody  UpdateTaxInfoVO updateTaxInfoVO) throws SQLException {
		Map<String, Object> result = new HashMap<>();
		result.put("earner_tax", inputService.update_taxinfo(updateTaxInfoVO));
		return gloabalResponseHandler.handleResponse(result, HttpStatus.OK);
	}
	
}
