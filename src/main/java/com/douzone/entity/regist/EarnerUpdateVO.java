package com.douzone.entity.regist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarnerUpdateVO {

    @NotBlank(message = "param_name is required")
    private String param_name;
    
    
    
    private String param_value;

    @NotBlank(message = "worker_id is required")
    private String worker_id;

    @NotBlank(message = "earner_code is required")
    @Pattern(regexp = "^\\d{6}$", message = "earner_code must be a 6-digit number")
    private String earner_code;
}