package com.roa.easyhealth.exception;

import com.roa.easyhealth.entity.result.MyResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常拦截
     * @param exception
     */
    @ExceptionHandler({Exception.class})
    public MyResult handler(Exception exception) {

        StringBuilder errMsg = new StringBuilder("error ");
        // 方法参数无效 异常
        if(exception instanceof MethodArgumentNotValidException) {
            BindingResult bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            List<FieldError> fieldErrorList = bindResult.getFieldErrors();
            fieldErrorList.forEach(fieldErrors -> {
                        FieldError fieldError = fieldErrors;
                        if (errMsg.toString() != null) {
                            errMsg.append(",");
                        }
                        errMsg.append(fieldError.getDefaultMessage());
                    }
            );
//        }else if (exception instanceof ConstraintViolationException) {
//            // 约束冲突异常
//            String[] messages = exception.getMessage().split(", ");
//            String[] split = messages[0].split(": ");
//            if(split.length>1){
//                errMsg.append(split[1]);
//            }
//            for(int i=1;i< messages.length;i++){
//                split = messages[i].split(": ");
//                if(split.length>1){
//                    errMsg.append(","+split[1]);
//                }
//            }
//
        }else if(exception instanceof MyException){
            String message = exception.getMessage();
            errMsg.append(message);
        }else{
            exception.printStackTrace();
        }

//        log.error("异常：",exception);
        return MyResult.BAD_REQUEST().setDataMessage(errMsg.toString());
    }
}
