/**
 * 
 */
package com.autozi.cheke.mobile.util.mvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;


/**
 * 视图导航类扩展
 * 
 * @author lihaifeng
 *
 */
public class ExtFreeMarkerViewResolver extends AbstractTemplateViewResolver {
	
	public ExtFreeMarkerViewResolver() {
		super.setViewClass(ExtFreeMarkerView.class);
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}
