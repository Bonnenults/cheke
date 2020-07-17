package com.autozi.common.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import com.autozi.common.utils.util1.ImageUploadUtils;

public class imageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 2924104012311582348L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("text/plain;charset=UTF-8");
    	response.getContentType();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String message = readFiles(request);
        JSONObject jsonResult =new JSONObject();
        if(message!=null){
        	jsonResult.put("message", message);
        }else{
        	jsonResult.put("message", "success");
        }
        writer.write(jsonResult.toString());
        writer.close();
   }
    
	private String readFiles(HttpServletRequest request) {
		
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        Integer fileSize = ApplicationPropertiesHelper.getImageFileSize();
        diskFileItemFactory.setSizeThreshold(fileSize);

        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setSizeMax(fileSize*6);
        servletFileUpload.setFileSizeMax(fileSize);

 		String message= null;
 		Long tradeGoodsId=null;
        try {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            List<?> items = servletFileUpload.parseRequest(request);
            
            Iterator<?> it = items.iterator();
            while (it.hasNext()) {
            	FileItem item = (FileItem) it.next();
				if (item.isFormField()&& "tradeGoodsId".equals(item.getFieldName())) {
					String tradeGoodsIdStr = item.getString();
					tradeGoodsId = Long.parseLong(tradeGoodsIdStr.replaceAll(",", "")) ;
				} 
            }		
            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                if(!item.isFormField()&&item.getSize()>0) {
                    ImageUploadUtils.saveFiles(tradeGoodsId, item, today, i);
                }
            }
        }catch (FileSizeLimitExceededException e) {
        	message="文件大小超出限制,请重新上传文件";
            e.printStackTrace();
        }
        catch (Exception e) {
        	message="上传文件出现错误,请重新上传文件";
            e.printStackTrace();
        }
        return message;
    }

}
