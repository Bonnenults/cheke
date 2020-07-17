package com.autozi.common.utils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-18
 * Time: 上午10:38
 */
public class ExcelExportUtil {

    /**
     * 生成Excel，没有表头信息
     * @param entities 数据实体
     * @param template 模板
     * @param out 输出流
     * @throws java.io.IOException 模板文件读取错误抛出该异常
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException 模板的格式不正确抛出该异常
     */
    public static void export(List<?> entities, String template, OutputStream out) throws IOException, InvalidFormatException {
        ExcelExporter.export(entities, template, out);
    }

    /**
     * 生成Excel，没有表头信息
     * @param entities 数据实体
     * @param template 模板
     * @param response Response
     * @throws java.io.IOException 模板文件读取错误抛出该异常
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException 模板的格式不正确抛出该异常
     */
    public static void export(List<?> entities, String template, HttpServletResponse response, String fileName) throws IOException, InvalidFormatException {
    	//
    	//response.setContentType("application/msexcel");
    	//response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        response.setContentType("application/ms-excel");
	    response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
        ExcelExporter.export(entities, template, response.getOutputStream());
    }

    /**
     * 生成Excel，包含表头信息
     * @param entities 数据实体
     * @param headerInfo 表头信息
     * @param template 模板
     * @param out 输出流
     * @throws java.io.IOException 模板文件读取错误抛出该异常
     * @throws InvalidFormatException 模板的格式不正确抛出该异常
     */
    public static void export(List<?> entities, Object headerInfo, String template, OutputStream out) throws IOException, InvalidFormatException {
        ExcelExporter.export(entities, headerInfo, template, out);
    }

    public static void export(List<?> entities, Object headerInfo, String template, HttpServletResponse response, String fileName) throws IOException, InvalidFormatException {
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ExcelExporter.export(entities, headerInfo, template, response.getOutputStream());
    }
    /**
     * 生成Excel，没有表头信息,有底部
     * @param entities 数据实体
     * @param template 模板
     * @param response Response
     * @throws java.io.IOException 模板文件读取错误抛出该异常
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException 模板的格式不正确抛出该异常
     */
    public static void exportHasFooter(List<?> entities, Object footerInfo, Object footer2Info,Object footer3Info, String template, HttpServletRequest request, HttpServletResponse response, String exclName) throws IOException, InvalidFormatException {
        response.setContentType("application/msexcel");
        response.setHeader("Content-disposition","attachment; filename=" + new String(exclName.getBytes("GBK"), "ISO-8859-1"));
        ExcelExporter.exportHasFooter(entities,footerInfo, footer2Info, footer3Info, template, response.getOutputStream());
    }
}
