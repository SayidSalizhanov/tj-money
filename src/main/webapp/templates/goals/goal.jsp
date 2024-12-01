<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 08.11.2024
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goal</title>
</head>
<body>

<h3>Редактирование Цели</h3>

<form action="/goals/${goalId}" method="POST">
    <div class="form">
        <input type="hidden" name="_method" value="PUT">

        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${goal.getTitle()}" required><br><br>

        <label for="progress">Прогресс:</label><br>
        <input type="text" id="progress" name="progress" value="${goal.getProgress()}" required><br><br>

        <label for="description">Содержание:</label><br>
        <textarea id="description" name="description" required>${goal.getDescription()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/goals/${goalId}" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <button type="submit">Удалить цель</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
