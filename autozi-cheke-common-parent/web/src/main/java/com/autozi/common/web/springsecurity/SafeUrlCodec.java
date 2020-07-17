package com.autozi.common.web.springsecurity;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Author: kai.liu
 * Date: 11-5-25
 * Time: 下午3:46
 */
public class SafeUrlCodec {

    public static String encode(String url) {
        if (url.contains("http://")) {
            return encodeAbsolutePath(url);
        } else {
            return encodeRelativePath(url);
        }

    }

    private static String encodeAbsolutePath(String url) {
        String[] parts = url.split("/");

        String needEncodeParts = StringUtils.join(ArrayUtils.subarray(parts, 4, parts.length), "/");
        if (needEncodeParts.length() > 0) {
            String domain = StringUtils.join(ArrayUtils.subarray(parts, 0, 4), "/");
            return domain + "/" + Base64.encodeBase64URLSafeString(needEncodeParts.getBytes()) + "?encode=true";
        } else {
            return url;
        }
    }

    private static String encodeRelativePath(String url) {
        if (url.length() < 2) {
            return url;
        }
        String urlExcludeFirstSlash = url.substring(1);
        int slashIndex = urlExcludeFirstSlash.indexOf("/");
        if (slashIndex != -1) {
            String namespace = urlExcludeFirstSlash.substring(0, slashIndex);
            String others = urlExcludeFirstSlash.substring(slashIndex + 1);
            return "/" + namespace + "/" + Base64.encodeBase64URLSafeString(others.getBytes()) + "?encode=true";
        } else {
            return url;
        }
    }

    public static String decode(String url) {
        String urlExcludeFirstSlash = url.substring(1);
        String[] urlParts = urlExcludeFirstSlash.split("/");

        if (urlParts.length < 2) {
            return url;
        }

        String decoded = new String(Base64.decodeBase64(urlParts[1]));
        return "/" + urlParts[0] + "/" + decoded;
    }

    public static void main(String[] args) {
        System.out.println(encode("http://test.b2bex.com:8080/goods/category!list.action?targetType=20&parentId=0"));
        System.out.println(encode("http://test.b2bex.com:8080/index.action"));
        System.out.println(encode("http://test.b2bex.com:8080#"));
        System.out.println(decode("http://test.b2bex.com:8080/goods/Y2F0ZWdvcnkhbGlzdC5hY3Rpb24_dGFyZ2V0VHlwZT0yMCZwYXJlbnRJZD0w"));
        System.out.println(decode("http://test.b2bex.com:8080/index.action"));
        System.out.println(decode("http://test.b2bex.com:8080#"));

    }
}
