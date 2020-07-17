package com.autozi.common.utils.util1;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
  
/**  
* @author renliqiang
*  
* To change the template for this generated type comment go to  
* Window>Preferences>Java>Code Generation>Code and Comments  
*/  
public class Cookies {   
  
    private int maxAge; // 设置该COOKIE的有效期,单位为秒   
    private String path; // cookie路径   
    Cookie[] cookie_get = {};   
    public static final int MAX_AGE = 60*60*24*30;
  
    public Cookies() {   
//        maxAge = 2592000;   
        maxAge = MAX_AGE;   
        path = "/";   
    }
    public Cookies(String path,int maxAge) {   
        this.maxAge = maxAge;   
        this.path = path;   
    } 
  
    /**  
    * Put cookie to the client  
    *  
    * @param response  
    * @param name  
    * @param value  
    */  
    public void putCookie(HttpServletResponse response, String name,   
            String value) {   
        try {   
            Cookie cookie = new Cookie(name, value);   
            cookie.setMaxAge(maxAge);   
            cookie.setPath(path);   
            response.addCookie(cookie);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    /**  
    * get cookie from client  
    *  
    * @param request  
    * @param name  
    * @return  
    */  
    public String getCookie(HttpServletRequest request, String name) {   
        if (cookie_get == null || cookie_get.length == 0) {   
            cookie_get = request.getCookies();   
        }   
        String returnStr;   
        returnStr = null;   
        try {   
            for (int i = 0; cookie_get != null && i < cookie_get.length; i++) {   
                cookie_get[i].setPath(path);   
                if (cookie_get[i].getName().equals(name)) {   
                    cookie_get[i].setMaxAge(-1);   
                    returnStr = cookie_get[i].getValue().toString();   
                    break;   
                }   
            }   
            return returnStr;   
        } catch (Exception e) {   
            return returnStr;   
        }   
    }   
  
    /**  
    * 清除Cookie  
    *  
    * @param response  
    *            HttpServletResponse  
    * @param name  
    *            String  
    */  
    public void removeCookie(HttpServletResponse response, String name) {   
        putCookie(response, name, null);   
    }   
  

}  
