package com.autozi.common.utils.util1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.multipart.MultipartFile;

import com.autozi.common.core.utils.ApplicationContextProvider;
import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import com.autozi.common.utils.file.DisposePictures;
import com.autozi.common.utils.file.FileManagerFactory;
import com.autozi.common.utils.file.IFile;
import com.autozi.common.utils.util2.ImgUtil;

public class ImageUploadUtils{
    private static final String IMAGE_PNG = "PNG";
    public static final String IMAGE_PNG_SUFFIX = ".png";
	public static Boolean saveFiles(Long tradeGoodsId, FileItem item, String today, int rowNum){
        ISaveImage tradeGoodsManager = ApplicationContextProvider.getBean(ISaveImage.class);
        String[] fileNamePaths = storeFiles2Img_thumb(item, today, tradeGoodsManager.getImageFolderNumber());
		tradeGoodsManager.saveImageFile(tradeGoodsId, fileNamePaths, rowNum,2l);
		return null;
	}
	public static String[] storeFiles2Img_thumb(FileItem item, String today, String folderNumber) {
		String[] fileNames = new String[5];
		if (item.isFormField() == false) {  
        	String randomImageName = getFileName(item);
            byte[] content = item.get();
            int[] changeSizeArray ={80,130,230,300,960};
            for(int i=0 ;i<changeSizeArray.length ;i++){
            	InputStream in=null;
            	ByteArrayOutputStream byteOut=null;
				try {
					in=new ByteArrayInputStream(content);
					BufferedImage image_thumb = ImageIO.read(in);				
					DisposePictures dp_thumb = new DisposePictures();
					image_thumb = dp_thumb.resize(image_thumb, changeSizeArray[i], changeSizeArray[i]);
					byteOut = new ByteArrayOutputStream(); 
					ImageIO.write(image_thumb, randomImageName.substring(randomImageName.lastIndexOf(".")+1), byteOut);
					
					String thumbImageNamePath  = "/goods" + folderNumber + "/" + today+"/thumb_"+changeSizeArray[i]+"/"+ "thumb_"+changeSizeArray[i]+"_"+changeSizeArray[i]+randomImageName;
	                IFile file = FileManagerFactory.getImgFileManager().createFile(thumbImageNamePath);
	                file.saveContent(byteOut.toByteArray());
	                
	                fileNames[i]=thumbImageNamePath;
				 }catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				 }finally{
					try{
		    		   if(in!=null) in.close();  
		    		   if(byteOut!=null) byteOut.close(); 
			        }catch(Exception e) {
			           e.printStackTrace();
			           throw new RuntimeException(e);
			        }
				}
            } 
			return fileNames;
        }
		return null;
	}
	
	private static String getFileName(FileItem item) {
	    String f = item.getName();
	    f = f.replace('\\', '/');
	    String s = f.substring(f.lastIndexOf('/')+1);
	    if (s.indexOf(".") > 0) {
	        return UUID.randomUUID().toString() + s.substring(s.lastIndexOf("."));
	    }
	    return UUID.randomUUID().toString();
	}
	public interface ISaveImage{
		void saveImageFile(Long goodsId, String[] fileNamePaths, int rowNum,Long userId);
        String getImageFolderNumber();
	}
    
    /**
    	 * 生成图片
    	 *
    	 *
    	 * @param dest
    	 *            目标图路径
         * @param imgStr
    	 *            图片文字
    	 * @param strLength
    	 *            图片宽度
    	 * @param height
    	 *            图片高度
    	 * @return
    	 * @throws java.io.IOException
    	 */
    	public static boolean generate(String dest,String imgStr, int strLength, int height) throws IOException {
    		// 定义图像buffer
    		BufferedImage buffImg = new BufferedImage(strLength, height,
    				BufferedImage.TYPE_4BYTE_ABGR);
    		Graphics2D g = buffImg.createGraphics();
    		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    		// 设定图像背景色
    		g.setColor(new Color(0xFFFFFF));
    		g.fillRect(0, 0, strLength, height);

    		// 创建字体，字体的大小应该根据图片的高度来定。
    		Font font = new Font("宋体", Font.BOLD, height);
    		// 设置字体。
    		g.setColor(Color.decode("0xBE0000"));
    		g.setFont(font);
    		// 画边框。
    		g.drawString(imgStr, 0, height-2);
    		return ImageIO.write(buffImg, IMAGE_PNG, createParentFile(dest));
    	}

	/**
	 * 创建根文件夹
	 */
	private static File createParentFile(String path) throws IOException {
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		return file;
	}

	private static String getNewImageTempName(String fileName) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		// sb.append(DateFormatter.formatToDate(date, "yyyyMMdd")).append("/");
		sb.append(DateFormatter.formatToDate(date, "HHmmssS")).append("_");
		sb.append(RandomUtils.nextInt(900000000) + 100000000).append(".");
		sb.append(fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length()).toLowerCase());
		return sb.toString();
	}
	
	/**
	  * @Description: 上传图片，并返回图片相对路径（包含文件名）--新增、修改活动上传头部图片、背景图片
	  * @param filePath 需要保存的相对路径（不包含文件名）
	  * @user zhiyun.chen
	  * @dateTime 2013-10-31上午10:05:30
	 */
	public static String uploadImage(MultipartFile file,String filePath) throws IOException {
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
	
	/**
	 * @description : 上传图片 , 并给图片加上水印 
	 * @param MultipartFile 待上传图片
	 * @param filePath 上传到图片服务器上的路径
	 * @param appendImagePath 水印图片的路径
	 * @return 上传到图片服务器上的路径
	 * @throws IOException
	 * @author liwensuo
	 * @date 2015-01-28 
	 */
	public static String uploadImageOfalpha(MultipartFile imgFile , String filePath , String appendImagePath ) throws IOException{
		float alpha = 1F;
        int x = 0;
        int y = 0;
        String imageFormat=IMAGE_PNG_SUFFIX ;
        String fileName = imgFile.getOriginalFilename();
		String newFileName = getNewImageTempName(fileName);
		
		String toPath = ApplicationPropertiesHelper.getImgFilePath() + filePath + newFileName;
		imageFormat = fileName.substring(fileName.lastIndexOf(".")+1 , fileName.length()) ;
		try{
			
		    ImgUtil.alphaImage2Image(imgFile.getInputStream() ,  appendImagePath,  
	                                 alpha, x, y, imageFormat, toPath) ;
		    return  filePath + newFileName;
		}catch(IOException e){
			e.printStackTrace() ;
		}
		
		return  null ;
		
	}
	
	
	/**
	  * @Description: 删除文件
	  * @param filePath 全路径
	  * @user zhiyun.chen
	  * @dateTime 2013-11-29下午05:08:43
	 */
	public static boolean delFile(String filePath){
		File file = new File(filePath);
    	return !file.exists() || file.delete();
	}
	
	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
			boolean overlay) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			// System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			// System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在，而且复制时允许覆盖。
			if (overlay) {
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				// System.out.println("目标文件已存在，准备删除它！");
				if (!delFile(destFileName)) {
					// System.out.println("复制文件失败：删除目标文件" + destFileName +
					// "失败！");
					return false;
				}
			} else {
				// System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				// System.out.println("目标文件所在的目录不存在，准备创建它！");
				if (!destFile.getParentFile().mkdirs()) {
					// System.out.println("复制文件失败：创建目标文件所在的目录失败！");
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
			// System.out.println("复制单个文件" + srcFileName + "至" + destFileName +
			// "成功！");
			return true;
		} catch (Exception e) {
			// System.out.println("复制文件失败：" + e.getMessage());
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
	
	public static String getName(String imagePath){
		return imagePath.substring(imagePath.lastIndexOf("/") + 1);
	}
}