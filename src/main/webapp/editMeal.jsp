<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Table meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${meal.uuid}">
        <dl>
            <dt>Change date:</dt>
            <dd><input required type="datetime-local" name="datetime" size="50"
                       value="<%if(meal.getDateTime()!=null){%>${meal.dateTime}<%}%>"></dd>
        </dl>
        <dl>
            <dt>Change calories:</dt>
            <dd><input required type="number" name="calories" size="50" value="${meal.calories}"></dd>
        </dl>
        <dl>
            <dt>Change description:</dt>
            <dd><input required type="text" name="description" size="50" value="${meal.description}"></dd>
        </dl>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить изменения</button>
        <button type="reset" onclick="window.history.back()">Назад</button>
    </form>
</section>

</body>
</html>