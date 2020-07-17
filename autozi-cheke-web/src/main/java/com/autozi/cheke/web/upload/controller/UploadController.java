package com.autozi.cheke.web.upload.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.upload.util.ImageUtils;
import com.autozi.cheke.web.upload.util.VideoUtils;
import com.autozi.common.utils.o2o.RenderUtils;
import com.autozi.common.utils.util1.StringUtils;
import com.qiniu.api.auth.AuthException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/upload")
@Controller
public class UploadController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String PREFIX_VIDEO="video/";

    @RequestMapping(value = "/uploadTempImg.action")
    public void uploadTempImg(String id,Long fileSizeLimit,HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile image = multipartRequest.getFile(id);

        try {
            if (!ImageUtils.isImage(image.getBytes())) {
                RenderUtils.renderHtml(response, "您上传的不是图片。");
                return;
            }
        } catch (IOException e) {
            RenderUtils.renderHtml(response, "服务器连接错误。");
            return;
        }
        if (image.getSize() / 1024 > fileSizeLimit.longValue()) {
            RenderUtils.renderHtml(response, "您上传的图片大小超过" + fileSizeLimit + "kb");
            return;
        }
        String fileSummName = ImageUtils.uploadToTemp(image);
//        RenderUtils.renderText(response, fileSummName);
//        responseJson(response,"200",fileSummName);
        response(response,fileSummName);
    }


  /*  *//**
     * 商品富文本中的图片上传 KindEditor
     * @param response
     * @param request
     * @throws Exception
     * @author zhaicl
     * @date 2016年10月12日 上午9:08:14
     *//*
    @RequestMapping("/extImageKindEditor.action")
    public void extImageKindEditor(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mureq.getFileMap();
        if (fileMap == null || fileMap.size() == 0) {
            result.put("error", 1);
            result.put("message", "请选择文件");
            out.println(result.toString());
            return;
        }
        for (Map.Entry entity : fileMap.entrySet()) {
            MultipartFile file = (MultipartFile) entity.getValue();// 获取上传文件对象
            *//*try {
                if (!ImageUtils.isImage(file.getBytes())) {
                    result.put("error", 1);
                    result.put("message", "您上传的不是图片。");
                    out.println(result.toString());
                    return;
                }
            } catch (IOException e) {
                result.put("error", 1);
                result.put("message", "服务器连接错误");
                out.println(result.toString());
                return;
            }
            if (file.getSize() > 1024 * 1024 * 2) {
                result.put("error", 1);
                result.put("message", "您上传的图片大小超过2M。");
                out.println(result.toString());
                return;
            }*//*
        }
        for (Map.Entry entity : fileMap.entrySet()) {
            MultipartFile file = (MultipartFile) entity.getValue();// 获取上传文件对象
            String filePath = "/jyj/goodsDetail/" + new Date().getYear() + "-" + new Date().getMonth() + "/";//上传到服务器的图片路径
            String fileSummName =  ImageUtils.uploadToTemp(file);
            result.put("error", 0);
            result.put("url", fileSummName);
            out.println(result.toString());
        }
    }*/

    /**
     * 商品富文本中的图片上传 KindEditor
     * @param response
     * @param request
     * @throws Exception
     * @author zhaicl
     * @date 2016年10月12日 上午9:08:14
     */
    @RequestMapping("/extImageKindEditor.action")
    public void extImageKindEditor(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mureq.getFileMap();
        if (fileMap == null || fileMap.size() == 0) {
            result.put("error", 1);
            result.put("message", "请选择文件");
            out.println(result.toString());
            return;
        }
        for (Map.Entry entity : fileMap.entrySet()) {
            MultipartFile file = (MultipartFile) entity.getValue();// 获取上传文件对象
            try {

                if (ImageUtils.isImage(file.getBytes())) {
                    if (file.getSize() > 1024 * 1024 * 2) {
                        result.put("error", 1);
                        result.put("message", "您上传的图片大小超过2M。");
                        out.println(result.toString());
                        return;
                    }
                }
            } catch (IOException e) {
                result.put("error", 1);
                result.put("message", "服务器连接错误");
                out.println(result.toString());
                return;
            }

        }
        for (Map.Entry entity : fileMap.entrySet()) {
            MultipartFile file = (MultipartFile) entity.getValue();// 获取上传文件对象
//            String filePath = "/jyj/goodsDetail/" + new Date().getYear() + "-" + new Date().getMonth() + "/";//上传到服务器的图片路径
            String fileSummName =  ImageUtils.uploadToTemp(file);
            result.put("error", 0);
            result.put("url", fileSummName);
            out.println(result.toString());
        }
    }

    /**
     *
     * @author mingxin li
     * @data 2018/5/14
     *
     */
    @RequestMapping("/qiniuUpload.action")
    public void upload(HttpServletResponse response, HttpServletRequest request) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        JSONObject result = new JSONObject();
        PrintWriter out = response.getWriter();
        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mureq.getFileMap();
        if (fileMap == null || fileMap.size() == 0) {
            result.put("error", 1);
            result.put("message", "请选择文件");
            out.println(result.toString());
            return;
        }

        for (Map.Entry entity : fileMap.entrySet()) {
            MultipartFile file = (MultipartFile) entity.getValue();// 获取上传文件对象

            /*if(!isVedioFile(file.getOriginalFilename())){
                result.put("error", 1);
                result.put("message", "您上传的不是视频文件！");
                out.println(result.toString());
                return;
            }
*/
            if (file.getSize() > 1024 * 1024 * 1000) {
                    result.put("error", 1);
                    result.put("message", "您上传的视频大小超过1000M。");
                    out.println(result.toString());
                    return;
                }

        }
        for (Map.Entry entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = (MultipartFile) entity.getValue();
            String resultJson = null;
            try {
                resultJson = VideoUtils.uploadFile(file);
                result.put("error", 0);
                result.put("url", resultJson);
                out.println(result.toString());
            } catch (AuthException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Get the Mime Type from a File
     * @param fileName 文件名
     * @return 返回MIME类型
     * thx https://www.oschina.net/question/571282_223549
     * add by fengwenhua 2017年5月3日09:55:01
     */
    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }

    /**
     * 根据文件后缀名判断 文件是否是视频文件
     * @param fileName 文件名
     * @return 是否是视频文件
     */
    public static boolean isVedioFile(String fileName){
        String mimeType = getMimeType(fileName);
        if (!StringUtils.isEmpty(fileName)&&mimeType.contains(PREFIX_VIDEO)){
            return true;
        }
        return false;
    }



}
