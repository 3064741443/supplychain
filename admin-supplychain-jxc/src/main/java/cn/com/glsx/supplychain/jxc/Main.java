package cn.com.glsx.supplychain.jxc;

import org.oreframework.boot.main.AbsOreBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Title: Main.java
 * @Description:
 * @author deployer name 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SpringBootApplication
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
