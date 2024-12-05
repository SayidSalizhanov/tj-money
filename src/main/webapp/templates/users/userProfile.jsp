<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

<div class="info">
    <p>id: ${user.getId()}</p>
    <p>${user.getUsername()}</p>
    <p>${user.getEmail()}</p>

    <c:if test="${user.getTelegramId() == null}">
        <p>telegram: отсутствует</p>
    </c:if>

    <c:if test="${user.getTelegramId() != null}">
        <p>telegram: ${user.getTelegramId()}</p>
    </c:if>
</div>

<div>
    <form action="/user/settings" method="GET">

        <button type="submit">Настройки</button>
    </form>

    <form action="/user/groups" method="GET">

        <button type="submit">Группы</button>
    </form>

    <form action="/transactions" method="GET">

        <button type="submit">Транзакции</button>
    </form>
</div>

<hr>

<div>
    <c:forEach var="transaction" items="${transactions}">
        <div>
            <p>Финансы: ${transaction.getAmount()}</p>
            <p>Дата: ${transaction.getDateTime().toString()}</p>
            <p>Категория: ${transaction.getCategory()}</p>
            <p>Тип: ${transaction.getType()}</p>
        </div>
    </c:forEach>
</div>

<hr>

<div>
    <form action="/goals" method="GET">

        <button type="submit">Цели</button>
    </form>

    <form action="/reminders" method="GET">

        <button type="submit">Напоминания</button>
    </form>

    <form action="/records" method="GET">

        <button type="submit">Записи</button>
    </form>
</div>

<hr>

<form action="/user" method="post">
    <button type="submit">Выйти</button>
</form>

</body>
</html>
