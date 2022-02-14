<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/13/2022
  Time: 11:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Список машин:</h1>
<ul>
    <c:forEach var="machines" items="${requestScope.services}">
        <li>
            <a href="${pageContext.request.contextPath}/orders?machineId=${machines.id}">${machines.description}</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>