package com.roa.easyhealth.entity.result;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.roa.easyhealth.entity.result.MyResultData.FAILED;
import static com.roa.easyhealth.entity.result.MyResultData.SUCCESS;


@Getter
@Setter
public class MyResult extends ResponseEntity<MyResultData> {
    private MyResultData resultData;
    private static HttpStatus status;

    public MyResult(HttpStatus status, MyResultData data) {
        super(data, status);
        resultData = data;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public int getDataCode() {
        return resultData.getCode();
    }

    public MyResult setDataCode(int code) {
        resultData.setCode(code);
        return new MyResult(status, resultData);
    }

    public MyResult setResultData(MyResultData resultData) {
        this.resultData = resultData;
        return new MyResult(status, resultData);
    }

    public String getDataMessage() {
        return resultData.getMessage();
    }

    public MyResult setDataMessage(String message) {
        resultData.setMessage(message);
        return new MyResult(status, resultData);
    }

    public Object getDataData() {
        return resultData.getData();
    }

    public MyResult setDataData(Object data) {
        resultData.setData(data);
        return new MyResult(status, resultData);
    }

    /**
     * 返回状态200的http结果
     * @param data 要返回的数据
     */
    public static MyResult OK(Object data) {
        MyResultData mrd = new MyResultData(SUCCESS, "OK,成功", data);
        status = HttpStatus.OK;
        return new MyResult(status, mrd);
    }
    public static MyResult OK() {
        return OK(null);
    }

    /**
     * 返回状态400的http结果
     * @param data 要返回的数据
     */
    public static MyResult BAD_REQUEST(Object data) {
        MyResultData mrd = new MyResultData(FAILED, "BAD_REQUEST,语法错误", data);
        status = HttpStatus.BAD_REQUEST;
        return new MyResult(status, mrd);
    }
    public static MyResult BAD_REQUEST() {
        return BAD_REQUEST(null);
    }

    /**
     * 返回状态401的http结果
     * @param data 要返回的数据
     */
    public static MyResult UNAUTHORIZED(Object data) {
        MyResultData mrd = new MyResultData(FAILED, "UNAUTHORIZED,未授权", data);
        status = HttpStatus.UNAUTHORIZED;
        return new MyResult(status, mrd);
    }
    public static MyResult UNAUTHORIZED() {
        return BAD_REQUEST(null);
    }

}
