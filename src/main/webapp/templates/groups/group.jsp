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

<div class="financial-section">
    <div class="financial-info">
        <h2>Доходы</h2>
        <p><strong>Общий доход:</strong> ${income}</p>
        <p><strong>Заработная плата:</strong> ${Заработная_плата}</p>
        <p><strong>Прибыль от бизнеса:</strong> ${Прибыль_от_бизнеса}</p>
        <p><strong>Дивиденты:</strong> ${Дивиденты}</p>
        <p><strong>Аренда:</strong> ${Аренда}</p>
        <p><strong>Премии и бонусы:</strong> ${Премии_и_бонусы}</p>
        <p><strong>Интересы:</strong> ${Интересы}</p>
        <p><strong>Пенсии и пособия:</strong> ${Пенсии_и_пособия}</p>
        <p><strong>Другое:</strong> ${Другие_доходы}</p>
    </div>

    <div class="financial-info">
        <h2>Расходы</h2>
        <p><strong>Общие расходы:</strong> ${expense}</p>
        <p><strong>Еда и напитки:</strong> ${Еда_и_напитки}</p>
        <p><strong>Транспорт:</strong> ${Транспорт}</p>
        <p><strong>Жилье:</strong> ${Жилье}</p>
        <p><strong>Развлечения:</strong> ${Развлечения}</p>
        <p><strong>Одежда:</strong> ${Одежда}</p>
        <p><strong>Здоровье:</strong> ${Здоровье}</p>
        <p><strong>Образование:</strong> ${Образование}</p>
        <p><strong>Другое:</strong> ${Другие_расходы}</p>
    </div>
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
