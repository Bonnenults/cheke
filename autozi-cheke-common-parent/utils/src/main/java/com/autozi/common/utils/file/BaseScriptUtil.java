package com.autozi.common.utils.file;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseScriptUtil {

	
	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/";
//	public static String base_path="C:/Users/Administrator/Desktop/4pl切换/房山/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-0816/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-0425/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/王星/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-0410/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/赵海宁/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-0410-1/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/赵海宁商品/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/赵海宁解绑0419/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/基材切换--迪科/";
//	public static String base_path="C:/Users/Administrator/Desktop/日常数据处理/提交IT表格-0821/";
	
	public static Logger _logger = LoggerFactory.getLogger("CHAINLOG");

	public static String convertString(String s){
    	if(s!=null){
    		return s.replace("\t", "").replace("\r", "").replace("\n", "").trim();
    	}
    	return "";
    }
    public static Long convertLong(String s){
    	if(s==null|| "".equals(s)){
    		return 0L;
    	}
    	return Long.valueOf(s.trim());
    }
    public static Integer convertInteger(String s){
    	if(s==null|| "".equals(s)){
    		return 0;
    	}
    	return Integer.valueOf(s.trim());
    }
    public static Double convertDouble(String s){
    	if(s==null|| "".equals(s)){
    		return 0D;
    	}
    	return Double.valueOf(s);
    }
	
	public static Map<String, Integer> 会员等级Map() {
		Map<String, Integer> mapperMap = new HashMap<String, Integer>();
		mapperMap.put("门店会员",1);
		mapperMap.put("分销会员",2);
		mapperMap.put("大客户",3);
		mapperMap.put("普通会员1",4);
		mapperMap.put("普通会员2",5);
		mapperMap.put("普通会员3",6);
		mapperMap.put("大容会员",7);
		mapperMap.put("上海1",8);
		mapperMap.put("上海2",9);
		mapperMap.put("上海3",10);
		mapperMap.put("辽宁1",11);
		mapperMap.put("辽宁2",12);
		mapperMap.put("辽宁3",13);
		mapperMap.put("陕西1A",14);
		mapperMap.put("陕西2A",15);
		mapperMap.put("山西1",17);
		mapperMap.put("山西2",18);
		mapperMap.put("山西3",19);
		mapperMap.put("天津1",20);
		mapperMap.put("天津2",21);
		mapperMap.put("天津3",22);
		mapperMap.put("苏州1",23);
		mapperMap.put("苏州2",24);
		mapperMap.put("苏州3",25);
		mapperMap.put("北京1",26);
		mapperMap.put("北京2",27);
		mapperMap.put("北京3",28);
		mapperMap.put("新疆1",32);
		mapperMap.put("新疆2",33);
		mapperMap.put("新疆3",34);
		mapperMap.put("A",35);
		mapperMap.put("B",36);
		mapperMap.put("C",37);
		mapperMap.put("D",38);
		mapperMap.put("E",39);
		mapperMap.put("F",40);
		mapperMap.put("驰加会员",42);
		mapperMap.put("湖北1",41);
		mapperMap.put("湖北2",43);
		mapperMap.put("湖北3",44);
		mapperMap.put("河南1",45);
		mapperMap.put("河南2",46);
		mapperMap.put("河南3",47);
		mapperMap.put("浙江1",48);
		mapperMap.put("浙江3",50);
		mapperMap.put("江苏轮胎1",51);
		mapperMap.put("江苏轮胎2",52);
		mapperMap.put("江苏轮胎3",53);
		mapperMap.put("上海直营1",54);
		mapperMap.put("上海直营2",55);
		mapperMap.put("上海直营3",56);
		mapperMap.put("福建1",57);
		mapperMap.put("福建2",58);
		mapperMap.put("福建3",59);
		mapperMap.put("海南1",60);
		mapperMap.put("海南2",61);
		mapperMap.put("海南3",62);
		mapperMap.put("广东1",63);
		mapperMap.put("广东2",64);
		mapperMap.put("广东3",65);
		mapperMap.put("甘肃1",66);
		mapperMap.put("甘肃2",67);
		mapperMap.put("甘肃3",68);
		mapperMap.put("宁夏1",69);
		mapperMap.put("宁夏2",70);
		mapperMap.put("宁夏3",71);
		mapperMap.put("陕西1",72);
		mapperMap.put("陕西2",73);
		mapperMap.put("陕西3",74);
		mapperMap.put("上海2号A",75);
		mapperMap.put("上海2号B",76);
		mapperMap.put("上海2号C",77);
		mapperMap.put("内蒙1",78);
		mapperMap.put("内蒙2",79);
		mapperMap.put("新疆4",80);
		mapperMap.put("北京轮胎1",81);
		mapperMap.put("北京轮胎2",82);
		mapperMap.put("北京轮胎3",83);
		mapperMap.put("内蒙3",84);
		mapperMap.put("天津1A",85);
		mapperMap.put("天津2A",86);
		mapperMap.put("辽宁A",87);
		mapperMap.put("辽宁B",88);
		mapperMap.put("河南1A",89);
		mapperMap.put("河南2A",90);
		mapperMap.put("浙江4",91);
		mapperMap.put("山东门店",92);
		mapperMap.put("山东分销",93);
		mapperMap.put("山东大客户",94);
		mapperMap.put("山东汽修厂1",95);
		mapperMap.put("山东汽修厂2",96);
		mapperMap.put("山东汽修厂3",97);
		mapperMap.put("四川1",98);
		mapperMap.put("四川2",99);
		mapperMap.put("广西1",100);
		mapperMap.put("广西2",101);
		mapperMap.put("广西3",102);
		mapperMap.put("湖北A",103);
		mapperMap.put("湖北B",104);
		mapperMap.put("浙江A",105);
		mapperMap.put("浙江B",106);
		mapperMap.put("云南1",107);
		mapperMap.put("云南2",108);
		mapperMap.put("云南3",109);
		mapperMap.put("云南A",110);
		mapperMap.put("云南B",111);
		mapperMap.put("江苏A",112);
		mapperMap.put("河北1",113);
		mapperMap.put("河北2",114);
		mapperMap.put("河北3",115);
		mapperMap.put("河北4",116);
		mapperMap.put("河北5",117);
		mapperMap.put("河北6",118);
		mapperMap.put("滴滴价",126);
		mapperMap.put("R",127);
		mapperMap.put("R建议零售价",128);
		return mapperMap;
	}
}
