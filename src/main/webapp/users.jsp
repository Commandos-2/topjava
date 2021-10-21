<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Pick Users</h2>
<table border="1" cellpadding="8" cellspacing="0">
    <c:forEach items="${users}" var="user">
        <jsp:useBean id="users" type="ru.javawebinar.topjava.model.User"/>
        <tr>
            <td><a href="meals?userId=${user.id}">${user.name}</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>