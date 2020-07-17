package com.autozi.cheke.util.web;

import com.autozi.common.utils.util2.ApplicationProperties;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class FView extends FreeMarkerView {

    public static double JS_VERSION_GW = 1.0; //正式公网
    public static double JS_VERSION_TEST = 1.0; //测试公网

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String contextpath = request.getContextPath();
        String b2cContextPath = ApplicationProperties.getValue("b2cDomain");
        String pxContextPath = ApplicationProperties.getValue("pxDomain");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextpath;
//        String basePath = request.getScheme() + "://" + request.getServerName() + contextpath;

        String userType = "Person";
        String index = request.getParameter("index");

        model.put("contextpath", basePath);
        model.put("b2cContextPath", b2cContextPath);
        model.put("pxContextPath", pxContextPath);
        model.put("respath", basePath);
        model.put("userTyper", userType);

        //TODO 正式环境将常量更换 ,并自行更换版本号
        model.put("jsVersion",JS_VERSION_TEST);

        model.put("currentUserName", SpringSecurityUtils.getCurrentUserName());
        model.put("prevPage", request.getHeader("Referer"));
        if (index != null) {
            model.put("pageIndex", index);
        }
        exposeHelpers(model, request);
        doRender(model, request, response);
    }

}
