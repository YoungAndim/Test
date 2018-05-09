<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<fm:form method="post" modelAttribute="user">
		
		用户编码：<fm:input path="userCode"/><fm:errors path="userCode"/><br/>
		用户名称：<fm:input path="userName"/><fm:errors path="userName"/><br/>
		用户密码：<fm:password path="userPassword"/><fm:errors path="userPassword"/><br/>
		用户生日：<fm:input path="birthday" Class="Wdate" readonly="readonly" onclick="WdatePicker();"/><fm:errors path="birthday"/>
		用户地址：<fm:input path="address"/></br>
		用户电话：<fm:input path="phone"/></br>
		用户角色：
				<fm:radiobutton path="userRole" value="1"/>系统管理员
				<fm:radiobutton path="userRole" value="2"/>经理
				<fm:radiobutton path="userRole" value="3" checked = "checked" />普通用户
				<input type="submit" value="保存"/>
	</fm:form>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
