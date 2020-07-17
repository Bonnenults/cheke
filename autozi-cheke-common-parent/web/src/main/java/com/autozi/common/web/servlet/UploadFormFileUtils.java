package com.autozi.common.web.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFormFileUtils {
	@SuppressWarnings("rawtypes")
	public static List<String> readFiles(HttpServletRequest request, String storeType) {
        List<String> names = new ArrayList<String>();
        
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        StoreOrderType sot = new StoreOrderType();
        try {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());

            List items = upload.parseRequest(request);
            Iterator it = items.iterator();
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
