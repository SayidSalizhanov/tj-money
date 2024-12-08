<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Reminder</title>
    <link rel="stylesheet" href="/css/reminders/reminder.css">
</head>
<body>

<div class="content">
    <form action="/reminder" method="POST" class="reminder-form">
        <div class="form">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="groupId" value="${groupId}">
            <input type="hidden" name="reminderId" value="${reminderId}">

            <label for="title">Название:</label><br>
            <input type="text" id="title" name="title" value="${reminder.getTitle()}" required><br><br>

            <label for="datetime">Дата и время:</label><br>
            <input type="datetime-local" id="datetime" name="datetime" required value="${reminder.getSendAt()}"><br><br>

            <label for="status">Статус:</label><br>
            <p id="status">${reminder.getStatus()}</p><br>

            <label for="message">Содержание:</label><br>
            <textarea id="message" name="message" required>${reminder.getMessage()}</textarea><br><br>

            <button type="submit" class="save-button">Сохранить изменения</button>
        </div>
    </form>

    <form action="/reminder" method="POST" class="delete-form">
        <input type="hidden" name="_method" value="DELETE">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="reminderId" value="${reminderId}">

        <button type="submit" class="delete-button">Удалить напоминание</button>
    </form>

    <button class="back-button" onclick="window.history.back();">← Назад</button>
</div>

</body>
</html>
