
package com.autozi.cheke.mobile.util.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 前端视图拦截器 不对请求前的处理，请求前的处理交给security来管理
 * @author shixin.zhang
 *
 */
@Controller
public class FrontViewInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 处理请求后页面跳转或替换
	 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null){
        	//获取返回url
        String url = modelAndView.getViewName();
        //判断url是否需要直接跳转或替换掉index内容
        if (!url.contains("redirect") && !isAllowURL(request) && !isFilterView(modelAndView)) {
            this.parseContentUrl(modelAndView, request);
		    }
        }
    }
	/**
     * 过滤view
     *
     * @param modelAndView
     * @return
     */
    private boolean isFilterView(ModelAndView modelAndView) {
        String view_name = modelAndView.getViewName();
        if (view_name.endsWith(getView_suffix_name()) || getExt_view_list().contains(view_name)) {
            return true;
        }
        return false;
    }

    /**
     * 内容URL替换
     *
     * @param modelAndView
     * @param request
     */
    private void parseContentUrl(ModelAndView modelAndView,
                                 HttpServletRequest request) {
    	System.out.println(modelAndView.getViewName());
        modelAndView.addObject("content", modelAndView.getViewName());
        modelAndView.setViewName(this.getIndex());
    }

    /**
     * 是否是允许的URL
     *
     * @param request
     * @return
     * @author lihf
     */
    private boolean isAllowURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        url = url.substring(url.lastIndexOf("/") + 1);
        if (url.indexOf(";") >= 0) {
            url = url.split("[;]")[0];
        }
        return this.getAllow_url_list().contains(url);
    }
    private String view_suffix_name;    // 待过滤的页面扩展名
    private List<String> ext_view_list; // 待过滤的页面列表
    private List<String> allow_url_list;// 无需处理的URL
    private String index = "/index";                       // 主页
    private List<String> url_pre;          // url.filter 前缀列表

    public String getView_suffix_name() {
        return view_suffix_name;
    }

    public void setView_suffix_name(String view_suffix_name) {
        this.view_suffix_name = view_suffix_name;
    }

    public List<String> getExt_view_list() {
        return ext_view_list;
    }

    public void setExt_view_list(List<String> ext_view_list) {
        this.ext_view_list = ext_view_list;
    }

    public List<String> getAllow_url_list() {
        return allow_url_list;
    }

    public void setAllow_url_list(List<String> allow_url_list) {
        this.allow_url_list = allow_url_list;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<String> getUrl_pre() {
        return url_pre;
    }

    public void setUrl_pre(List<String> url_pre) {
        this.url_pre = url_pre;
    }

}
