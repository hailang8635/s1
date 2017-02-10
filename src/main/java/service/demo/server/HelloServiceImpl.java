package service.demo.server;
import org.apache.thrift.TException;

import service.demo.Hello.Iface; 
 public class HelloServiceImpl implements Iface { 
	 
    
//    @Override 
    public String helloString(String para) throws TException { 
//    	System.out.println(para); 
        return para; 
    } 
    
    
 }