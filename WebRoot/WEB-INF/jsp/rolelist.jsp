<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>用户管理页面</span>
            </div>
<!--             <div class="search"> -->
<%--            		<form method="post" action="${pageContext.request.contextPath }/provider/providerlist.html"> --%>
<!-- 					<input name="method" value="query" class="input-text" type="hidden"> -->
<!-- 					 <span>供应商编码：</span> -->
<%-- 					 <input name="proCode" class="input-text"	type="text" value="${proCode }"> --%>
					 
<!-- 					 <span>供应商名称：</span> -->
<%-- 					 <input name="proName" class="input-text"	type="text" value="${proName }"> --%>
<!-- 					 <input type="hidden" name="pageIndex" value="1"/> -->
<!-- 					 <input	value="查 询" type="submit" id="searchbutton"> -->
<%-- 					 <a href="${pageContext.request.contextPath}/jsp/useradd.jsp" >添加用户</a> --%>
<!-- 				</form> -->
<!--             </div> -->
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">角色编码</th>
                    <th width="20%">角色名称</th>
                    <th width="10%">创建时间</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="role" items="${roleList }" varStatus="status">
					<tr>
						<td>
						<span>${role.roleCode }</span>
						</td>
						<td>
						<span>${role.roleName }</span>
						</td>
						<td>
							<span>${role.creationDate}</span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:;" userid=${role.id } username=${role.roleName }><img src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" userid=${role.id } username=${role.roleName }><img src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="javascript:;" userid=${role.id } username=${role.roleName }><img src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
						</td>
					</tr>
				</c:forEach>
			</table>
<%--  			<input type="hidden" id="totalPageCount" value="${totalPageCount}"/> --%>
<%-- 		  	 <c:import url="rollpage.jsp"> --%>
<%-- 	          	<c:param name="totalCount" value="${totalCount}"/> --%>
<%-- 	          	<c:param name="currentPageNo" value="${currentPageNo}"/> --%>
<%-- 	          	<c:param name="totalPageCount" value="${totalPageCount}"/> --%>
<%--           	</c:import> --%>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/userlist.js"></script>