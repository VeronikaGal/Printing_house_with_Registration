<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/10/2022
  Time: 1:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${not empty requestScope.orders}">
    <h1>Заказанные услуги:</h1>
    <ul>
        <c:forEach var="order" items="${requestScope.orders}">
            <li>${order.delivery}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>

