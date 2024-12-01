<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 11.11.2024
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reminder</title>
</head>
<body>

<h3>Редактирование транзакции</h3>

<form action="/reminders/${reminderId}" method="POST">
    <div class="form">
        <input type="hidden" name="_method" value="PUT">

        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${reminder.getTitle()}" required><br><br>

        <label for="datetime">Дата и время:</label><br>
        <input type="datetime-local" id="datetime" name="datetime" required><br><br>

        <p>Статус: ${reminder.getStatus()}</p>

        <label for="message">Содержание:</label><br>
        <textarea id="message" name="message" required>${reminder.getMessage()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/reminders/${reminderId}" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <button type="submit">Удалить транзакцию</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
