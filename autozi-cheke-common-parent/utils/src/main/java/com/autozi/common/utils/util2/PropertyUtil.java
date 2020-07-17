package com.autozi.common.utils.util2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * 访问 properties 中的配置信息
 * <p/>
 * 调用方法: 
 * <pre>
 * String s = PropertyFileReader.getPropertyValue("MyFile.properties", "name", "default"); <p/>
 * 或 Properties p = PropertyFileReader.getProperties("MyFile.properties"); <p/>
 * </pre>
 * 
 * @author lihf
 */
public class PropertyUtil {
	// 创建对象ec
	static PropertyUtil ec;
	
	// 静态对象初始化[在其它对象之前]
	private static Hashtable<String, Object> register = new Hashtable<String, Object>();

	//不能实例化
	private PropertyUtil() {
		super();
	}

	/**
	 * 读取配置文件
	 * 
	 * @param java.lang.String
	 *            fileName 文件名
	 * @return Properties 
	 */
	public static Properties getProperties(String fileName) {// 传递配置文件路径
		InputStream is = null;// 定义输入流is
		Properties p = null;
		try {
			p = (Properties) register.get(fileName);// 将fileName存于一个HashTable
			/**
			 * 如果为空就尝试输入进文件
			 */
			if (p == null) {
				try {
					is = new FileInputStream(fileName);// 创建输入流
				} catch (Exception e) {
					if (fileName.startsWith("/"))
						// 用getResourceAsStream()方法用于定位并打开外部文件。
						is = PropertyUtil.class
								.getResourceAsStream(fileName);
					else
						is = PropertyUtil.class.getResourceAsStream("/"
								+ fileName);
				}
				p = new Properties();
				p.load(is);// 加载输入流
				register.put(fileName, p);// 将其存放于HashTable缓存
				is.close();// 关闭输入流
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return p;// 返回Properties对象
	}
	
	   /**
     * 读取配置文件
     * 
     * @param java.lang.String
     *            fileName 文件名
     * @return Properties 
     */
    public static Properties getProperties(String fileName,boolean iscache) {// 传递配置文件路径
        InputStream is = null;// 定义输入流is
        Properties p = null;
        try {
            if(iscache){
                p = (Properties) register.get(fileName);// 将fileName存于一个HashTable
            }
            /**
             * 如果为空就尝试输入进文件
             */
            if (p == null) {
                try {
                    is = new FileInputStream(fileName);// 创建输入流
                } catch (Exception e) {
                    if (fileName.startsWith("/"))
                        // 用getResourceAsStream()方法用于定位并打开外部文件。
                        is = PropertyUtil.class
                                .getResourceAsStream(fileName);
                    else
                        is = PropertyUtil.class.getResourceAsStream("/"
                                + fileName);
                }
                p = new Properties();
                p.load(is);// 加载输入流
                register.put(fileName, p);// 将其存放于HashTable缓存
                is.close();// 关闭输入流
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally{
        	if(is!=null){
        		try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return p;// 返回Properties对象
    }

	/**
	 * 读取配置文件中的字串属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static String getStrValue(String fileName, String strKey, String defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return (String) p.getProperty(strKey,defaultValue);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}		
	}
	
	/**
	 * 读取配置文件中的Integer属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static Integer getIntValue(String fileName, String strKey, Integer defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return  Integer.valueOf((String) p.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}
	
	/**
	 * 读取配置文件中的Long属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static Long getLongValue(String fileName, String strKey, Long defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return  Long.valueOf((String) p.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}
	/**
	 * 读取配置文件中的Double属性
	 * 
	 * @param fileName 属性文件名
	 * @param strKey Key
	 * @param defaultValue 缺省值
	 */
	public static Double getDoubleValue(String fileName, String strKey, Double defaultValue) {
		Properties p = getProperties(fileName);
		try {
			return  Double.valueOf((String) p.getProperty(strKey));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}
	
	   /**
     * 读取配置文件中的Integer属性
     * 
     * @param fileName 属性文件名
     * @param strKey Key
     * @param defaultValue 缺省值
     */
    public static Integer getPrintIntValue(String fileName, String strKey, Integer defaultValue) {
        Properties p = getProperties(fileName,false);
        try {
            return  Integer.valueOf((String) p.getProperty(strKey));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return defaultValue;
        }
    }
    
    /**
     * 读取配置文件中的字串属性
     * 
     * @param fileName 属性文件名
     * @param strKey Key
     * @param defaultValue 缺省值
     */
    public static String getPrintStrValue(String fileName, String strKey, String defaultValue) {
        Properties p = getProperties(fileName,false);
        try {
            return (String) p.getProperty(strKey,defaultValue);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return defaultValue;
        }       
    }
}
