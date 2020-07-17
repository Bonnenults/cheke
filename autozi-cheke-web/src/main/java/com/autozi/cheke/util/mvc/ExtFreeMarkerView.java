package com.autozi.cheke.util.mvc;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

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

	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
		model.put(CONTEXT_PATH, request.getContextPath());
	}
}
