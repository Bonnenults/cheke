package com.autozi.common.utils.chanpay.util;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class ChanPayUtil {
    /**
     * 获取基本传递参数
     *
     * @param service
     * @return
     */
    public static Map<String, String> getBaseParameter(String service) {
        Map<String, String> origMap = new HashMap<String, String>();
        // 基本参数
        origMap.put(ChanPayConstant.SERVICE, service);// 接口名称
        origMap.put(ChanPayConstant.VERSION, "1.0");
        origMap.put(ChanPayConstant.PARTNER_ID, ChanPayConstant.PARTNER_ID_VALUE); // 畅捷支付分配的商户号
        origMap.put(ChanPayConstant.TRADE_DATE, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        origMap.put(ChanPayConstant.TRADE_TIME, new SimpleDateFormat("HHmmss").format(new Date()));
        origMap.put(ChanPayConstant.INPUT_CHARSET, ChanPayConstant.CHARSET);// 字符集
        origMap.put(ChanPayConstant.MEMO, "");// 备注
        return origMap;

    }

    /**
     * 向测试服务器发送post请求
     *
     * @param origMap
     * @param charset
     * @param privateKey
     */
    public static String sendPost(Map<String, String> origMap, String charset, String privateKey) throws Exception {
        Map<String, String> sPara = ChanPayUtil.buildRequestPara(origMap, "RSA", privateKey, charset);
        String resultString = ChanPayUtil.buildRequest(sPara, "RSA", privateKey, charset, ChanPayConstant.GATEWAY_URL);
        return resultString;
    }

    public static String sendEBankPost(Map<String, String> origMap, String charset, String privateKey) throws Exception {
        Map<String, String> sPara = ChanPayUtil.buildRequestPara(origMap, "RSA", privateKey, charset);
        String url = ChanPayConstant.GATEWAY_URL +"?"+ createLinkString(sPara, true);
        return url;
    }

    public static void sendFilePost(Map<String, String> origMap,
                                    String charset, String MERCHANT_PRIVATE_KEY,
                                    String strParaFileName, String strFilePath) {
        try {

            Map<String, String> sPara = ChanPayUtil.buildRequestPara(origMap,
                    "RSA", MERCHANT_PRIVATE_KEY, charset);
            String resultString = ChanPayUtil.buildFileRequest(sPara, "RSA",
                    MERCHANT_PRIVATE_KEY, charset,
                    ChanPayConstant.BATCH_FILE_GATEWAY_URL, strParaFileName,
                    strFilePath);
            System.out.println(resultString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密，部分接口，有参数需要加密
     *
     * @param src       原值
     * @param publicKey 畅捷支付发送的平台公钥
     * @param charset   UTF-8
     * @return RSA加密后的密文
     */
    public static String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset),
                    publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createLinkString(Map<String, String> params, boolean encode) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get(ChanPayConstant.INPUT_CHARSET);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String lingString(Map<String, String> params, boolean encode) {

        params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get(ChanPayConstant.INPUT_CHARSET);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String generateOutTradeNo() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + String.valueOf(new Double(
                Math.round(Math.random() * 1000000000)).longValue());
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param sParaTemp 请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset, String gatewayUrl) throws Exception {

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(sParaTemp, inputCharset));
        request.setUrl(gatewayUrl);
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            System.out.println("收到返回信息为null");
            return null;
        }
        String strResult = response.getStringResult();
        System.out.println(strResult);
        return strResult;
    }

    public static String buildFileRequest(Map<String, String> sParaTemp,
                                          String signType, String key, String inputCharset,
                                          String gatewayUrl, String strParaFileName, String strFilePath)
            throws Exception {

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
                .getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);

        request.setMethod(HttpRequest.METHOD_POST);

        request.setParameters(generatNameValuePair(sParaTemp, inputCharset));
        request.setUrl(gatewayUrl);

        HttpResponse response = httpProtocolHandler.execute(request,
                strParaFileName, strFilePath);
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();

        return strResult;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase(ChanPayConstant.SIGN) || key.equalsIgnoreCase(ChanPayConstant.SIGN_TYPE)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if (ChanPayConstant.MD5.equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if (ChanPayConstant.RSA.equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }

        // 签名结果与签名方式加入请求提交参数组中
        sPara.put(ChanPayConstant.SIGN, mysign);
        sPara.put(ChanPayConstant.SIGN_TYPE, signType);

        return sPara;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara,
                                           String key, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara,
                                           String privateKey, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(
            Map<String, String> properties, String charset) throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(), charset));
        }

        return nameValuePair;
    }

    public static String getNowDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern).format(new Date());
    }

    public static String getOrderEndDate() throws RuntimeException {
        Calendar now=Calendar.getInstance();
        now.add(Calendar.MINUTE,1);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime());
    }
}
