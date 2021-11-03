package com.roa.easyhealth.util;

import com.alibaba.fastjson.JSON;
import com.roa.easyhealth.entity.result.MyResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    /**
     * 往响应里加信息
     *
     * @param response 响应对象
     * @param result 返回结果
     */
    public static void responseReturnJson(HttpServletResponse response, MyResult result) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            String ResultStr = JSON.toJSONString(result.getResultData());
            response.setStatus(result.getStatus().value());
            writer = response.getWriter();
            writer.print(ResultStr);

        } catch (IOException e) {

        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static String getAllAttribute(HttpServletRequest request, String key){
        return StringUtil.getNotNullStr(
                request.getParameter(key),
                (String) request.getAttribute(key),
                request.getHeader(key)
        );
    }
}
