<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
    <link rel="stylesheet" href="/css/user/profile.css">
</head>
<body>

<div class="content-wrapper">
    <div class="content">
        <div class="info-avatar">
            <div class="info">
                <h2>Информация о пользователе</h2>
                <p><strong>ID:</strong> ${user.id}</p>
                <p><strong>Имя пользователя:</strong> ${user.username}</p>
                <p><strong>Почта:</strong> ${user.email}</p>
                <c:if test="${user.telegramId == null}">
                    <p><strong>Telegram:</strong> отсутствует</p>
                </c:if>
                <c:if test="${user.telegramId != null}">
                    <p><strong>Telegram:</strong> ${user.telegramId}</p>
                </c:if>
            </div>
            <div class="avatar">
                <img src="${urlPhoto}" alt="Avatar" class="rounded-circle" width="200" height="200">
            </div>
        </div>

        <div class="button-group">
            <form action="/user/settings" method="GET">
                <button type="submit" class="nav-button">Настройки</button>
            </form>

            <form action="/user/groups" method="GET">
                <button type="submit" class="nav-button">Группы</button>
            </form>

            <form action="/transactions" method="GET">
                <button type="submit" class="nav-button">Транзакции</button>
            </form>
        </div>

        <hr>

        <form action="/user" method="GET" class="filter-form">
            <label for="incomePeriod">Выбрать период:</label>
            <select id="incomePeriod" name="period">
                <option value="all">Все время</option>
                <option value="day">День</option>
                <option value="month">Месяц</option>
                <option value="year">Год</option>
            </select>
            <button type="submit">Применить</button>
        </form>

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

        <div class="button-group-wrapper">
            <div class="button-group">
                <form action="/goals" method="GET">
                    <button type="submit" class="goal-button">Цели</button>
                </form>

                <form action="/reminders" method="GET">
                    <button type="submit" class="reminder-button">Напоминания</button>
                </form>

                <form action="/records" method="GET">
                    <button type="submit" class="record-button">Записи</button>
                </form>
            </div>

            <form action="/user" method="post">
                <button type="submit" class="logout-button">Выйти</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
