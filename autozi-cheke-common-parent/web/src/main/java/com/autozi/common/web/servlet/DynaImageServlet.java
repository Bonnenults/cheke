package com.autozi.common.web.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autozi.common.utils.file.FileManagerFactory;
import com.autozi.common.utils.file.IFile;
import com.autozi.common.utils.file.IFileManager;


/**
 * 上传图片
 *
 */
public class DynaImageServlet extends HttpServlet {
	private static final long serialVersionUID = -256018519873220260L;

	public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        writeImage(response, fileName);
    }

    private void writeImage(HttpServletResponse response, String fileName) throws IOException {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        response.setHeader("Content-Type", "image/" + type);

        IFileManager fileManager = FileManagerFactory.getTempFileManager();
        IFile file = fileManager.getFile(fileName);

        if (!file.exists()) {
            InputStream is = this.getClass().getResourceAsStream("noPicture.gif");
            int leng = is.available();
            BufferedInputStream buff = new BufferedInputStream(is);
            byte[] mapObj = new byte[leng];
            buff.read(mapObj, 0, leng);
            response.getOutputStream().write(mapObj);
            is.close();
        } else {
            byte[] content = file.readContent();
            response.getOutputStream().write(content);
        }
    }
}
