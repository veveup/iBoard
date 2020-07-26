<%--
  Created by IntelliJ IDEA.
  User: veve
  Date: 2020/7/23
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@include file="base.jsp"%>
</head>
<body>

<h3> 执行成功 </h3>

${list}

<c:forEach items="${messages}" var="m">
    ${m.content}:${m.author}<br>
</c:forEach>

</body>
</html>
