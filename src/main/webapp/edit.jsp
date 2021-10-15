<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <title>Table meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <form method="get" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${mealTo.uuid}">
        <dl>
            <dt>Change date:</dt>
            <dd><input required type="date"  name="date" size="50" value="${mealTo.date}"></dd>
        </dl>
        <dl>
            <dt>Change time:</dt>
            <dd><input required type="time"  name="time" size="50" value="${mealTo.time}"></dd>
        </dl>
        <dl>
            <dt>Change calories:</dt>
            <dd><input required type="text"  name="calories" size="50" value="${mealTo.calories}"></dd>
        </dl>
        <dl>
            <dt>Change description:</dt>
            <dd><input required type="text" name="description" size="50" value="${mealTo.description}"></dd>
        </dl>
        <button type="submit">Сохранить</button>
        <button type="reset">Отменить изменения</button>
        <button type="reset" onclick="window.history.back()">Назад</button>
    </form>
</section>

</body>
</html>