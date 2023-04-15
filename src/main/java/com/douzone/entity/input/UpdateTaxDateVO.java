package com.douzone.entity.input;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaxDateVO {

	@Min(value = 1, message = "지급일 입력을 다시 확인해주세요. 지급일은 1-31 사이의 양수입니다.")
    @Max(value = 31, message = "지급일 입력을 다시 확인해주세요. 지급일은 1-31 사이의 양수입니다.")
    private int payment_date;

	@Min(value = 200000, message = "귀속년월 형식을 다시 확인해주세요.")
    @Max(value = 299999, message = "귀속년월 형식을 다시 확인해주세요.")
    private int accrual_ym;

    @NotNull(message = "tax_id is required")
    private Integer tax_id;

}