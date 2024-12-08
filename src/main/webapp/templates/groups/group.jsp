<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="info">
    <p>id: ${group.getId()}</p>
    <p>${group.getName()}</p>
    <p>${group.getCreatedAt()}</p>
    <p>${group.getDescription()}</p>
</div>

<div>
    <form action="/group/settings" method="GET">
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit">Настройки</button>
    </form>

    <form action="/group/members" method="GET">
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit">Участники</button>
    </form>

    <form action="/transactions" method="GET">
        <input type="hidden" name="groupId" value="${groupId}">

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
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit">Цели</button>
    </form>

    <form action="/reminders" method="GET">
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit">Напоминания</button>
    </form>

    <form action="/records" method="GET">
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit">Записи</button>
    </form>
</div>

</body>
</html>
