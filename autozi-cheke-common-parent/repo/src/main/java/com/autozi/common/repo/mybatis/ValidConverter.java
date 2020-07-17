//package com.autozi.common.dal.mybatis;
//
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.autozi.common.core.service.DataRightsService;
//import com.autozi.common.core.utils.SpringSecurityUtils;
//
//@Component
//public class ValidConverter implements SqlConverter {
//
////    @Autowired
////    private DataRightsService dataRightsService;
//
//    //VALID{AREA,CATEGORY}
//	//VALID{id, table_A.areaId}--->(table_A.areaId between start and end) and (table_A.areaId between start and end)
//	private static final String VALID_PREFIX = "#VALID";
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List exec(List sqlList) {
//        for (int i = 0; i < sqlList.size(); i++) {
//            Object element = sqlList.get(i);
//            if (element instanceof List) {
//                exec((List) element);
//            } else if (element instanceof String) {
//                String sql = (String) element;
//                if (sql.contains(VALID_PREFIX)) {
//                    sqlList.set(i, splitValidSql(sql));
//                }
//            }
//        }
//		return sqlList;
//	}
//
//    private String splitValidSql(String sql) {
//        String[] validStrings = StringUtils.substringsBetween(sql, VALID_PREFIX + "{", "}");
//        for (String validString : validStrings) {
//            String generatedSql = "";//dataRightsService.resolveValidString(validString, SpringSecurityUtils.getCurrentUserName());
//        	sql = sql.replaceFirst("#VALID\\{\\S+\\}", generatedSql);
//        }
//        return sql;
//    }
//
//}
