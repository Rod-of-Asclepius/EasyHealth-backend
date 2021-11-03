package com.roa.easyhealth.exception;

import com.roa.easyhealth.entity.result.MyResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

public @EqualsAndHashCode(callSuper = true)
@Data
class MyException extends Exception{
    public MyException(String message) {
        super(message,new Throwable("error"));
    }
    public MyException(String message, Throwable throwable) {
        super(message,throwable);
    }
    public MyException(String message, String throwable) {
        super(message,new Throwable(throwable));
    }
    public MyException(String message, HttpStatus status) {
        super(message,new Throwable(String.valueOf(status.value())));
    }
    public MyException(MyResult result) {
        super(result.getResultData().getMessage(),new Throwable(result.getStatus().toString()));
    }
}
