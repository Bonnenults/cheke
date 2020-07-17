package com.autozi.cheke.web.upload.util;

import com.autozi.common.utils.util1.DateFormatter;
import com.google.gson.Gson;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.PutExtra;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

/**
 *
 * @author mingxin li
 * @data 2018/5/14
 *
 */
public class VideoUtils {

    /**
     * 测试
     */
//    public static final String ACCESS_KEY = "u1jJB9MNIvmfr29c-6Ta93nMbzj3fJEyzqCM-ALN";//
//    public static final String SECRET_KEY = "SUT05viZOzRlWEVKcS9EYxb0QDRpwRJjxQo_1Z8S";
//    public static final String BUCKETNAME = "cheke";
//    public static final String DOMAIN = "http://p8r297h5h.bkt.clouddn.com";
    /**
     * 正式
      */
    public static final String ACCESS_KEY = "U-LCpIVGfVJ5cgqphcw4o6VjJgzlqFWi3HmSYe8f";
    public static final String SECRET_KEY = "3n9Luxqu9d98B0kGHmCXluhKjjYxo-JtAxHENARi";
    public static final String BUCKETNAME = "cheke";
    public static final String DOMAIN = "http://video.autozi.com";

    /**
     * 密钥配置
     */
    static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    ///////////////////////指定上传的Zone的信息//////////////////
    //第一种方式: 指定具体的要上传的zone
    //注：该具体指定的方式和以下自动识别的方式选择其一即可
    //要上传的空间(bucket)的存储区域为华东时
    // Zone z = Zone.zone0();
    //要上传的空间(bucket)的存储区域为华北时
    // Zone z = Zone.zone1();
    //要上传的空间(bucket)的存储区域为华南时
    // Zone z = Zone.zone2();

    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
//      static  Zone z = Zone.autoZone();
    static Zone z = Zone.zone1();
    static Configuration c = new Configuration(z);

    //创建上传对象
    static UploadManager uploadManager = new UploadManager(c);
    static BucketManager bucketManager = new BucketManager(auth, c);
    /**
     * 通过文件流上传
     *
     * @param file
     *            文件流
     * @throws AuthException
     * @throws JSONException
     * @author mingxin li
     * @data 2018/5/14
     */
    public static String uploadFile(MultipartFile file) throws AuthException, JSONException {

        String fileName = file.getOriginalFilename();
        String newFileName = getNewName(fileName);

        // 获取当前时间
        long startTime = System.currentTimeMillis();
        String key = getNewName(fileName);
        String upToken = auth.uploadToken(BUCKETNAME);
        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();

        try {
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            // 上传文件
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
//            Response response = uploadManager.put(localfile, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            long endTime = System.currentTimeMillis();
            System.out.println("视频文件上传用时：" + (endTime - startTime) + "ms");
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String fileUrl = getFileResourceUrl(key);
            String mediaPrtScree = qiNiuMediaPrtScreen(key,"jpg");
            System.out.println("++++++++++++fileUrl="+fileUrl);
            System.out.println("++++++++++++mediaPrtScree="+mediaPrtScree);
            JSONObject reJson = new JSONObject();
            reJson.put("fileUrl",fileUrl);
            reJson.put("image",mediaPrtScree);
            return reJson.toString();

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 删除空间中的文件
     *
     * @param fileName
     * @author mingxin li
     * @data 2018/5/15
     */
    public static boolean deleteFile(String fileName) throws Exception {

        try {
            bucketManager.delete(BUCKETNAME, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }

    /**
     * 获得下载地址
     * 这个获取的就是你上传文件资源的链接
     * @param filename
     * @return
     * @throws Exception
     * @uesr mingxin.li
     * @date 2018/5/14
     */
    public static String getFileResourceUrl(String filename) throws Exception {
        String downloadUrl = "";
        if (filename != null) {

            String encodedFileName = URLEncoder.encode(filename, "utf-8");
            downloadUrl = String.format("%s/%s", DOMAIN, encodedFileName);
            System.out.println(downloadUrl);
        }
        return downloadUrl;
    }

    private static Mac getMac() {
        Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
        return mac;
    }

    /**
     * 七牛的视频截图
     *
     * @param fileName
     *            要截图文件名称
     * @param format
     *            截图的类型(jpg.png)
     * @uesr mingxin.li
     * @date 2018/05/15
     */
    public static String qiNiuMediaPrtScreen(String fileName, String format) {

        String screenPic = "";
        long startTime = System.currentTimeMillis();// 获取当前时间

        // 新建一个OperationManager对象
        OperationManager operater = new OperationManager(auth, c);
        // 设置要截图的空间和key，并且这个key在你空间中存在(key就是文件的名字)
        String bucket = BUCKETNAME;
        String key = fileName;
        // 设置截图操作参数
        String fops = "vframe/" + format + "/offset/5|imageView2/0/w/720";
        // 设置截图的队列
        String pipeline = "cheke";
        // 可以对截图后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        String str = fileName.substring(0, fileName.indexOf("."));
        String urlbase64 = UrlSafeBase64.encodeToString(BUCKETNAME + ":" + str
                + "." + format);
        String pfops = fops + "|saveas/" + urlbase64;
        // 设置pipeline参数
        StringMap params = new StringMap().putWhen("force", 1, true)
                .putNotEmpty("pipeline", pipeline);
        try {
            String persistid = operater.pfop(bucket, key, pfops, params);

            screenPic = getFileResourceUrl(str + "." + format);
            System.out.println("视频截图成功.[persistid={}]"+persistid);
        } catch (QiniuException e) {
            Response r = e.response;// 捕获异常信息
            System.out.println(r.toString());// 请求失败时简单状态信息
            try {
                System.out.println(r.bodyString());// 响应的文本信息
            } catch (QiniuException e1) {
                System.out.println(e1.getMessage());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("截取视频截图用时：" + (endTime - startTime) + "ms");

        return screenPic;
    }

    private static String getNewName(String fileName) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        sb.append(DateFormatter.formatToDate(date, "HHmmssS")).append("_");
        sb.append(RandomUtils.nextInt(900000000) + 100000000).append(".");
        String Type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();

        sb.append(Type);
        return sb.toString();
    }
}
