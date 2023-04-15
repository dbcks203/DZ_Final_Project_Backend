package com.douzone.dao;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

@Mapper
@Repository("inputDAO")
public interface InputDAO {
	
	List<Map<String, Object>> earner_search( EarnerSearchVO earnerSearchVO);

	List<Map<String, Object>> get_task( GetTaskVO getTaskVO);

	List<TaxInfoVO> get_tax( GetTaxVO getTaxVO);

	void tax_insert(TaxInsertVO taxInsertVO);

	void update_taxdate( UpdateTaxDateVO updateTaxDateVO);

	Map<String, Object> update_taxinfo(UpdateTaxInfoVO updateTaxInfoVO);

	void task_insert( TaskInsertVO taskInsertVO);

	void task_delete( TaskDeleteVO taskDeleteVO);

	Map<String, Object> sum_task(SumTaskVO sumTaskVO);

	Map<String, Object> sum_tax(SumTaxVO sumTaxVO);

	List<String> get_calendar(GetTaxVO getTaxVO);

	void calendar_update(GetTaxVO getTaxVO);

	void delete_calendar(GetTaxVO getTaxVO);

	void calendar_ins_del(GetTaxVO getTaxVO);
}