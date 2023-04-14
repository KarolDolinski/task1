package com.kd.assignment.validator;

import com.kd.assignment.dto.DataRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

public class DataValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return DataRequest.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        DataRequest myForm = (DataRequest) target;
//
//        }
//    }
}

/*
20


 public class MyFormValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return MyForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MyForm myForm = (MyForm) target;

        for (int i = 0; i < myForm.getListObjects().size(); i++) {
            TypeA typeA = myForm.getListObjects().get(i);

            if(typeAHasAnErrorOnNumber) {
                errors.rejectValue("listObjects[" + i + "].number", "your_error_code");
            }

            ...
        }

        ...
    }

 */
