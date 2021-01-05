package cn.com.glsx.merchant.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("static")
public class StaticProperty
{
    private String resource;
    
    public String getResource()
    {
        return resource;
    }
    
    public void setResource(String resource)
    {
        this.resource = resource;
    }
    
}
