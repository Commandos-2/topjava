<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="mealsTo" type="java.util.List<ru.javawebinar.topjava.model.MealTo>" scope="request"/>
    <title>Table meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="mealsEntry" items="${mealsTo}">
        <jsp:useBean id="mealsEntry" type="ru.javawebinar.topjava.model.MealTo"/>
        <fmt:parseDate value="${mealsEntry.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                       type="both"/>
        <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="yyyy-MM-dd HH:mm"/>
        <tr style="color:<%=(mealsEntry.isExcess()) ? "#cc0000" : "#006400"%>">
            <td>${dateTime}</td>
            <td>${mealsEntry.description}
            </td>
            <td>${mealsEntry.calories}
            </td>
            <td><a href="meals?uuid=${mealsEntry.uuid}&action=edit">Edit</a></td>
            <td><a href="meals?uuid=${mealsEntry.uuid}&action=delete">Delete</a></td>
        </tr>
    </c:forEach></table>
<h3><a href="meals?action=add">Add meal</a></h3>
</body>
</html>