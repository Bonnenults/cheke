package com.autozi.common.utils.util1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.xwork.StringUtils;

import com.autozi.common.core.utils.ApplicationPropertiesHelper;


/**
 * 富文本工具类
 * @user zhiyun.chen
 *
 */
public class HtmlUtils {
	
	/**
     * @param content 要保存的内容
     * @param customerDirPath 自定义路径
     * @param fileName 文件名
     * @return 返回自定义路径+文件名称   字符串
     * */
    public static String createHtmlFile(String content, String customerDirPath, String fileName) throws IOException {
    	String rootPath = ApplicationPropertiesHelper.getHtmlFilePath();
    	String fullPath = rootPath + customerDirPath;
    	createDirs(fullPath);
        createHtmlFile(content, fullPath+fileName);
        return customerDirPath + fileName;
    }
    
    /**
     * 读取html文件
     *
     * @param introduction
     * @return String
     * @throws Exception
     */
    public static String readHtml(String introduction) throws Exception {
        String filePath = ApplicationPropertiesHelper.getHtmlFilePath();
       	return readHtml(filePath , "/" + introduction);
    }
    /**
     * 
      * @Description: 删除html文件
      * @param customerDirPath
      * @param fileName
      * @dateTime 2012-11-2下午02:39:50
      * @throws
     */
    public static boolean deleteFile(String customerDirPath, String fileName) {
    	String rootPath = ApplicationPropertiesHelper.getHtmlFilePath();
    	String fullPath = rootPath + customerDirPath;
    	return delFile(fullPath + fileName);
    }
	
	/**
     * 判断指定文件夹是否存在，不存在时创建之
     *
     * @param path
     */
    public static void createDirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    /**
     * @param content 要保存的内容
     * @param fileFullPath 文件全路径
     * @return 返回自定义路径+文件名称   字符串
     * */
    public static String createHtmlFile(String content, String fileFullPath) throws IOException {
        OutputStreamWriter writer = null;
        try {
            FileOutputStream outputFile = new FileOutputStream(fileFullPath);
            writer = new OutputStreamWriter(outputFile, "utf-8");
            writer.write(content, 0, content.length());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return fileFullPath;
    }
    
    /**
     * 读取html文件
     *
     * @param filePath
     * @return String
     * @throws Exception
     */
    public static String readHtml(String filePath, String name) throws Exception {
        FileInputStream fileInputStream = null;
        try {
        	if(StringUtils.isBlank(name)){
        		return  "" ;
        	}
            fileInputStream = new FileInputStream(filePath  + name);
            return IOUtils.toString(fileInputStream, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "系统找不到指定的文件。";
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
	
	/**
     * 删除掉文件
     * @param filePath
     */
    public static boolean delFile(String filePath){
    	
    	File file = new File(filePath);
        
    	return !file.exists() || file.delete();
    }
    
    /**
	 * 复制单个文件
	 * @param srcFileName  待复制的文件名
	 * @param destFileName 目标文件名
	 * @param overlay  如果目标文件存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			//System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			//System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在，而且复制时允许覆盖。
			if (overlay) {
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				//System.out.println("目标文件已存在，准备删除它！");
				 if(!delFile(destFileName)){
					 //System.out.println("复制文件失败：删除目标文件" + destFileName + "失败！");
					 return false;
				 }
			} else {
				//System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				//System.out.println("目标文件所在的目录不存在，准备创建它！");
				if (!destFile.getParentFile().mkdirs()) {
					//System.out.println("复制文件失败：创建目标文件所在的目录失败！");
					return false;
				}
			}
		}
		// 准备复制文件
		int byteread = 0;// 读取的位数
		InputStream in = null;
		OutputStream out = null;
		try {
			// 打开原文件
			in = new FileInputStream(srcFile);
			// 打开连接到目标文件的输出流
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			// 一次读取1024个字节，当byteread为-1时表示文件已经读完
			while ((byteread = in.read(buffer)) != -1) {
				// 将读取的字节写入输出流
				out.write(buffer, 0, byteread);
			}
			//System.out.println("复制单个文件" + srcFileName + "至" + destFileName + "成功！");
			return true;
		} catch (Exception e) {
			//System.out.println("复制文件失败：" + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			// 关闭输入输出流，注意先关闭输出流，再关闭输入流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * @Description: 获取富文本中的图片名称集合
	  * @param htmlContent 富文本内容
	  * @param imgPath 富文本中图片的相对路径(如：/goods/images/temp/)
	  * @user zhiyun.chen
	  * @dateTime 2013-10-30下午03:34:14
	 */
	private static ArrayList<String> getImageFileName(String htmlContent, String imgPath){
		ArrayList<String> list = new ArrayList<String>();
		String regx = "<img.*?/>";
      Pattern p = Pattern.compile(regx);
      Matcher m = p.matcher(htmlContent);
      String regx1 = "(?<=src=\").*(?=\")";
      Pattern p1 = Pattern.compile(regx1);
      //首先找到img标签，然后在图片标签里找src属性
      while(m.find()){
          String child = m.group();
          Matcher m1 = p1.matcher(child);
          if(m1.find()){
          	String mGroup = m1.group();
          	//如果包含上传图片的临时路径，则把图片名称加入集合
          	if( StringUtils.contains(mGroup, ApplicationPropertiesHelper.getImgServerUrl() + imgPath)) {
          		String imageFileName = StringUtils.substringBefore(mGroup, "\"");
	            	imageFileName = imageFileName.replace(ApplicationPropertiesHelper.getImgServerUrl() + imgPath,"");
	                System.out.println(imageFileName);
	                list.add(imageFileName);
          	}
          }
      }
      return list;
	}
	
	/**
	  * @Description: 把富文本中上传的临时图片复制到永久路径，同时替换富文本内容中的临时图片路径，并返回替换后的富文本内容
	  * @param htmlContent 富文本内容
	  * @param oldFilePath 临时路径（如：/goods/images/temp/）
	  * @param newFilePath 永久路径（如：/goods/images/8888/,8888为商品ID）
	  * @user zhiyun.chen
	  * @dateTime 2013-10-30下午03:40:11
	 */
	public static String replaceImageUrlFromHtml(String htmlContent,String oldFilePath, String newFilePath) {
		String newHtmlContent = htmlContent;
		ArrayList<String> imageStrList = getImageFileName(htmlContent,oldFilePath);
		if (null != imageStrList && imageStrList.size() > 0) {
			String rootPath = ApplicationPropertiesHelper.getImgFilePath();// 上传服务器绝对路径
			for (String imageStr : imageStrList) {
				// 复制图片到新路径
				boolean bool = copyFile(rootPath + oldFilePath + imageStr, rootPath + newFilePath+ imageStr, true);
				if (bool) {// 如果复制成功，则删除原图片
					delFile(rootPath + oldFilePath + imageStr);
				}
			}
			newHtmlContent = htmlContent.replace(oldFilePath, newFilePath);
		}
		return newHtmlContent;
	}
}
