package com.autozi.common.utils.o2o;

import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by denghua on 2015/8/26.
 */
public class Base64ToImageUtil {

    public static void main(String[] args)
    {
        String strImg = GetImageStr();
        System.out.println(strImg);
       // GenerateImage(strImg);
    }
    //图片转化成base64字符串
    public static String GetImageStr()
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "d://test.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * base64字符串转化成图片
     * @param str 图片数据
     * @param path 图片服务器域名
     * @param createPath 文件夹名
     * @param imageType 图片类型
     * @return
     */
    public static String GenerateImage(String str,String path,String createPath,String imageType,Long userId)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (str == null) //图像数据为空
            return null;

       String imgStr= str.substring(str.indexOf("base64,") + 7, str.length());
        //图片扩展名
        if(StringUtils.isNotBlank(imageType)){
            if(imageType.indexOf("jpeg")!=-1){
                imageType=".jpg";
            }else{
                imageType="."+imageType.replace("image/","");
            }
        }else{
            imageType=".jpg";
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            File file = new File(path + createPath+"/"+userId);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
            //生成图片
            String imgFilePath = createPath+"/"+userId+"/"+System.currentTimeMillis()+imageType;//新生成的图片
            OutputStream out = new FileOutputStream(path+imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return imgFilePath;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

