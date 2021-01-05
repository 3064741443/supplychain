package glsx.com.cn.task;

import glsx.com.cn.task.service.SupplyChainSyncService;

import org.oreframework.boot.main.AbsOreBootStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Title: Main.java
 * @Description:
 * @author leiyj 
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SpringBootApplication
@EnableScheduling
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
