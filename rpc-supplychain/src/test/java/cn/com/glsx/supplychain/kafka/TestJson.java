package cn.com.glsx.supplychain.kafka;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class TestJson {

    public static void main(String[] args) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sn", "123456");
        System.out.println("========" + JSONObject.toJSONString(params));
    }
}
