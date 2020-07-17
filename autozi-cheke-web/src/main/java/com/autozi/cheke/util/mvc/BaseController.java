/**
 * 文件名称   : com.wms.web.base.BaseController.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-3-18
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.util.mvc;

import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.web.UserDetailsImpl;
import com.autozi.common.core.page.Page;
import com.autozi.common.core.utils.SpringSecurityUtils;
import com.autozi.common.utils.o2o.RenderUtils;
import com.autozi.common.utils.util2.PrintUtil;
import com.autozi.common.utils.util2.PropertyUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class BaseController {

    @Autowired
    	protected IUserService userService;
	
    protected long getCurrentUserId() {
        return getUserDetailsImpl().getUserId();
    }

    protected long getCurrentUserPartyId() {
        return getUserDetailsImpl().getPartyId();
    }

    protected UserDetailsImpl getUserDetailsImpl() {
        return (UserDetailsImpl) SpringSecurityUtils.getCurrentUserDetails();
    }

    public User getCurrentUser() {
       	return userService.getUserById(getUserDetailsImpl().getUserId());
       }


	/**
	 * 应答信息的返回
	 * 
	 * @param response
	 * @param responseMsg
	 * @throws Exception
	 */
	protected void response(HttpServletResponse response, String responseMsg){
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(responseMsg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：JSON返回
	 * 
	 * </PRE>
	 * @作者 Lihf
	 * @日期 2013-2-2
	 * @param response
	 * @param code
	 * @param msg
	 * @throws Exception
	 */
	protected void response(HttpServletResponse response, String code,String msg){
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("code", code);
		json1.put("msg", msg);
		json.put("status", json1);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * 返回json格式的字符串
     * @param response
     * @param code
     * @param msg
     */
    protected void responseJson(HttpServletResponse response, String code,String msg){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * 应答信息的返回
	 * 
	 * @param response
	 * @param code
	 * @throws IOException
	 */
	protected void response(HttpServletResponse response, String code, String msg,String data){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", data);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
   	 * 分页信息
   	 *
   	 * 用map转换成string需要在查询条件增减时在action层对map进行维护
   	 * 这样并不方便，所以在下面重写了一个方法,但是对不通过查询条件Vo
   	 * 获取查询条件的地方，用map会比较灵活，所以保留此方法#annotate by litongke
   	 * @param uiModel
   	 * @param data_container
   	 * @param page
   	 *
   	 */
    protected void pageInfoByMap(Model uiModel, String url, Page<?> page, String data_container, Map<String, Object> queryMap) {
        uiModel.addAttribute("curPageNo", page.getPageNo());
        uiModel.addAttribute("totalPages", page.getTotalPages());
        uiModel.addAttribute("data_container", data_container);
        uiModel.addAttribute("url", url);
        StringBuilder strbud = new StringBuilder();
        for (String key : queryMap.keySet()) {
            if (null != queryMap.get(key) && StringUtils.isNotBlank(queryMap.get(key).toString())) {
                try {
                    strbud.append(key).append("=").append(URLEncoder.encode(queryMap.get(key).toString(), "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (strbud.length() > 0) {
            strbud.delete(strbud.length() - 1, strbud.length()).append(" ");
        }
        uiModel.addAttribute("search_condition", strbud.toString().trim());
    }
    protected void pageInfoByMap(Model uiModel,Page<?> page,Map<String, Object> queryMap){
        String data_container = "data_table_list;page_common_nav";
        pageInfoByMap(uiModel, page, data_container, queryMap);
    }
    
	/**
	 * 分页信息
	 * 
	 * 用map转换成string需要在查询条件增减时在action层对map进行维护
	 * 这样并不方便，所以在下面重写了一个方法,但是对不通过查询条件Vo
	 * 获取查询条件的地方，用map会比较灵活，所以保留此方法#annotate by litongke
	 * @param uiModel
	 * @param data_container
	 * @param page
	 * 
	 */
    protected void pageInfoByMap(Model uiModel,Page<?> page,String data_container,Map<String, Object> queryMap){
        uiModel.addAttribute("curPageNo", page.getPageNo());
        uiModel.addAttribute("totalPages", page.getTotalPages());
        uiModel.addAttribute("data_container", data_container);
        StringBuilder strbud=new StringBuilder();
        for(String key :queryMap.keySet()){
            if(null!= queryMap.get(key) && StringUtils.isNotBlank(queryMap.get(key).toString())){
//			if(!StringUtils.isEmpty(queryMap.get(key).toString())){
                try {
                    strbud.append(key).append("=").append(URLEncoder.encode(queryMap.get(key).toString(), "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if(strbud.length()>0){
            strbud.delete(strbud.length()-1, strbud.length()).append(" ");
        }
        uiModel.addAttribute("search_condition", strbud.toString().trim());
    }

    protected <T> Page<T> buildPage(HttpServletRequest request) {
        Page<T> page = new Page<T>();
        String pageNo = request.getParameter("pageNo");
        if (pageNo == null || pageNo.equals("")) {
            pageNo = "1";
        }
        page.setPageNo(Integer.parseInt(pageNo));
        String pageNum= request.getParameter("pageNum");
        if (StringUtils.isNotBlank(pageNum)) {
            page.setPageNo(Integer.parseInt(pageNum));
        }
        return page;
    }

    protected HashMap<String, Object> buildFilter(HttpServletRequest request, Model uiModel) {
        Map<?, ?> filter = request.getParameterMap();
        HashMap<String, Object> filters = new HashMap<String, Object>();
        filters = (HashMap<String, Object>) copyQueryMap(filter, filters);
        filters.remove("pageNum"); //移除页码标志
        if (filters.containsKey("sysTime")) {
            filters.remove("sysTime");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> queryMap = (Map<String, Object>) filters.clone();
        uiModel.addAttribute("queryMap", queryMap);
        return filters;
    }


    /**
     * 取得查询条件
     *
     * @param filter
     * @param queryMap
     */
    public static Map<String, Object> copyQueryMap(Map<?, ?> filter, Map<String, Object> queryMap) {
        for (Map.Entry<?, ?> entry : filter.entrySet()) {
            String[] value = (String[]) entry.getValue();
            if (value != null && !"".equals(value[0]) && !" ".equals(value[0])) {
                queryMap.put(entry.getKey().toString(), value[0].trim());
            }
        }
        return queryMap;
    }

    /**
     * 设置打印偏移尺寸
     *
     * @param uiModel
     * @param busType
     */
    protected void setPrintSize(Model uiModel, int busType) {
        long partyId = getCurrentUserPartyId();
        // ADD BY LIHF@QEEGOO 2012-8-21 下午8:55:06 START BUG描述：特殊用户处理
        long userId = getCurrentUserId();
        String userStr = PropertyUtil.getPrintStrValue(PrintUtil.getPrintConfigFile(), "ts.user.list", "");
        if (userStr.length() > 0
                && userStr.indexOf(userId + "") != -1) {
            partyId = userId;
        }
        // ADD BY LIHF@QEEGOO 2012-8-21 下午8:55:06 END
        uiModel.addAttribute("extWidthNum", PrintUtil.getExtWidthNum(partyId, busType));
        uiModel.addAttribute("extHeightNum", PrintUtil.getExtHeightNum(partyId, busType));
        uiModel.addAttribute("htmlTop", PrintUtil.getHtmlTop(partyId, busType));
        uiModel.addAttribute("htmlLeft", PrintUtil.getHtmlLeft(partyId, busType));
    }
    
    /**
	 * 输出JSON到页面
	 */
	protected void renderJson(HttpServletResponse response, Object data) {
		RenderUtils.renderJson(response, data);
	}

    protected void saveToken(HttpServletRequest request){
        TokenProcessor tokenProcessor= TokenProcessor.getInstance();
        tokenProcessor.saveToken(request);
    }

    /**
     * ajax 输出文本
     *
     * @param response
     * @param content
     */
    public static void renderText(HttpServletResponse response, String content) {
        response.setContentType("text/plain;charset=utf-8");
        try {
            if (content != null) {
                response.getWriter().write(content);
            }
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
       }


    public Map<String,Object> convertStringToMap(String str){
       Map<String,Object> filters = new HashMap<String, Object>();
       String[] keyAndValArray = str.split("~");
       for(String keyAndVal : keyAndValArray){
          String[] key = keyAndVal.split("@");
           filters.put(key[0],key[1]);
       }
       return filters;
    }

    protected String convertMapToString(Map<String, Object> queryMap) {
        StringBuilder strbud = new StringBuilder();
        for (String key : queryMap.keySet()) {
            if (null != queryMap.get(key) && StringUtils.isNotBlank(queryMap.get(key).toString())) {
                //			if(!StringUtils.isEmpty(queryMap.get(key).toString())){
                try {
                    strbud.append(key).append("=").append(URLEncoder.encode(queryMap.get(key).toString(), "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (strbud.length() > 0) {
            strbud.delete(strbud.length() - 1, strbud.length()).append(" ");
        }
        return strbud.toString().trim();
    }

    public Map<String,String> changeMapObjToString(Map<String,Object> map){
        Map<String,String> result = new HashMap<String, String>();
        for(String key:map.keySet()){
            result.put(key,map.get(key).toString());
        }
        return result;
    }
}
