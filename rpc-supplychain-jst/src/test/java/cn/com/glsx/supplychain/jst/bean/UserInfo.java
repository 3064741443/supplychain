package cn.com.glsx.supplychain.jst.bean;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/8 17:05
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;


    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
