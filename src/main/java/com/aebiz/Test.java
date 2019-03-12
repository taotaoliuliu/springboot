package com.aebiz;

import org.apache.commons.collections.map.LRUMap;

public class Test {
	
	
	/**
	 * 1. put当新增加一个集合元素对象，则表示该对象是最近被访问的

		2. get 操作会把当前访问的元素对象作为最近被访问的，会被移到链接表头
	 * @param args
	 */
	  public static void main(String[] args) {  
	        LRUMap lruMap = new LRUMap();  
	        lruMap.put("a1", "1");  
	        lruMap.put("a2", "2");  
	    
	        lruMap.put("a3", "3");  
	        lruMap.put("a4", "4");  
	      //  lruMap.get("a2");  
	        // mark as recent used  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	        lruMap.get("a1");  
	      //  lruMap.get("a2");  
	        // mark as recent used  
	        System.out.println(lruMap);  
	        lruMap.get("a2");  
	        System.out.println(lruMap);  
	        lruMap.get("a1");  
	        System.out.println(lruMap);  

	    }  

}
