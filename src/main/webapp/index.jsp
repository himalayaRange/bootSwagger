<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/general_path.jsp"%>
<!DOCTYPE html>
<html>
<body>
<h2 style="color: orange;">使用SpringMVC + Swagger 构建RESTful API 文档</h2>
<a href="${ctx}/api">API 管理</a>
<hr/>
<form action="${ctx}/clientPOST" accept-charset="utf-8" method="post">
	<input type="hidden" name="id"   value="10"/>
	<input type="hidden" name="age"  value="18"/>
	<input type="text"   name="name" value="亚亚" autocomplete="off"/>
	<input type="password" name="passwd" value="123456" autocomplete="off"/>
	<input type="submit" value="登录系统"/>
</form>
</body>
</html>