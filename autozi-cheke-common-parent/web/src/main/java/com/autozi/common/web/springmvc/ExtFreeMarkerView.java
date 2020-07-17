/**
 * 
 */
package com.autozi.common.web.springmvc;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import com.autozi.common.utils.util2.ApplicationProperties;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * FreeMarkerView视图扩展
 * 
 * @author lihaifeng
 *
 */
public class ExtFreeMarkerView extends FreeMarkerView {
	
	public static final String CONTEXT_PATH = "contextpath";
	public static final String RETURN_URL_ID = "returnUrlId";
	public static final String B2B_STYLE_PATH = "b2bStylePath";
	public static final String B2B_FULL_PATH = "b2bFullPath";
	public static final String B2B_VERSION = "b2bVersion";
	public static final String DPL_STYPE_PATH = "dplStyleServer";
	public static final String SALESMAN_STYPE_PATH = "SalesManStyleServer";
	public static final String IMAGE_SERVER_PATH = "imageServerPath";
	public static Long SERVER_TIME=null;
	static{
		if(SERVER_TIME==null){
			SERVER_TIME=System.currentTimeMillis();
		}
	}
	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
		String contextPath=request.getContextPath();
		String fullPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath;
		model.put(B2B_FULL_PATH, fullPath);
		model.put(CONTEXT_PATH, contextPath);
		model.put(RETURN_URL_ID, request.getParameter("mid"));
		model.put(B2B_STYLE_PATH, ApplicationProperties.getValue("b2bStylePath"));
		model.put(B2B_VERSION, SERVER_TIME);
		model.put(DPL_STYPE_PATH, ApplicationProperties.getValue("dpl.style.server"));
		model.put(SALESMAN_STYPE_PATH, ApplicationProperties.getValue("salesman.style.server"));
		model.put(IMAGE_SERVER_PATH, ApplicationProperties.getValue("img.server.url"));
	}
}
