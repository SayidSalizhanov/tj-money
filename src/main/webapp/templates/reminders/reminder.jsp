<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reminder</title>
</head>
<body>

<form action="/reminder" method="POST">
    <div class="form">
        <input type="hidden" name="_method" value="PUT">

        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="reminderId" value="${reminderId}">

        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${reminder.getTitle()}" required><br><br>

        <label for="datetime">Дата и время:</label><br>
        <input type="datetime-local" id="datetime" name="datetime" required value="${reminder.getSendAt()}"><br><br>

        <p>Статус: ${reminder.getStatus()}</p>

        <label for="message">Содержание:</label><br>
        <textarea id="message" name="message" required>${reminder.getMessage()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/reminder" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="reminderId" value="${reminderId}">

    <button type="submit">Удалить напоминание</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
