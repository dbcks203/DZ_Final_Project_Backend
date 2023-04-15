package com.douzone.entity.input;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaxInfoVO {
	
	
	@Min(value = 0, message = "세율은 0 이상의 값을 입력해주세요.")
	private double tax_rate;
	@Min(value = 0, message = "지급총액은 0 이상의 값을 입력해주세요.")	
	private int total_payment;
	@Min(value = 1, message = "tax_id must be greater than 0")
    private Integer tax_id;
	
}
