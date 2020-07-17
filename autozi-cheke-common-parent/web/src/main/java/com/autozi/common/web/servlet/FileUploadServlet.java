/**
 * Author: Bob Chen
 */

package com.autozi.common.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * 上传文件
 *
 */
public class FileUploadServlet extends HttpServlet {  
	private static final long serialVersionUID = 4822016110250283248L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	String storeType = request.getParameter("storeType");
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        List<String> names = null;

        //If you want to upload files of other types, you need to modify following codes.
        if("csv".equals(storeType) || "thumb".equals(storeType) ||"files".equals(storeType)|| "img_thumb".equals(storeType) || "img".equals(storeType)){
        	names = readFiles(request, storeType);
        }else{
        	names = null;
        	System.out.println("-The storeType is not correct!(FileUploadServlet)-");
        }
		if (names != null) {
			//files为合同上传处的返回值，其他的可以在else中处理
			if("files".equals(storeType)){
				for (String name : names) {
					writer.write("<script language='javascript'>");
				    writer.write("window.parent.document.getElementById('msg').innerHTML='<font color=red>上传文件成功</font>';");
				    writer.write("window.parent.document.theForm.fileUrl.value='"+name+"';");
					writer.write("</script>");
				}
			}else{
				for (String name : names) {
					writer.write(name + ";");
				}
			}
		}
    }
    
	private List<String> readFiles(HttpServletRequest request, String storeType) {
        List<String> names = new ArrayList<String>();
        
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        StoreOrderType sot = new StoreOrderType();
        try {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());

            List<?> items = upload.parseRequest(request);
            Iterator<?> it = items.iterator();
            while (it.hasNext()) {
                FileItem item = (FileItem) it.next();
                String name = sot.StoreFile(storeType, item, today);
                if(name != null){
                	names.add(name);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return names;
    }
}
