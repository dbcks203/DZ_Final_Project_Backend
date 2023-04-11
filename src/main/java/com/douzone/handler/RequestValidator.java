package com.douzone.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.douzone.entity.regist.EarnerUpdateVO;
import java.util.regex.Pattern;

public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EarnerUpdateVO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EarnerUpdateVO request = (EarnerUpdateVO) target;
        
        Set<String> allowedParamNames = new HashSet<>(Arrays.asList(
                "earner_name",
                "residence_status",
                "is_native",
                "personal_no",
                "zipcode",
                "address",
                "address_detail",
                "email1",
                "email2",
                "tel1",
                "tel2",
                "tel3",
                "phone1",
                "phone2",
                "phone3",
                "etc",
                "is_tuition",
                "deduction_amount",
                "is_artist",
                "earner_type",
                "ins_reduce",
                "sworker_type",
                "occupation_code",
                "rate_coefficient",
                "sworker_reduce",
                "workinjury_reduce"
                ));

        // param_name 값이 allowedParamNames에 포함되지 않으면 에러 발생
        if (!allowedParamNames.contains(request.getParam_name())) {
            errors.rejectValue("param_name", "param_name.invalid", "Invalid param_name.");
        }

        String param_value = request.getParam_value();

        switch (request.getParam_name()) {
            case "is_tuition":
            case "is_artist":
            case "is_sworker":
                if (!("Y".equals(param_value) || "N".equals(param_value))) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be 'Y' or 'N'.");
                }
                break;

            case "is_native":
                if (!("내".equals(param_value) || "외".equals(param_value))) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be '내' or '외'.");
                }
                break;
                
            case "earner_type":
            	if (!("단기".equals(param_value) || "일반".equals(param_value))) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be '단기' or '일반'.");
                }
                break;
                
            case "personal_no":
            	if (!isValidPersonalNo(param_value)) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be a valid Personal_Number.");
                }
            	break;
            	
            case "tel1":
            case "tel2":
            case "tel3":
            case "phone1":
            case "phone2":
            case "phone3":
            case "occupation_code":
                if (!(Pattern.matches("^\\d{3,4}$", param_value))) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be a 3-4 digit number.");
                }
                break;
                
            case "deduction_amount":
            case "rate_coefficient":
            case "ins_reduce":
            case "sworker_reduce":
            case "workinjury_reduce":
            	if (!isPositiveInteger(param_value)) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be a positive integer.");
                }
                break;
                
            case "email2":
            	if (!isValidEmailDomain(param_value)) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should be a valid email domain.");
                }
                break;
                
            case "email1":
            case "earner_name":
            case "zipcode":
            case "address":
            case "address_detail":
                if (isValidInput(param_value)) {
                    errors.rejectValue("param_value", "param_value.invalid", "ParamValue should contain without special characters.");
                }
                break;
            case "etc":
            	break;
        }
    }
    
    private boolean isPositiveInteger(String value) {
        try {
            int intValue = Integer.parseInt(value);
            return intValue > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidPersonalNo(String value) {
        
        String rrnPattern = "^\\d{6}-(1|2|3|4)\\d{6}$";
        String frnPattern = "^\\d{6}-(5|6|7|8)\\d{5}[A-Z]$";

        return value.matches(rrnPattern) || value.matches(frnPattern);
    }
    
    private boolean isValidEmailDomain(String emailDomain) {
        String emailDomainPattern = "^(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$";
        return emailDomain.matches(emailDomainPattern);
    }
    
    private boolean isValidInput(String input) {
        String inputPattern = "^[0-9A-Za-z가-힣]+$";
        return input.matches(inputPattern);
    }
}

