package com.roa.easyhealth.entity.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
public class MyResultData {
    //内部状态码（与http无关）
    public static final int SUCCESS = 200;
    public static final int FAILED = 400;
    //描述成功与否
    private int code;
    //返回的信息
    private String message;
    //返回的数据
    private Object data;

    public MyResultData() {
    }

    public MyResultData(Object data) {
        this.data = data;
    }

    public MyResultData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        Object data1 = data;
        if(data!=null){
            final Class<?> aClass = data.getClass();
        }
    }

    public MyResultData addProp(String name,Object value) throws JsonProcessingException {

        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode objectNode = objectMapper.valueToTree(data);
        objectNode.set(name,objectMapper.valueToTree(value));
        data = objectMapper.readValue(objectNode.toString(),Object.class);
        return this;
    }
}
