package com.autozi.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationPropertiesHelper {
    private static Logger logger = LoggerFactory.getLogger(ApplicationPropertiesHelper.class);
    private static String imageFilePath;
    private static String wtriteTextPath;
    private static String attachmentFilePath;
    private static String contractDocPath;
    private static String tempFilePath;
    private static String imageServerUrl;
    private static String adImageServerUrl;
    private static String imageFileDefault;
    private static Integer imageFileSize;
	private static String orderAttachmentContentTypes;
    //商品图片信息
    private static String goodsImageServerUrl;
    private static String goodsImageFilePath;
    //完善商品信息路径
    private static String goodsInfoFilePath;
    //B2C文件路径
    private static String b2cGoodsInfoFilePath;
    //品牌描述文件路径
    private static String htmlFilePath;


    public static String getImgFilePath() {
        String imgPath = imageFilePath;
        if (StringUtils.isEmpty(imgPath)) {
            logger.error("application.properties file doesn't define the key of 'img.file.path' or the value is empty.");
            return null;
        }
        return imgPath;
    }
    public static String getWtriteTextPath() {
    	String textPath = wtriteTextPath;
    	if (StringUtils.isEmpty(textPath)) {
    		logger.error("application.properties file doesn't define the key of 'write.text.path' or the value is empty.");
    		return null;
    	}
    	return textPath;
    }
    

    
    public static String getAttDocPath() {
        String attPath = attachmentFilePath;
        if (StringUtils.isEmpty(attPath)) {
            logger.error("application.properties file doesn't define the key of 'attachment.file.path' or the value is empty.");
            return null;
        }
        return attPath;
    }
    
    public static String getContractFilePath() {
        String conPath = contractDocPath;
        if (StringUtils.isEmpty(conPath)) {
            logger.error("application.properties file doesn't define the key of 'contract.doc.path' or the value is empty.");
            return null;
        }
        return conPath;
    }
    
    public static String getTempFilePath() {
        String tempPath = tempFilePath;
        if (StringUtils.isEmpty(tempPath)) {
            logger.error("application.properties file doesn't define the key of 'temp.file.path' or the value is empty.");
            return null;
        }
        return tempPath;
    }
    
    /**
     * URL format: [img.server.url]/ (e.g. http://domain_name:port/context/)
     * 
     * @return image server URL
     */
    public static String getImgServerUrl() {
        String url = imageServerUrl;
        if (StringUtils.isEmpty(url)) {
            logger.error("application.properties file doesn't define the key of 'img.server.url' or the value is empty.");
            return null;
        }
        return url.endsWith("/") ? url : url + "/";
    }

    /**
     * URL format: [adImg.server.url]/ (e.g. http://domain_name:port/context/)
     *
     * @return adImage server URL
     */
    public static String getAdImageServerUrl() {
        String url = adImageServerUrl;
        if (StringUtils.isEmpty(url)) {
            logger.error("application.properties file doesn't define the key of 'adImg.server.url' or the value is empty.");
            return null;
        }
        return url.endsWith("/") ? url : url + "/";
    }
    
    /**
     * URL format: [img.server.url]/ (e.g. http://domain_name:port/context/)
     * 
     * @return image server URL
     */
    public static String getImgDefaultFileName() {
        String fileName = imageFileDefault;
        if (StringUtils.isEmpty(fileName)) {
            logger.error("application.properties file doesn't define the key of 'img.file.default' or the value is empty.");
            return null;
        }
        return fileName;
    }
    
    public static String getOrderAttachmentContentTypes(){
	        if (StringUtils.isEmpty(orderAttachmentContentTypes)) {
	            logger.error("application.properties file doesn't define the key of 'order.attachment.contentTypes' or the value is empty.");
	            return null;
	        }
	        return orderAttachmentContentTypes;
    }

	public void setImageFilePath(String imageFilePath) {
		ApplicationPropertiesHelper.imageFilePath = imageFilePath;
	}

	public void setAttachmentFilePath(String attachmentFilePath) {
		ApplicationPropertiesHelper.attachmentFilePath = attachmentFilePath;
	}

	public void setContractDocPath(String contractDocPath) {
		ApplicationPropertiesHelper.contractDocPath = contractDocPath;
	}

	public static void setWtriteTextPath(String wtriteTextPath) {
		ApplicationPropertiesHelper.wtriteTextPath = wtriteTextPath;
	}
	public void setTempFilePath(String tempFilePath) {
		ApplicationPropertiesHelper.tempFilePath = tempFilePath;
	}

	public void setImageServerUrl(String imageServerUrl) {
        ApplicationPropertiesHelper.imageServerUrl = imageServerUrl;
    }

    public void setAdImageServerUrl(String adImageServerUrl) {
        ApplicationPropertiesHelper.adImageServerUrl = adImageServerUrl;
    }

	public void setImageFileDefault(String imageFileDefault) {
		ApplicationPropertiesHelper.imageFileDefault = imageFileDefault;
	}
	
	public void setOrderAttachmentContentTypes(
			String orderAttachmentContentTypes) {
		ApplicationPropertiesHelper.orderAttachmentContentTypes = orderAttachmentContentTypes;
	}

	public static Integer getImageFileSize() {
		return imageFileSize;
	}
	public static Integer getImageFileSizeByK() {
		return imageFileSize/1024;
	}
	public void setImageFileSize(String imageFileSize) {
		try{
			ApplicationPropertiesHelper.imageFileSize = Integer.parseInt(imageFileSize);
		}catch(Exception e){
			e.printStackTrace();
			ApplicationPropertiesHelper.imageFileSize=1024*512;
		}
	}

     public static String getGoodsImgFilePath() {
        String imgPath = goodsImageFilePath;
        if (StringUtils.isEmpty(imgPath)) {
            logger.error("application.properties file doesn't define the key of 'goods.img.file.path' or the value is empty.");
            return null;
        }
        return imgPath;
    }

     public static String getGoodsImgServerUrl() {
        String url = goodsImageServerUrl;
        if (StringUtils.isEmpty(url)) {
            logger.error("application.properties file doesn't define the key of 'goods.img.server.url' or the value is empty.");
            return null;
        }
        return url.endsWith("/") ? url : url + "/";
    }

    public void setGoodsImageServerUrl(String goodsImageServerUrl) {
        ApplicationPropertiesHelper.goodsImageServerUrl = goodsImageServerUrl;
    }

    public void setGoodsImageFilePath(String goodsImageFilePath) {
        ApplicationPropertiesHelper.goodsImageFilePath = goodsImageFilePath;
    }

    public static String getGoodsInfoFilePath() {
        String infoPath = goodsInfoFilePath;
        if (StringUtils.isEmpty(infoPath)) {
            logger.error("application.properties file doesn't define the key of 'goods.info.file.path' or the value is empty.");
            return null;
        }
        return infoPath;
    }

     public void setGoodsInfoFilePath(String goodsInfoFilePath) {
         ApplicationPropertiesHelper.goodsInfoFilePath = goodsInfoFilePath;
     }

    public void setHtmlFilePath(String htmlFilePath) {
        ApplicationPropertiesHelper.htmlFilePath = htmlFilePath;
    }

    public static String getHtmlFilePath() {
        String infoPath = htmlFilePath;
        if (StringUtils.isEmpty(infoPath)) {
            logger.error("application.properties file doesn't define the key of 'html.file.path' or the value is empty.");
            return null;
        }
        return infoPath;
    }
    
	public static String getB2cGoodsInfoFilePath() {  
        String infoPath = b2cGoodsInfoFilePath;
        if (StringUtils.isEmpty(infoPath)) {
            logger.error("application.properties file doesn't define the key of 'b2c.goods.info.file.path' or the value is empty.");
            return null;
        }
        return infoPath;
    }

     public void setB2cGoodsInfoFilePath(String goodsInfoFilePath) {
        ApplicationPropertiesHelper.b2cGoodsInfoFilePath = goodsInfoFilePath;
    }

}
