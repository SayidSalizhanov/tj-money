<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Reminder</title>
</head>
<body>

<form action="/reminders/new" method="post">
    <div class="form">
        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="datetime">Дата и время отправки:</label><br>
        <input type="datetime-local" id="datetime" name="datetime" required><br><br>

        <label for="message">Содержание:</label><br>
        <textarea id="message" name="message" required></textarea><br><br>

        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit" style="background-color: green; color: white;">Сохранить</button>
    </div>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
