package com.autozi.common.utils.util2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author haocheng
 *
 */
public abstract class EmailProperties {
	public static Properties prop=null;
	static {
		prop = new Properties();
		InputStream in = EmailProperties.class.getResourceAsStream("/email.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 私有构造方法，不需要创建对象
	 */
	private EmailProperties() {
	}

	/**
	 * 从application.properties取出key对应的字符串
	 * @param key
	 * @return 
	 */
	public static String getValue(String key) {
		return prop.getProperty(key);
	}
}
