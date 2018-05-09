package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Singleton {
	
	private static Singleton singleton;
	private static Properties properties;
	private Singleton(){
		//��ȡ�����ļ��Ĳ���
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
	 * ��ʼ��Singleton��ǰ��
	 * @return
	 */
	public static Singleton getInstance(){
		return SingletonHelper.INSTANCE;
	}
	/**
	 * ��������˾�̬�ڲ���ſ���ʹ��
	 * @return
	 */
	public static Singleton test(){
		return singleton;
	}
	/**
	 * ��ȡ����Դ�ļ��е�����
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return properties.getProperty(key);
	}
	
}
