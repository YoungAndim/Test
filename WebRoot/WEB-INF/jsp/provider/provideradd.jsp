<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<fm:form method="post" modelAttribute="provider">
	
		供应商编码：<fm:input path="proCode" /> <fm:errors path="proCode"/><br/>
		供应商名称：<fm:input path="proName"/> <fm:errors path="proName"/><br/>
		联系人：<fm:input path="proContact"/> <fm:errors path="proContact"/><br/>
		联系电话：<fm:input path="proPhone"/> <fm:errors path="proPhone"/><br/>
		联系地址：<fm:input path="proAddress"/><br/>
			传真：<fm:input path="proFax"/><br/>
			描述：<fm:input path="proDesc"/><br/>
		<input type="submit" value="保存"/>
		<input type="button" value="返回" onclick="show();"/>
	</fm:form>
<<script type="text/javascript">
	function show() {
		window.location.href="http://localhost:8080/SMBMS_C10_09/provider/provider.do";
	}
</script>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>