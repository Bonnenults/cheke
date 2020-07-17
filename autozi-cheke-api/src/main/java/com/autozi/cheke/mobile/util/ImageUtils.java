package com.autozi.cheke.mobile.util;

import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import com.autozi.common.utils.util1.DateFormatter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class ImageUtils {

    private static final String IMAGE_PNG = "PNG";
    public static final String IMAGE_PNG_SUFFIX = ".png";
    
    
   static  java.util.List<String> commonImgType = new ArrayList<String>();
   static{
	   commonImgType.add("png");
	   commonImgType.add("jpg");
	   commonImgType.add("jpeg");
   }

    /**
     * 裁剪图片
     *
     * @param image  原图路径
     * @param dest   目标图路径
     * @param top    选择框的左边y坐标
     * @param left   选择框的左边x坐标
     * @param width  选择框宽度
     * @param height 选择框高度
     * @return
     * @throws IOException
     */
    public static boolean cut(File image, String dest, int top, int left, int width, int height) throws IOException {
        BufferedImage bi = (BufferedImage) ImageIO.read(image);
        height = Math.min(height, bi.getHeight());
        width = Math.min(width, bi.getWidth());
        if (height <= 0) {
            height = bi.getHeight();
        }
        if (width <= 0) {
            width = bi.getWidth();
        }
        top = Math.min(Math.max(0, top), bi.getHeight() - height);
        left = Math.min(Math.max(0, left), bi.getWidth() - width);
        BufferedImage bi_cropper = bi.getSubimage(left, top, width, height);
        //将切割的图的大小设置为 100X100
        Image cropImage = bi_cropper.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(cropImage, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        return ImageIO.write(tag, IMAGE_PNG, createParentFile(dest));
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


    public static boolean deleteImage(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }


    public static String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String newFileName = getNewImageTempName(fileName);
        String tempPath = ApplicationPropertiesHelper.getImgFilePath();
        if(!tempPath.endsWith("/")){
            tempPath+="/";
        }
        String serverPath = ApplicationPropertiesHelper.getImgServerUrl();
        if(!serverPath.endsWith("/")){
            serverPath+="/";
        }
        File target = createParentFile(tempPath + newFileName);
        try {
            file.transferTo(target);
            return serverPath + newFileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

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

    public static String storeImage(String tempImage){
        String serverPath =  ApplicationPropertiesHelper.getImgServerUrl();
        if(!serverPath.endsWith("/")){
            serverPath+="/";
        }
        String tempName = tempImage.replaceFirst(serverPath+"temp/", "");
        String storePath =  ApplicationPropertiesHelper.getImgFilePath();
        if(!storePath.endsWith("/")){
            storePath+="/";
        }
        String date = DateFormatter.formatToDate(new Date(),"yyyyMMdd");
        File storeFile = createParentFile(storePath+"upload/"+date+"/"+tempName);
        String tempPath =  ApplicationPropertiesHelper.getImgFilePath()+"temp/"+tempName;
        if(!tempPath.endsWith("/")){
            tempPath+="/";
        }
        OutputStream out = null;
        try{
            File imageFile = new File(tempPath);
            out = new FileOutputStream(storeFile);
            FileUtils.copyFile(imageFile,out);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverPath+"upload/"+date+"/"+tempName;
    }

}