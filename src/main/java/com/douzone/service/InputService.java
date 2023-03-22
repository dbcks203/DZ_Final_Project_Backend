package com.douzone.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.dao.InputDAO;
import com.douzone.entity.EarnerTaxVO;
import com.douzone.entity.TaxInfoVO;

import lombok.extern.slf4j.Slf4j;

@Service("inputService")
@Transactional
@Slf4j
public class InputService {

	@Autowired
	InputDAO inputDAO;

	public List<Map<String, Object>> earner_search(Map<String, Object> params) {
		return inputDAO.earner_search(params);
	}

	public List<Map<String, Object>> get_earners(Map<String, Object> params) {
		return inputDAO.get_earners(params);
	}

	public List<TaxInfoVO> get_tax(Map<String, Object> params) {
		return inputDAO.get_tax(params);
	}

	public Map<String, Object> update_taxinfo(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		params.put("result", null);
			
		int i=1;
		inputDAO.update_taxinfo(params);
		ResultSet rs = (ResultSet) params.get("result");// 뒤부터 값 확인 코드 => 결과 확인 후 삭제
		List<Map<String, Object>> earner_taxs  = new ArrayList<>();
		try {
			while (rs.next()) {
				Map<String, Object> earner_tax = new HashMap<String, Object>();
				EarnerTaxVO earnerTax = new EarnerTaxVO(
					rs.getString("is_tuition"),
					rs.getString("deduction_amount"),
					rs.getString("is_artist"),
					rs.getString("ins_reduce"),
					rs.getString("tax_id"),
					rs.getString("tax_rate"),
					rs.getString("ins_rate"),
					rs.getString("total_payment"),
					rs.getString("accrual_ym"),
					rs.getString("payment_ym"),
					rs.getString("payment_date"),
					rs.getString("calculated"),
					rs.getString("tax_income"),
					rs.getString("tax_local"),
					rs.getString("artist_cost"),
					rs.getString("ins_cost"),
					rs.getString("tax_total"),
					rs.getString("real_payment")
				);
				earner_tax.put("earner_tax"+ (i++), earnerTax);
				earner_taxs.add(earner_tax);
			}
		result.put("earner_tax", earner_taxs);
		} catch (Exception e) {
			System.out.println(e.getMessage());// 로그로 고칠 것
		}
		return result;
	}

	public void update_taxdate(Map<String, Object> params) {
		inputDAO.update_taxdate(params);
	}

	public int  tax_insert(Map<String, Object> params) {
		inputDAO.tax_insert(params);
		return (int)params.get("tax_id");
	}

//	public void update_taxinfo(Map<String, Object> params) {
		// inputDAO.tax_update(params);
//		inputDAO.update_taxinfo(params);// 정보 넣고 백업
//		System.out.println(params);
//		 ResultSet rs = (ResultSet)params.get("result");
//		 System.out.println(rs);
//		  try{
//	            while (rs.next()){
//	                String empNo = rs.getString("is_tuition");
//	                String tax_id = rs.getString("tax_id");
//	                System.out.println(empNo);
//	                System.out.println(tax_id);
//	            }
//	        }catch (Exception e ){
//	            System.out.println(e.getMessage());
//	        }
//	}

}