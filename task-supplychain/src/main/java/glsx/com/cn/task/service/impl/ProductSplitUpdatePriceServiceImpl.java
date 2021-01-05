package glsx.com.cn.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import glsx.com.cn.task.mapper.am.ProductSplitHistoryPriceMapper;
import glsx.com.cn.task.service.ProductSplitUpdatePriceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSplitUpdatePriceServiceImpl implements ProductSplitUpdatePriceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;

    @Override
    public void updateProductSplit() {
    	
    	logger.info("ProductSplitUpdatePriceServiceImpl::updateProductSplit start");
    	
    	Integer curDateTimeStamp = this.getCurDayTimeStamp();
    	
        ProductSplitHistoryPrice record = new ProductSplitHistoryPrice(); 
        List<ProductSplitHistoryPrice> listPrice = productSplitHistoryPriceMapper.listProductSplitHistoryPrice(record); 
        Map<String,List<ProductSplitHistoryPrice>> mapPrice = new HashMap<String,List<ProductSplitHistoryPrice>>();
        //productcode+time : type
        Map<String,Byte> mapTypeRefect = new HashMap<String,Byte>();
        //反向修改表
        Map<Byte,List<Long>> mapReverse = new HashMap<Byte,List<Long>>();
        for(ProductSplitHistoryPrice price:listPrice)
        {
        	List<ProductSplitHistoryPrice> subList = mapPrice.get(price.getProductCode());
        	if(subList == null)
        	{
        		subList = new ArrayList<ProductSplitHistoryPrice>();
        	}
        	//去重
        	boolean bIsRepeat = false;
        	for(ProductSplitHistoryPrice subPrice:subList)
        	{
        		
        		if(subPrice.getTime().equals(price.getTime()))
        		{
        			bIsRepeat = true;
        			break;
        		}
        	}
        	if(bIsRepeat)
        	{
        		continue;
        	}
        	subList.add(price);
        	mapPrice.put(price.getProductCode(), subList);
        }
        
        Iterator iterPrice = mapPrice.entrySet().iterator();
        while(iterPrice.hasNext())
        {
        	Map.Entry entry = (Map.Entry) iterPrice.next();
        	String productCode = (String)entry.getKey();
        	List<ProductSplitHistoryPrice> subList = (List<ProductSplitHistoryPrice>)entry.getValue();
        	this.ListSort(subList);
        	int i = 0;
        	int pos = -1;
        	for(ProductSplitHistoryPrice his:subList)
        	{
        		Integer hisDateTimeStamp = this.getTimeStamp(his.getTime());
        		if(curDateTimeStamp >= hisDateTimeStamp)
        		{
        			pos=i;      		
        		}
        		i++;
        	}
        	
        	if(pos < 0)
        	{
        		for(ProductSplitHistoryPrice his:subList)
        		{
        			his.setType(Byte.valueOf("1")); //修改成未来价格
        		}
        	}
        	else
        	{
        		i = 0;
        		for(ProductSplitHistoryPrice his:subList)
        		{
        			if(i<pos)
        			{
        				his.setType(Byte.valueOf("2")); //修改成历史价格
        			}
        			else if(i == pos)
        			{
        				his.setType(Byte.valueOf("0")); //修改成当前价格
        			}
        			else
        			{
        				his.setType(Byte.valueOf("1")); //修改成未来价格
        			}
        			i++;
        		}
        	}
        	
        	for(ProductSplitHistoryPrice his:subList)
        	{
        		mapTypeRefect.put(his.getProductCode()+his.getTime(), his.getType());    
        	}	
        }
        
        //按照类型找出update的id 
        for(ProductSplitHistoryPrice price:listPrice)
        {
        	String strKey = price.getProductCode() + price.getTime();
        	Byte fdType = mapTypeRefect.get(strKey);
        	List<Long> listId = mapReverse.get(fdType);
        	if(listId == null)
        	{
        		listId = new ArrayList<Long>();
        	}
        	listId.add(price.getId());
        	mapReverse.put(fdType, listId);
        }
       
        Iterator iterType = mapReverse.entrySet().iterator();
        while(iterType.hasNext())
        {
        	Map.Entry entry = (Map.Entry) iterType.next();
        	Byte type = (Byte)entry.getKey();
        	List<Long> listId = (List<Long>)entry.getValue();
        	if(listId != null && listId.size() > 0)
        	{
        		productSplitHistoryPriceMapper.updateTypeProductSplitPrice(type, listId);
        	}
        }
        
        logger.info("ProductSplitUpdatePriceServiceImpl::updateProductSplit end!");
    }
    
    private Integer getCurDayTimeStamp()
    {
    	Integer ret = 0;
    	try 
    	{
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	String curDay = df.format(new Date());
	    	curDay += " 00:00:00";
			Date curDate = df.parse(curDay);
			ret = (int)(curDate.getTime()/1000);
		} 
    	catch (ParseException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ret;
    }
    
    private Integer getTimeStamp(Date dayDate)
    {
    	Integer ret = 0;
    	ret = (int)(dayDate.getTime()/1000);
    	return ret;
    }
    
    //按照日期升序
    private void ListSort(List<ProductSplitHistoryPrice> list)
    {
    	Collections.sort(list,new Comparator<ProductSplitHistoryPrice>(){
    		@Override
    		public int compare(ProductSplitHistoryPrice o1,ProductSplitHistoryPrice o2)
    		{
    			int flag = o1.getTime().compareTo(o2.getTime()); 	
    			return flag;
    		}
    	});
    }
    
}
