package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.supplychain.jst.Main;
import cn.com.glsx.supplychain.jst.bean.UserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/8/20 15:44
 */

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@RunWith(SpringJUnit4ClassRunner.class)
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = Main.class)
public class TestFastJson {

    //将Bean转成Json格式数据
    @Test
    public void EntityTojson() {
      UserInfo userInfo=new UserInfo("luoqinag",33);
      JSONObject jsonObject=(JSONObject) JSON.toJSON(userInfo);
        System.out.println("将Bean转成Json格式数据"+"姓名："+jsonObject.get("name")+" 年龄："+jsonObject.get("age"));
    }

    // List集合转json格式字符串
    @Test
    public void listToJson() {
        List<UserInfo> userInfoList=new ArrayList<>();
        UserInfo userInfo1=new UserInfo("张三丰",89);
        UserInfo userInfo2=new UserInfo("张无忌",22);
        userInfoList.add(userInfo1);
        userInfoList.add(userInfo2);
        String result=JSON.toJSONString(userInfoList);
        System.out.println("List集合转json格式字符串:"+result);

    }

    // map转json格式字符串
    @Test
    public void mapToJson() {
        Map<String,UserInfo> map=new HashMap<>();
        UserInfo userInfo1=new UserInfo("张三丰",89);
        UserInfo userInfo2=new UserInfo("张无忌",22);
        map.put(UUID.randomUUID().toString(),userInfo1);
        map.put(UUID.randomUUID().toString(),userInfo2);
        String result=JSON.toJSONString(map);
        System.out.println("map转json格式字符串:"+result);

    }

    // 字符数组转化为JSon
    @Test
    public void StringArrayToJSONArray() {
        //String arrayAyy = "[[\'马云',50],null,[\'马化腾',30]]";
        String arrayStr="[[\"马云\",50],null,[\"马化腾\",30]]";
        com.alibaba.fastjson.JSONArray jsonArray= com.alibaba.fastjson.JSONArray.parseArray(arrayStr);
        System.out.println("数组:"+jsonArray);
        System.out.println("数组大小："+jsonArray.size());
        Collection collection=new Vector();
        collection.add(null);
        jsonArray.removeAll(collection);
        System.out.println("数组:"+jsonArray);
        System.out.println("数组大小："+jsonArray.size());
    }


    // 将JSON字符数组转成JSONArray格式数据
    @Test
    public void StringToJSONArray() {
        //String arrayAyy = "[[\'马云',50],null,[\'马化腾',30]]";
        String s = "[{\"name\":\"张三丰\",\"age\":98},{\"name\":\"张无忌\",\"age\":33}]";
        //将JSON文本转换为JSONArray
        JSONArray jsonArray=JSON.parseArray(s);
        System.out.println("数组:"+jsonArray);
        System.out.println("数组大小："+jsonArray.size());
        String str=jsonArray.getString(0);
        JSONObject jsonObject= JSON.parseObject(str);
        System.out.println(jsonObject.get("name").toString()+jsonObject.get("age"));

    }

    // 复杂数据类型
    @Test
    public void Complexdata() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", "zhangsan");
        map.put("age", 24);
        map.put("sex", "男");

        // map集合
        HashMap<String, Object> temp = new HashMap<String, Object>();
        temp.put("name", "xiaohong");
        temp.put("age", "23");
        map.put("girlInfo", temp);

        // list集合
        List<String> list = new ArrayList<String>();
        list.add("爬山");
        list.add("骑车");
        list.add("旅游");
        map.put("hobby", list);
        String jsonString = JSON.toJSONString(map);
        System.out.println("复杂数据类型:" + jsonString);
    }

    // 格式话日期
    public static void Deserialization(String json) {
        String str="{\"name\":\"luoqiang\",\"age\":25}";
        UserInfo userInfo = JSON.parseObject(str, UserInfo.class);
        System.out.println("姓名是:" + userInfo.getName() + ", 年龄是:"
                + userInfo.getAge());
    }

    // 格式化日期
    @Test
    public  void DateFormate() {
       Date date=new Date();
        System.out.println("输出毫秒值：" + JSON.toJSONString(date));
        System.out.println("默认格式为:"
                + JSON.toJSONString(date,
                SerializerFeature.WriteDateUseDateFormat));
        System.out.println("自定义日期："
                + JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd",
                SerializerFeature.WriteDateUseDateFormat));
    }


    //将Json（文本）字符串数据信息转换为JsonObject对象(相当于json数据),通过K V的形式获取值
    @Test
    public  void stringToJsonObject(){
        String str="{\"attribCode\":\"GLSX41083\",\"batch\":\"202012\",\"iccid\":\"8986043519185003314\",\"imei\":\"8670980402595059\",\"imsi\":\"1111111111\",\"simCardNo\":\"111111111\",\"sn\":\"8670980402839359\",\"vcode\":\"111111\",\"wareHouseId\":2,\"wareHouseIdUp\":2,\"wareHouseName\":\"测试环境新用户hgm仓库\",\"wareHouseUpName\":\"测试环境新用户hgm仓库\"}";
        //将Json文本数据信息转换为JsonObject对象(相当于json数据)
        JSONObject jsonObject=JSON.parseObject(str);
        //利用键值对的方式获取到值
        System.out.println("姓名："+jsonObject.get("name")+" 年龄:"+jsonObject.get("age")+jsonObject);
    }

    //JSON（文本）字符串转换成实体类
    @Test
    public  void stringToBean(){
        String str="{\"name\":\"luoqiang\",\"age\":25}";
        //将Json文本数据信息转换为JsonObject对象(相当于json数据)
        UserInfo userInfo=JSON.parseObject(str,UserInfo.class);
        //利用键值对的方式获取到值
        System.out.println("姓名："+userInfo.getName()+" 年龄:"+userInfo.getAge());
    }

    //Javabean对象转换成String类型的JSON字符串
    @Test
    public  void toJSONString(){
        UserInfo userInfo1=new UserInfo("张三丰",89);

        //Javabean对象转换成String类型的JSON字符串
        String jsonString=JSONObject.toJSONString(userInfo1);
        System.out.println(jsonString);

        UserInfo userInfo2=JSONObject.toJavaObject(JSON.parseObject(jsonString),UserInfo.class);
        System.out.println(userInfo2.toString());
    }

}
