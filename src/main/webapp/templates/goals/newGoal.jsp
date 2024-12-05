<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NewGoal</title>
</head>
<body>

<form action="/goals/new" method="post">
    <div class="form">
        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="progress">Прогресс:</label><br>
        <input type="text" id="progress" name="progress" required><br><br>

        <label for="description">Содержание:</label><br>
        <textarea id="description" name="description" required></textarea><br><br>

        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit" style="background-color: green; color: white;">Сохранить цель</button>
    </div>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
