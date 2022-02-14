<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/11/2022
  Time: 12:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br>
    <label>Phone Number:
        <input class="phoneNumber" id="phoneNumberId1" maxlength="5"/>
        <input class="phoneNumber" id="phoneNumberId2" maxlength="3"/>
        <input class="phoneNumber" id="phoneNumberId3" maxlength="2"/>
        <input class="phoneNumber" id="phoneNumberId4" maxlength="2"/>
    </label><br>
    <label for="addressId">Address:
        <input type="text" name="address" id="addressId">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId">
    </label><br>
    <label for="passwordId">Password:
        <input type="password" name="password" id="passwordId">
    </label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select><br>
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
