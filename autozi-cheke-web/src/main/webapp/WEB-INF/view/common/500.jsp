<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.autozi.common.utils.util2.ApplicationProperties,org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.springframework.dao.DataAccessException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.SQLException" %>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
	
	String msg = getMessage(ex);
	if (ex.getMessage() != null) {
//		msg += ex.getMessage();
	}
%>
<%
	String path= ApplicationProperties.getValue("b2bStylePath");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>500 - 系统内部错误</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/styles/theme.css" />
        <script type="text/javascript" src="<%=path%>/b2bjs/scripts/jquery-1.5.1.min.js"></script>
		<script type="text/javaScript">
			var timeId = null;
			var running = true;
			function jump(){
				window.parent.location.href = "http://www.b2bex.com";
			}
			var index = 0;
			function showCount(){
				if(index==8){
					running = false;
					return;
				}
				$('#time_block').text((8-index)+"秒");
				timeId = setTimeout("startCount()",1000);
				index++;
			}
			function stopCount(){
				if(!running){
					clearTimeout(timeId);
					history.go(-1);
					return;
				}
				running = true;
			}
			function startCount(){
				showCount();
				stopCount();
			}
			startCount();
		</script>
	
</head>

<body>
   <div class="error_msg">
       <h2>500&nbsp;不好意思，出现错误了！</h2>
       <p><%= msg %></p>
       <div>错误详情已经记录下来了。请联系管理员处理这个问题！<a href="#" onclick="jump();">返回首页</a><br />页面将在<dfn id="time_block">8秒</dfn>后自动返回上一个浏览页面。</div>
   </div>
</body>
</html>

<%!
	private String getMessage(Throwable e) {
		if (e instanceof DataAccessException) {
			return "数据库操作失败！";
		} else if (e instanceof NullPointerException) {
			return "调用了未经初始化的对象或者是不存在的对象！";
		} else if (e instanceof IOException) {
			return "读写异常！";
		} else if (e instanceof ClassNotFoundException) {
			return "指定的类不存在！";
		} else if (e instanceof ArithmeticException) {
			return "数学运算异常！";
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			return "数组下标越界！";
		} else if (e instanceof IllegalArgumentException) {
			return "方法的参数错误！";
		} else if (e instanceof ClassCastException) {
			return "类型强制转换错误！";
		} else if (e instanceof SecurityException) {
			return "违背安全原则异常！";
		} else if (e instanceof SQLException) {
			return "操作数据库异常！";
		} else if (e instanceof NoSuchMethodError) {
			return "方法末找到异常！";
		} else if (e instanceof InternalError) {
			return "发生了内部错误！";
		} else if (e instanceof Exception) {
			return "内部错误，操作失败！";
		}
		return e.toString();
	}
%>