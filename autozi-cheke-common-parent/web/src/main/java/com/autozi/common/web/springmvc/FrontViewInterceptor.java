/**
 * 
 */
package com.autozi.common.web.springmvc;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.autozi.common.utils.util2.PropertyUtil;
import com.autozi.common.utils.util3.SceneDefinition;

/**
 * 前端视图拦截器
 * 
 * @author lihf
 *
 */
@Controller
public class FrontViewInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null
				&& !isAllowURL(request)
				&& !isFilterView(modelAndView)){
		    String _index_pre = getIndexPre(request);
		    if(url_pre.contains(_index_pre)){
		        this.parseContentUrl(modelAndView,request,_index_pre);
		    }
		}
	}
	/**
	 * 过滤view
	 * 
	 * @param modelAndView
	 * @return
	 */
	private boolean isFilterView(ModelAndView modelAndView){
		String view_name = modelAndView.getViewName();
		if(view_name.endsWith(getView_suffix_name())
				|| getExt_view_list().contains(view_name)){
			return true;
		}
		return false;
	}
	
	/**
	 * 内容URL替换
	 * 
	 * @param modelAndView
	 * @param request
	 * @param indexPre
	 */
	private void parseContentUrl(ModelAndView modelAndView,
			HttpServletRequest request,String indexPre){
		if(SceneDefinition.REQUEST_NOT_ACCESSIBLE_PAGE.equals(modelAndView.getViewName())){
			modelAndView.setViewName(indexPre+this.getNotAccessiblePage());
		}else if(SceneDefinition.REQUEST_ERROR_PAGE.equals(modelAndView.getViewName())){
			modelAndView.setViewName(indexPre+this.getErrorPage());
		}else{
			modelAndView.addObject("content", modelAndView.getViewName());
			modelAndView.setViewName(indexPre+this.getIndex());
		}
	}
	
	/**
	 * 是否是允许的URL
	 * 
	 * @author lihf
	 * @param request
	 * @return
	 */
	private boolean isAllowURL(HttpServletRequest request){
		String url = request.getRequestURI();
		url = url.substring(url.lastIndexOf("/") + 1);
		return this.getAllow_url_list().contains(url);
	}
	
	/**
	 * 获取主页路径前缀
	 * 
	 * @param request
	 * @return
	 */
	private String getIndexPre(HttpServletRequest request){
	    String index_pre = "";
	    String url = request.getRequestURI().split("/")[1];
	    if(!request.getContextPath().equals("")){
	    	url = request.getRequestURI().split("/")[2];
	    }
	    for (int index = 0;index<url_pre.size();index++) {
            String key = url_pre.get(index)+".url.filter";
            String[] url_list = PropertyUtil.getStrValue("systemConfig.properties", key, "").split(",");
            if(Arrays.asList(url_list).contains(url)){
                index_pre = url_pre.get(index);
                break;
            }
        }
	    return index_pre;
	}
	
	private String view_suffix_name;    // 待过滤的页面扩展名
	private List<String> ext_view_list; // 待过滤的页面列表
	private List<String> allow_url_list;// 无需处理的URL
	private String index = "/index";                       // 主页
	private List<String> url_pre;          // url.filter 前缀列表
	private String notAccessiblePage = "/request_not_accessible";//权限不足
	private String errorPage = "/request_error";//错误页面
	
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
	public String getNotAccessiblePage() {
		return notAccessiblePage;
	}
	public String getErrorPage() {
		return errorPage;
	}
	
}
