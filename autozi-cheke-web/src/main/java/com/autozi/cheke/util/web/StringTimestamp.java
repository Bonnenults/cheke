package com.autozi.cheke.util.web;

import com.autozi.common.utils.util2.DateTools;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.List;

/**
 * 自定义freemarker获取当前时间，使用方式在html页面${currentDate()}
 * @author Richard
 *
 */
public class StringTimestamp implements TemplateMethodModel {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args)throws TemplateModelException {
		if (null == args || args.isEmpty()) {
			return DateTools.getLongDateStr();
		}
		return DateTools.formatDateForMore(new Date(), args.get(0).toString());
	}

}
