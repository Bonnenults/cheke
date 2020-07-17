package com.auto.dao.formate;

import com.autozi.cheke.settle.entity.DrawCashOrder;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;


public class FormaterUtil<T> {


    public static String convertPropertyToColmun(String property) {
        char[] cs = property.toCharArray();
        StringBuilder stringBuffer = new StringBuilder();
        for (char c : cs) {
            if (c >= 'A' && c <= 'Z') {
                stringBuffer.append("_");
            }
            stringBuffer.append(Character.toUpperCase(c));
        }
        return stringBuffer.toString();
    }

    public static String getJdbcType(Field field) {
        String fieldType = field.getType().getName();
        if (fieldType.endsWith("String")) {
            return "VARCHAR";
        } else if (fieldType.endsWith("Date")) {
            return "TIMESTAMP";
        } else {
            return "DECIMAL";
        }
    }

    public static void main(String[] args) throws ParserConfigurationException {
//		createXml("D:\o2o.czgj\\com.qeegoo.o2o.cfgj.dao\\com\\qeegoo\\o2o\\cfgj\\visit\\mapper\\VisitOptionMapper.xml",VisitOption.class,"crm_visit_option","cvo","com.qeegoo.o2o.cfgj.visit.mapper");
      createXml("D:\\qeegoo\\newWorkSpace\\o2o_work\\autozi_new_work\\cheke\\autozi-cheke-dao\\src\\main\\java\\com\\autozi\\cheke\\settle\\mapper\\DrawCashOrderMapper.xml",DrawCashOrder.class,"pay_draw_cash_order","pdco","com.autozi.cheke.settle.mapper");

    }

    /**
     * @param <T>
     * @param fileName          mapper.xml目标文件
     * @param entityClass       待生成.xml的entity的class
     * @param tableName         对应entity的表名
     * @param tableAlias        表的别名
     * @param mapperPackageName 对应interface-mapper.java的包名
     * @throws ParserConfigurationException
     */
    public static <T> void createXml(String fileName, Class<T> entityClass, String tableName, String tableAlias, String mapperPackageName) throws ParserConfigurationException {
        /* 生成mapper和dao*/


        String entityName = entityClass.getSimpleName();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.newDocument();


        //mapper.xml头与根节点
        Element root = document.createElement("mapper");
        root.setAttribute("namespace", mapperPackageName + "." + entityName + "Mapper");
        document.appendChild((Node) root);
        Element cache = (Element) document.createElement("cache");
        cache.setAttribute("eviction", "LRU");
        cache.setAttribute("type", "org.mybatis.caches.memcached.MemcachedCache");
        root.appendChild(cache);

        //resultMap节点
        Element resultMap = (Element) document.createElement("resultMap");
        resultMap.setAttribute("id", entityName + "Map");
        resultMap.setAttribute("type", entityName);
        Element id = (Element) document.createElement("result");
        id.setAttribute("column", "ID");
        id.setAttribute("property", "id");
        id.setAttribute("jdbcType", "DECIMAL");
        resultMap.appendChild(document.createTextNode("\n\t"));
        resultMap.appendChild(id);
        Element version = (Element) document.createElement("result");
        version.setAttribute("column", "VERSION");
        version.setAttribute("property", "version");
        version.setAttribute("jdbcType", "DECIMAL");
        resultMap.appendChild(document.createTextNode("\n\t"));
        resultMap.appendChild(version);
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                String fieldName = field.getName();
                Element result = (Element) document.createElement("result");
                result.setAttribute("column", convertPropertyToColmun(fieldName));
                result.setAttribute("property", fieldName);
                result.setAttribute("jdbcType", getJdbcType(field));
                resultMap.appendChild(document.createTextNode("\n\t"));
                resultMap.appendChild(result);
            }
        }
        root.appendChild(resultMap);

        //base_column_list sql
        Element baseColumnList = (Element) document.createElement("sql");
        baseColumnList.setAttribute("id", "Base_Column_List");
        StringBuilder buffer = new StringBuilder();
        baseColumnList.appendChild(document.createTextNode("\n\t"));
        buffer.append("ID,VERSION,");
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                buffer.append(convertPropertyToColmun(field.getName())).append(",");
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        ((Node) baseColumnList).appendChild(document.createTextNode(buffer.toString()));
        baseColumnList.appendChild(document.createTextNode("\n"));
        root.appendChild(baseColumnList);

        //base_column_list with tableAlias sql
        Element baseColumnList_alias = (Element) document.createElement("sql");
        baseColumnList_alias.setAttribute("id", tableAlias + "_Base_Column_List");
        StringBuilder buffer_alias = new StringBuilder();
        baseColumnList_alias.appendChild(document.createTextNode("\n\t"));
        buffer_alias.append(tableAlias != null ? (tableAlias + ".") : "").append("ID,").append(tableAlias != null ? (tableAlias + ".") : "").append("VERSION,");
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                buffer_alias.append(tableAlias != null ? (tableAlias + ".") : "").append(convertPropertyToColmun(field.getName())).append(",");
            }
        }
        buffer_alias.deleteCharAt(buffer_alias.length() - 1);
        ((Node) baseColumnList_alias).appendChild(document.createTextNode(buffer_alias.toString()));
        baseColumnList_alias.appendChild(document.createTextNode("\n"));
        root.appendChild(baseColumnList_alias);

        //get
        Element getElement = (Element) document.createElement("select");
        getElement.setAttribute("id", "get");
        getElement.setAttribute("resultMap", entityName + "Map");
        getElement.setAttribute("parameterType", "long");
        getElement.appendChild(document.createTextNode("\n\tselect"));
        getElement.appendChild(document.createTextNode("\n\t\t"));
        Element incluse1 = (Element) document.createElement("include");
        incluse1.setAttribute("refid", "Base_Column_List");
        getElement.appendChild(incluse1);
        getElement.appendChild(document.createTextNode("\n\tfrom " + tableName + " " + tableAlias));
        getElement.appendChild(document.createTextNode("\n\twhere ID = #{id,jdbcType=DECIMAL}\n"));
        root.appendChild(getElement);


        //delete
        Element delElement = (Element) document.createElement("delete");
        delElement.setAttribute("id", "delete");
        delElement.setAttribute("parameterType", "long");
        delElement.appendChild(document.createTextNode("\n\tdelete"));
        delElement.appendChild(document.createTextNode("\n\tfrom " + tableName));
        delElement.appendChild(document.createTextNode("\n\twhere ID = #{id,jdbcType=DECIMAL}\n"));
        root.appendChild(delElement);

        //insert
        Element insElement = (Element) document.createElement("insert");
        insElement.setAttribute("id", "insert");
        insElement.setAttribute("parameterType", entityName);
        insElement.appendChild(document.createTextNode("\n\tinsert into " + tableName + "("));
        insElement.appendChild(document.createTextNode("\n\t\t"));
        Element incluse2 = (Element) document.createElement("include");
        incluse2.setAttribute("refid", "Base_Column_List");
        insElement.appendChild(incluse2);
        insElement.appendChild(document.createTextNode("\n\t) values ("));
        insElement.appendChild(document.createTextNode("\n\t#{id,jdbcType=DECIMAL},"));
        insElement.appendChild(document.createTextNode("\n\t#{version,jdbcType=DECIMAL}"));
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("serialVersionUID")) {
                insElement.appendChild(document.createTextNode(","));
                insElement.appendChild(document.createTextNode("\n\t#{" + field.getName() + ",jdbcType=" + getJdbcType(field) + "}"));
            }
        }
        insElement.appendChild(document.createTextNode("\n\t)\n"));
        root.appendChild(insElement);


        //update
        Element updElement = (Element) document.createElement("update");
        updElement.setAttribute("id", "update");
        updElement.setAttribute("parameterType", entityName);
        updElement.appendChild(document.createTextNode("\n\tupdate " + tableName + " " + tableAlias));
        updElement.appendChild(document.createTextNode("\n\t"));
        Element setElement = (Element) document.createElement("set");
        setElement.appendChild(document.createTextNode("\n\t\tVERSION = VERSION + 1"));
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldName.equalsIgnoreCase("serialVersionUID")) {
                setElement.appendChild(document.createTextNode("\n\t\t"));
                Element ifElement = (Element) document.createElement("if");
                ifElement.setAttribute("test", fieldName + " != null");
                ifElement.appendChild(document.createTextNode("\n\t\t," + convertPropertyToColmun(fieldName) + " = #{" + fieldName + ",jdbcType=" + getJdbcType(field) + "}"));
                ifElement.appendChild(document.createTextNode("\n\t\t"));
                setElement.appendChild(ifElement);
            }
        }
        setElement.appendChild(document.createTextNode("\n\t"));
        updElement.appendChild(setElement);
        updElement.appendChild(document.createTextNode("\n\twhere ID = #{id,jdbcType=DECIMAL} and version = #{version,jdbcType=DECIMAL}\n"));
        root.appendChild(updElement);


        //findListForExample
        Element exampleSelect = document.createElement("select");
        exampleSelect.setAttribute("id", "findListForExample");
        exampleSelect.setAttribute("parameterType", entityName);
        exampleSelect.setAttribute("resultMap", entityName + "Map");
        exampleSelect.appendChild(document.createTextNode("\n\tselect\n\t"));
        Element incluse3 = (Element) document.createElement("include");
        incluse3.setAttribute("refid", (tableAlias != null ? tableAlias + "_" : "") + "Base_Column_List");
        exampleSelect.appendChild(incluse3);
        exampleSelect.appendChild(document.createTextNode("\n\tfrom " + tableName + " " + tableAlias + "\n\t"));
        Element incluse4 = (Element) document.createElement("include");
        incluse4.setAttribute("refid", "common_query_condition");
        exampleSelect.appendChild(incluse4);
        root.appendChild(exampleSelect);


        //findListForMap
        Element mapSelect = document.createElement("select");
        mapSelect.setAttribute("id", "findListForMap");
        mapSelect.setAttribute("parameterType", "map");
        mapSelect.setAttribute("resultMap", entityName + "Map");
        mapSelect.appendChild(document.createTextNode("\n\tselect\n\t"));
        Element incluse5 = (Element) document.createElement("include");
        incluse5.setAttribute("refid", (tableAlias != null ? tableAlias + "_" : "") + "Base_Column_List");
        mapSelect.appendChild(incluse5);
        mapSelect.appendChild(document.createTextNode("\n\tfrom " + tableName + " " + tableAlias + "\n\t"));
        Element incluse6 = (Element) document.createElement("include");
        incluse6.setAttribute("refid", "common_query_condition");
        mapSelect.appendChild(incluse6);
        root.appendChild(mapSelect);

        //common_query_condition
        Element condition = document.createElement("sql");
        condition.setAttribute("id", "common_query_condition");
        condition.appendChild(document.createTextNode("\n\t"));
        Element where = document.createElement("where");
        where.appendChild(document.createTextNode("\n\t\t"));
        Element ifElement = document.createElement("if");
        ifElement.setAttribute("test", "id != null");
        ifElement.appendChild(document.createTextNode("\n\t\t\t"));
        ifElement.appendChild(document.createTextNode((tableAlias != null ? tableAlias + ".id = " : "id = ") + "#{id,jdbcType=DECIMAL}\n\t\t"));
        where.appendChild(ifElement);
        where.appendChild(document.createTextNode("\n\t"));
        condition.appendChild(where);
        root.appendChild(condition);


        TransformerFactory tf = TransformerFactory.newInstance();

        try {

            createMapper(fileName, entityClass, mapperPackageName);
            createDao(fileName, entityClass, mapperPackageName);

            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(document);

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//mybatis.org//DTD Mapper 3.0//EN");

            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://mybatis.org/dtd/mybatis-3-mapper.dtd");

//		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            fileName = fileName.replace("java","resources\\\\mybatis");
        fileName = fileName.replace("com\\autozi\\cheke","");
        fileName = fileName.replace("mapper","");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

            StreamResult result = new StreamResult(pw);

            transformer.transform(source, result);


        } catch (TransformerConfigurationException e) {

            System.out.println(e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        } catch (TransformerException e) {

            System.out.println(e.getMessage());

        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

    }


    public static <T> void createMapper(String fileName, Class<T> entityClass, String mapperPackageName) throws FileNotFoundException {
        fileName = StringUtils.substringBefore(fileName, "\\mapper");
        fileName = fileName + "\\mapper\\" + entityClass.getSimpleName() + "Mapper.java";
        StringBuilder mapperFile = new StringBuilder("package " + mapperPackageName + ";");
        mapperFile.append("\n");
        mapperFile.append("import com.autozi.common.dal.mapper.BaseMapper;");
        mapperFile.append("\n");
        mapperFile.append("import " + entityClass.getPackage().getName() + "." + entityClass.getSimpleName() + ";");
        mapperFile.append("\n");
        mapperFile.append("public interface " + entityClass.getSimpleName() + "Mapper" + " extends BaseMapper<" + entityClass.getSimpleName() + "> {");
        mapperFile.append("\n");
        mapperFile.append("}");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)), true);
        pw.println(mapperFile.toString());
    }


    public static <T> void createDao(String fileName, Class<T> entityClass, String mapperPackageName) throws FileNotFoundException {

        fileName = StringUtils.substringBefore(fileName, "\\mapper");
        fileName = fileName + "\\dao\\" + entityClass.getSimpleName() + "Dao.java";
        String daoPackageFileName = StringUtils.substringBefore(mapperPackageName, ".mapper");
        StringBuilder daoFile = new StringBuilder("package " + daoPackageFileName + ".dao;");
        daoFile.append("\n");
        daoFile.append("import com.autozi.common.dal.mybatis.MyBatisDao;");
        daoFile.append("\n");
        daoFile.append("import " + entityClass.getPackage().getName() + "." + entityClass.getSimpleName() + ";");
        daoFile.append("\n");
        daoFile.append("import org.springframework.stereotype.Component;");
        daoFile.append("\n");
        daoFile.append("@Component");
        daoFile.append("\n");
        daoFile.append("public class " + entityClass.getSimpleName() + "Dao extends MyBatisDao<" + entityClass.getSimpleName() + "> {");
        daoFile.append("\n");
        daoFile.append("}");

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)), true);
        pw.println(daoFile.toString());

    }

}
