package com.autozi.common.utils.chart;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.springframework.web.multipart.MultipartFile;

import com.autozi.common.utils.file.FileUtil;
import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.util2.DateUtils;
import com.autozi.common.utils.util2.GB2Alpha;
import com.autozi.common.utils.util2.ImgUtil;

public class CarUtil {

	/*------原图缩略成60*60大小-------*/
	private static int LOGO_IMG_WIDTH = 60; 
	private static int LOGO_IMG_HEIGHT = 60;
	
	
	
	public static String getFirstChar(String str) {
		if (org.apache.commons.lang.StringUtils.isBlank(str))
			return null;
		if (str.length() == 1)
			return str;
		if (GB2Alpha.checkInputIsChinese(str)) {
			char word = str.charAt(0);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			return String.valueOf(pinyinArray[0].charAt(0));
		} else {
			return String.valueOf(str.charAt(0));
		}
	}

	public static Boolean isImg(String fileName) {
		String lastName = fileName.substring(fileName.lastIndexOf("."));
		if (lastName.equals(".png") || lastName.equals(".gif")
				|| lastName.equals(".jpg")) {
			return true;
		}
		return false;
	}

	public static String uploadLogoImg(Long id,MultipartFile file) {
		if(!file.isEmpty() && isImg(file.getOriginalFilename()) ){
			try {
				String src = file.getOriginalFilename();
				String fileName = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")+"-"+id+src.substring(src.lastIndexOf("."));
				
				return copyFile(file.getInputStream(),fileName);
				//return fileName;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 写文件到本地
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String fileName) {
		String srcPath = ApplicationProperties.getValue("img.file.path")+"/carlogo/";
		FileUtil.createDirs(srcPath);
		String p = srcPath + fileName;
		
		FileOutputStream fs = null;
        String finalSrc="";
		try {
			fs = new FileOutputStream(p);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = in.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			finalSrc= cutImg(p,fileName);
            return finalSrc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null!=fs){
                    fs.close();
                    fs = null;
                }
                if(null != in){
                    in.close();
                    in = null;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private static String cutImg(String srcPath,String fileName){
		String tarPath = ApplicationProperties.getValue("img.file.path");
		tarPath = tarPath+"/carlogo/small";
		FileUtil.createDirs(tarPath);
		String tarImg = "small-"+fileName;
		try {
			ImgUtil.resizeImage(srcPath, tarPath+"/"+tarImg, LOGO_IMG_WIDTH, LOGO_IMG_HEIGHT);
			return "carlogo/small/"+tarImg;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}