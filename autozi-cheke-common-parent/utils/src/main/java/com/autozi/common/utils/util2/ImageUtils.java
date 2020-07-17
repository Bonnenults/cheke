package com.autozi.common.utils.util2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.multipart.MultipartFile;

import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import com.autozi.common.utils.util1.DateFormatter;

public class ImageUtils {

    public static final String IMAGE_PNG_SUFFIX = ".png";
    
    
    static  java.util.List<String> commonImgType = new java.util.ArrayList<String>();
    static{
 	   commonImgType.add("png");
 	   commonImgType.add("jpg");
 	   commonImgType.add("jpeg");
    }
    
    /**
     * 获取默认图片
     */
    public static String getGoodsImage(String imagePath) {
		return imagePath!=null && imagePath.trim()!=""? ApplicationPropertiesHelper.getImgServerUrl()+imagePath:"0";
	}

    /**
     * 是否为图片
     */
    public static boolean isImage(byte[] imageContent) {
        if (imageContent == null || imageContent.length == 0) {
            return false;
        }
        Image image = null;
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(imageContent);
            image = ImageIO.read(inputStream);
            if (image == null || image.getWidth(null) <= 0 || image.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean deleteImage(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
      * @Description: 上传图片
      * @user zhiyun.chen
      * @dateTime 2014-5-21下午04:41:24
     */
    public static String storeFiles2Img_thumb(InputStream in, String fileName, String rootPath,String filePath, int targetWidth, int targetHeight) throws Exception {
		String newImgUploadFileName;
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			newImgUploadFileName = System.currentTimeMillis() + "_userImage" + fileName.substring(index);
		} else {
			newImgUploadFileName = System.currentTimeMillis() + "_";
		}
		String thumbImageNamePath = null;
		BufferedImage image_thumb = ImageIO.read(in);
		thumbImageNamePath = filePath + "/" + newImgUploadFileName;
		thumbImageNamePath = thumbImageNamePath.replace("//", "/");
		Image image = image_thumb.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		
		ImageIO.write(tag, "PNG", createParentFile(rootPath+thumbImageNamePath));
		
		return thumbImageNamePath;
    }
    
    /**
	 * 创建根文件夹
	 */
	private static File createParentFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		return file;
	}

	public static String uploadImages(MultipartFile file, String filePath) {
		String fileName = file.getOriginalFilename();
        String newFileName = getNewImageTempName(fileName);
        String path = ApplicationPropertiesHelper.getImgFilePath() + filePath + newFileName;
        File target = createParentFile(path);
        try {
            file.transferTo(target);
            return filePath + newFileName;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
	}
	
	private static String getNewImageTempName(String fileName) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        //sb.append(DateFormatter.formatToDate(date, "yyyyMMdd")).append("/");
        sb.append(DateFormatter.formatToDate(date, "HHmmssS")).append("_");
        sb.append(RandomUtils.nextInt(900000000) + 100000000).append(".");
        String imgType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        if(!commonImgType.contains(imgType)){
        	imgType ="png";
        }
        sb.append(imgType);
        return sb.toString();
    }
}