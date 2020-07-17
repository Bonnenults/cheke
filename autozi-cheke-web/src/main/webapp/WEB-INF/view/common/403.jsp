<%@ page import="com.autozi.common.utils.util2.ApplicationProperties" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path=ApplicationProperties.getValue("b2bStylePath");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>403 - 缺少权限</title>
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
       <h2>403&nbsp;你没有访问该页面的权限！</h2>
       <div><a href="#" onclick="jump();">返回首页</a><br />页面将在<dfn id="time_block">8秒</dfn>后自动返回上一个浏览页面。</div>
   </div>
</body>
</html>