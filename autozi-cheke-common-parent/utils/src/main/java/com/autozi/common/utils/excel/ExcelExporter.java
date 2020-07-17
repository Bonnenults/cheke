package com.autozi.common.utils.excel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-14
 * Time: 下午1:40
 */
public final class ExcelExporter {

    private final static String HEADER_INFO_PREFIX = "header.";
    private final static String FOOTER_INFO_PREFIX = "footer.";
    private final static String FOOTER2_INFO_PREFIX = "footer2.";
    private final static String FOOTER3_INFO_PREFIX = "footer3.";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private List<ExportProperty> exportProperties;
    private List<ExportProperty> headerProperties;
    private List<ExportProperty> footerProperties;
    private List<ExportProperty> footer2Properties;
    private List<ExportProperty> footer3Properties;

    /**
     * 该对象不可重复使用，每次生成一个Excel应该创建一个新的对象，因此不对象的构造器，强制调用静态方法
     */
    private ExcelExporter() {}

    /**
     * 生成Excel，没有表头信息
     * @param entities 数据实体
     * @param template 模板
     * @param out 输出流
     * @throws java.io.IOException 模板文件读取错误抛出该异常
     * @throws InvalidFormatException 模板的格式不正确抛出该异常
     */
    public static void export(List<?> entities, String template, OutputStream out) throws IOException, InvalidFormatException {
        new ExcelExporter().exportExcel(entities, template, out);
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
        new ExcelExporter().exportExcel(entities, headerInfo, template, out);
    }

    public void exportExcel(List<?> entities, String template, OutputStream out) throws IOException, InvalidFormatException {
        exportExcel(entities, null, template, out);
    }

    public void exportExcel(List<?> entities, Object headerInfo, String template, OutputStream out) {
    	
        InputStream in = null;
		try {
			in = new FileInputStream(template);
			Workbook templateWorkbook = WorkbookFactory.create(in);
			resolveTemplate(templateWorkbook);
			if (headerInfo != null && headerProperties.size() > 0) {
			    generateHeaderInfo(headerInfo, templateWorkbook.getSheetAt(0), headerProperties);
			}
			if (exportProperties.size() > 0) {
			    generateData(entities, templateWorkbook.getSheetAt(0), exportProperties);
			}
			templateWorkbook.write(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
    }

    private List<ExportProperty> resolveTemplate(Workbook templateWorkbook) throws IOException, InvalidFormatException {
        Sheet sheet = templateWorkbook.getSheetAt(0);

        List<CellRangeAddress> mergedRegions = extractMergedRegion(sheet);

        exportProperties = new ArrayList<ExportProperty>();
        headerProperties = new ArrayList<ExportProperty>();
        footerProperties = new ArrayList<ExportProperty>();
        footer2Properties = new ArrayList<ExportProperty>();
        footer3Properties = new ArrayList<ExportProperty>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    String content = cell.getStringCellValue();
                    if (content.startsWith("${") && content.endsWith("}")) {
                        String property = StringUtils.substringBetween(cell.getStringCellValue(), "${", "}");
                        if (StringUtils.isNotBlank(property)) {
                            if (property.startsWith(HEADER_INFO_PREFIX)) {
                                ExportProperty exportProperty = new ExportProperty(property.substring(HEADER_INFO_PREFIX.length()), row.getRowNum(), cell.getColumnIndex());
                                exportProperty.setColumnSpan(extractCellColSpan(mergedRegions, row, cell));
                                headerProperties.add(exportProperty);
                            } else if(property.startsWith(FOOTER_INFO_PREFIX)){
                                ExportProperty exportProperty = new ExportProperty(property.substring(FOOTER_INFO_PREFIX.length()), row.getRowNum(), cell.getColumnIndex());
                                exportProperty.setColumnSpan(extractCellColSpan(mergedRegions, row, cell));
                                footerProperties.add(exportProperty);
                            } else if(property.startsWith(FOOTER2_INFO_PREFIX)){
                                ExportProperty exportProperty = new ExportProperty(property.substring(FOOTER2_INFO_PREFIX.length()), row.getRowNum() ,cell.getColumnIndex());
                                exportProperty.setColumnSpan(extractCellColSpan(mergedRegions, row,cell));
                                footer2Properties.add(exportProperty);
                            } else if(property.startsWith(FOOTER3_INFO_PREFIX)){
                                ExportProperty exportProperty = new ExportProperty(property.substring(FOOTER3_INFO_PREFIX.length()), row.getRowNum() ,cell.getColumnIndex());
                                exportProperty.setColumnSpan(extractCellColSpan(mergedRegions, row,cell));
                                footer3Properties.add(exportProperty);
                            }else {
                                ExportProperty exportProperty = new ExportProperty(property, row.getRowNum(), cell.getColumnIndex());
                                exportProperty.setColumnSpan(extractCellColSpan(mergedRegions, row, cell));
                                exportProperties.add(exportProperty);
                            }
                        }
                    }
                }
            }

        }

        return exportProperties;
    }

    private int extractCellColSpan(List<CellRangeAddress> mergedRegion, Row row, Cell cell) {
        for (CellRangeAddress region : mergedRegion) {
            if (region.isInRange(row.getRowNum(), cell.getColumnIndex())) {
                return region.getNumberOfCells();
            }
        }
        return 1;
    }

    private List<CellRangeAddress> extractMergedRegion(Sheet sheet) {
        List<CellRangeAddress> mergedRegion = new ArrayList<CellRangeAddress>();
        int mergedRegionNumber = sheet.getNumMergedRegions();
        for (int i = 0; i < mergedRegionNumber; i++) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            mergedRegion.add(region);
        }
        return mergedRegion;
    }

    private void generateHeaderInfo(Object headerInfo, Sheet sheet, List<ExportProperty> exportProperties) {
        for (ExportProperty property : exportProperties) {
            Cell cell = sheet.getRow(property.getRowIndex()).getCell(property.getColumnIndex());
            setCellValue(cell, headerInfo, property.getPropertyName());
        }
    }

    private void generateData(List<?> entities, Sheet sheet, List<ExportProperty> exportProperties) {
        int rowIndex = exportProperties.get(0).getRowIndex();
        for (Object entity : entities) {
            generateRow(entity, rowIndex, sheet, exportProperties);
            rowIndex++;
        }
    }

    private void generateRow(Object entity, int rowIndex, Sheet sheet, List<ExportProperty> exportProperties) {
        Row row = sheet.createRow(rowIndex);
        for (ExportProperty exportProperty : exportProperties) {
            Cell cell = row.createCell(exportProperty.getColumnIndex());
            if (exportProperty.getColumnSpan() > 1) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, exportProperty.getColumnIndex(), exportProperty.getLastColumnIndex()));
            }
            setCellValue(cell, entity, exportProperty.getPropertyName());
        }
    }

    private void setCellValue(Cell cell, Object entity, String property) {
        try {
            Object valueObject = PropertyUtils.getProperty(entity, property);
            if (valueObject != null) {
                Class<?> valueClass = valueObject.getClass();
                if (valueClass == String.class) {
                    cell.setCellValue(valueObject.toString());
                } else if (valueClass == Date.class) {
                    cell.setCellValue(dateFormat.format((Date) valueObject));
                } else if (valueClass == Integer.class) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Integer) valueObject);
                } else if (valueClass == Double.class) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Double) valueObject);
                } else if (valueClass == Boolean.class) {
                    cell.setCellValue((Boolean) valueObject);
                } else {
                    cell.setCellValue(valueObject.toString());
                }
            } else {
                cell.setCellValue("");
            }
        } catch (InvocationTargetException e) {
            cell.setCellValue("Error");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            cell.setCellValue("Error");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            cell.setCellValue("Error");
            e.printStackTrace();
        }
    }

    /**
     * 带合计行的导出
     * @param entities
     * @param footerInfo
     * @param template
     * @param outputStream
     */
    public static void exportHasFooter(List<?> entities, Object footerInfo,Object footer2Info,Object footer3Info, String template, ServletOutputStream outputStream) throws IOException, InvalidFormatException {
        new ExcelExporter().exportExcel(entities, null,footerInfo, footer2Info, footer3Info, template, outputStream);
    }
    public void exportExcel(List<?> entities, Object headerInfo,Object footerInfo, Object footer2Info, Object footer3Info, String template, OutputStream out) throws IOException, InvalidFormatException {
        InputStream in = new FileInputStream(template);
        Workbook templateWorkbook = WorkbookFactory.create(in);
        resolveTemplate(templateWorkbook);
        if (headerInfo != null && headerProperties.size() > 0) {
            generateHeaderInfo(headerInfo, templateWorkbook.getSheetAt(0), headerProperties);
        }
        if (exportProperties.size() > 0) {
            generateData(entities, templateWorkbook.getSheetAt(0), exportProperties);
        }
        if(footerInfo!=null && footerProperties.size() > 0){
            generateFooterInfo(footerInfo,templateWorkbook.getSheetAt(0), footerProperties,entities.size());
        }
        if(footer2Info != null && footer2Properties.size() > 0){
            generateFooter2Info(footer2Info, templateWorkbook.getSheetAt(0), footer2Properties, entities.size());
        }
        if(footer3Info != null && footer3Properties.size() > 0){
            generateFooter3Info(footer3Info, templateWorkbook.getSheetAt(0), footer3Properties, entities.size());
        }
        templateWorkbook.write(out);
        in.close();
    }

    private void generateFooter3Info(Object footer3Info, Sheet sheet, List<ExportProperty> footer2Properties, int dataLength) {
        int lastRowNumber = sheet.getLastRowNum();
        if(dataLength == 1){
            lastRowNumber = lastRowNumber ;
        }else {
            lastRowNumber = lastRowNumber + 1;
        }
        generateRow(footer3Info,lastRowNumber,sheet,footer2Properties);
    }

    private void generateFooter2Info(Object endRowInfo, Sheet sheet, List<ExportProperty> footer2Properties, int dataLength) {
        int lastRowNumber = sheet.getLastRowNum();
        if(dataLength == 1){
            lastRowNumber = lastRowNumber -1 ;
        } else if(dataLength == 2){
            lastRowNumber = lastRowNumber ;
        }else {
            lastRowNumber = lastRowNumber +1;
        }
        generateRow(endRowInfo,lastRowNumber,sheet,footer2Properties);
    }

    private void generateFooterInfo(Object footerInfo, Sheet sheet, List<ExportProperty> exportProperties,int dataLength) {
        int lastRowNumber = sheet.getLastRowNum();
        if(dataLength == 1){
            lastRowNumber = lastRowNumber -2;
        }else if(dataLength == 2){
            lastRowNumber = lastRowNumber -1;
        }else if(dataLength == 3){
            lastRowNumber = lastRowNumber;
        }else {
            lastRowNumber = lastRowNumber +1;
        }
        generateRow(footerInfo,lastRowNumber,sheet,exportProperties);
    }
}
