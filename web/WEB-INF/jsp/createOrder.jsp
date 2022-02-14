<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/14/2022
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/createOrder" method="post">
    <label for="file">File:
        <input type="file" name="file" id="file">
    </label><br>
    <select name="paperType" id="paperType">
        <c:forEach var="paperType" items="${requestScope.paperType}">
            <option value="${paperType}">${paperType}</option>
        </c:forEach>
    </select><br>
    <label for="quantity">Quantity:
        <input type="number" name="quantity" id="quantity">
    </label><br>
    <c:forEach var="delivery" items="${requestScope.delivery}">
        <input type="radio" name="delivery" value="${delivery}"> ${delivery}
        <br>
    </c:forEach>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}<br></span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
