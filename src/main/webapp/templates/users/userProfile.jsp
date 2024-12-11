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
    <p>Доходы: ${income}</p>
    <p>Заработная плата: ${Заработная_плата}</p>
    <p>Прибыль от бизнеса: ${Прибыль_от_бизнеса}</p>
    <p>Дивиденты: ${Дивиденты}</p>
    <p>Аренда: ${Аренда}</p>
    <p>Премии и бонусы: ${Премии_и_бонусы}</p>
    <p>Интересы: ${Интересы}</p>
    <p>Пенсии и пособия: ${Пенсии_и_пособия}</p>
    <p>Другое: ${Другие_доходы}</p>
</div>

<hr>

<div>
    <p>Расходы: ${expense}</p>
    <p>Еда и напитки: ${Еда_и_напитки}</p>
    <p>Транспорт: ${Транспорт}</p>
    <p>Жилье: ${Жилье}</p>
    <p>Развлечения: ${Развлечения}</p>
    <p>Одежда: ${Одежда}</p>
    <p>Здоровье: ${Здоровье}</p>
    <p>Образование: ${Образование}</p>
    <p>Другое: ${Другие_расходы}</p>
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
