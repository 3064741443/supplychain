package cn.com.glsx.supplychain;

import java.math.BigDecimal;

import org.oreframework.boot.main.AbsOreBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

/**
 * @Title: Main.java
 * @Description:
 * @author zhoudan 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan
@EnableTransactionManagement
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
