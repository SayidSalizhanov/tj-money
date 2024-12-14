<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Группа</title>
    <link rel="stylesheet" href="/css/group/group.css">
</head>
<body>

<div class="content-wrapper">
    <div class="content">
        <div class="info">
            <h2>Информация о группе</h2>
            <p><strong>ID:</strong> ${group.getId()}</p>
            <p><strong>Название:</strong> ${group.getName()}</p>
            <p><strong>Создана:</strong> ${group.getCreatedAt()}</p>
            <p><strong>Описание:</strong> ${group.getDescription()}</p>
        </div>

        <div class="button-group">
            <form action="/group/settings" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="nav-button">Настройки</button>
            </form>

            <form action="/group/members" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="nav-button">Участники</button>
            </form>

            <form action="/transactions" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="nav-button">Транзакции</button>
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

        <hr><br>

        <div class="button-group">
            <form action="/goals" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="goal-button">Цели</button>
            </form>

            <form action="/reminders" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="reminder-button">Напоминания</button>
            </form>

            <form action="/records" method="GET">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit" class="record-button">Записи</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
