<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/11/2022
  Time: 12:24 PM
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
<h1>Список услуг:</h1>
<ul>
    <c:forEach var="services" items="${requestScope.services}">
        <li>
            <a href="${pageContext.request.contextPath}/orders?serviceId=${services.id}">${services.description}</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
