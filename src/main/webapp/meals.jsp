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
    </tr>
    <c:forEach var="mealsEntry" items="${mealsTo}">
        <jsp:useBean id="mealsEntry" type="ru.javawebinar.topjava.model.MealTo"/>
        <fmt:parseDate value="${mealsEntry.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                       type="both"/>
        <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="yyyy - dd MMM  - hh:mm"/>
        <tr style="color:<% if(mealsEntry.isExcess()){%> #cc0000<%}else {%> #006400<%}%>">
            <td>${dateTime}</td>
            <td><%=mealsEntry.getDescription()%>
            </td>
            <td><%=mealsEntry.getCalories()%>
            </td>
        </tr>
    </c:forEach></table>
</body>
</html>