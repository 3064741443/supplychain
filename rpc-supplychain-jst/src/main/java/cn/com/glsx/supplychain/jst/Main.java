package cn.com.glsx.supplychain.jst;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.main.AbsOreBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

import cn.com.glsx.supplychain.jst.util.JstUtils;

/**
 * @Title: Main.java
 * @Description:
 * @author deployer name 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan
public class Main extends AbsOreBootStarter {
	
	@Override
    public void run(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }
    
    public static void main(String[] args)
    {	
    	Main main = new Main();
        main.run(args);
    }
}
