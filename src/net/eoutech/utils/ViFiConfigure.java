package net.eoutech.utils;

import java.io.IOException;
import java.util.Properties;

public class ViFiConfigure 
{
	private Properties properties;
	private static ViFiConfigure config = null;
	
	public synchronized static ViFiConfigure getInstance() 
	{
		return (config == null ? new ViFiConfigure() : config); 
	}
	
	
	public ViFiConfigure()
	{
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getStrValue(String key) 
	{
		return  properties.containsKey(key) ? properties.getProperty(key) : "";
	}
	
	public int getIntValue(String key)
	{
		if (properties.containsKey(key)) {
			String val = properties.getProperty(key);
			try {
				return Integer.parseInt(val);
			}catch (Exception e) {
				return -1;
			}
		}else {
			return -2;
		}
	}
	
	public boolean checkIp(String ip){
		String ips = getStrValue("sp_remote_request_IP");
		if(!ips.isEmpty()){
			String[] ipArr = ips.split("\\|");
			for(String s:ipArr){
				if(s.equals(ip)){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	
	public boolean isRunLoastest()
	{
		return getStrValue( "sys.modal.run" ).equals( "T" );
	}
	
	
}
