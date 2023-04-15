package com.douzone.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.dao.InputDAO;

import com.douzone.entity.TaxInfoVO;
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

@Transactional
@Service("inputService")
public class InputService {

	@Autowired
	InputDAO inputDAO;

	public List<Map<String, Object>> earner_search(EarnerSearchVO earnerSearchVO) throws Exception  {
		List<Map<String, Object>> earner_search = inputDAO.earner_search(earnerSearchVO);
		
		UtilService.decrypt_list(earner_search);
		
		return earner_search;
	}

	public List<Map<String, Object>> get_task(GetTaskVO getTaskVO) throws Exception {
		List<Map<String, Object>> get_task = inputDAO.get_task(getTaskVO);
		
		UtilService.decrypt_list(get_task);
		
		return get_task;
	}

	public List<TaxInfoVO> get_tax(GetTaxVO getTaxVO) {
		return inputDAO.get_tax(getTaxVO);
	}
	
	public Map<String,Object> update_taxinfo(UpdateTaxInfoVO updateTaxInfoVO) {
		return inputDAO.update_taxinfo(updateTaxInfoVO);
	}


	public void update_taxdate(UpdateTaxDateVO updateTaxDateVO) {
		inputDAO.update_taxdate(updateTaxDateVO);
	}

	public int tax_insert(TaxInsertVO taxInsertVO) {
		inputDAO.tax_insert(taxInsertVO);
		return taxInsertVO.getTax_id();
	}

	public void task_insert(TaskInsertVO taskInsertVO) {
		inputDAO.task_insert(taskInsertVO);
	}

	public void task_delete(TaskDeleteVO taskDeleteVO) {
		inputDAO.task_delete(taskDeleteVO);
	}

	public Map<String, Object> sum_task(SumTaskVO sumTaskVO) {
		return inputDAO.sum_task(sumTaskVO);
	}

	public Map<String, Object> sum_tax(SumTaxVO sumTaxVO) {
		return inputDAO.sum_tax(sumTaxVO);
	}
	
	public List<String> get_calendar(GetTaxVO getTaxVO) {
		return inputDAO.get_calendar(getTaxVO);
	}

	public void calendar_ins_del(GetTaxVO getTaxVO) {
		String[] select_dates = getTaxVO.getSelect_dates();
		String select_date = "{";
		
		for(String i : select_dates) {
			select_date = select_date.concat(i+", ");
		}
		
		select_date = select_date.substring(0,select_date.length()-2);
		select_date = select_date.concat("}");
		getTaxVO.setSelect_date(select_date);
		
		inputDAO.calendar_ins_del(getTaxVO);
	}
}