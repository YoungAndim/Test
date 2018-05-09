package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Singleton {
	
	private static Singleton singleton;
	private static Properties properties;
	private Singleton(){
		//读取配置文件的操作
		String configFile = "database.properties";
		properties = new Properties();
		InputStream is = 
				Singleton.class.getClassLoader().getResourceAsStream(configFile);
		try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class SingletonHelper{
		private static final Singleton INSTANCE = new Singleton();
	}
	
	/**
	 * 初始化Singleton当前类
	 * @return
	 */
	public static Singleton getInstance(){
		return SingletonHelper.INSTANCE;
	}
	/**
	 * 必须调用了静态内部类才可以使用
	 * @return
	 */
	public static Singleton test(){
		return singleton;
	}
	/**
	 * 提取数据源文件中的数据
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return properties.getProperty(key);
	}
	
}
