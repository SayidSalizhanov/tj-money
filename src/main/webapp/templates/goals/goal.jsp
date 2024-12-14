<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Goal</title>
    <link rel="stylesheet" href="/css/goals/goal.css">
</head>
<body>

<div class="content">
    <form action="/goal" method="POST" class="goal-form">
        <div class="form">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="groupId" value="${groupId}">
            <input type="hidden" name="goalId" value="${goalId}">

            <label for="title">Название:</label><br>
            <input type="text" id="title" name="title" value="${goal.getTitle()}" required><br><br>

            <label for="progress">Прогресс:</label><br>
            <input type="number" id="progress" name="progress" min="0" max="100" value="${goal.getProgress()}" required><br><br>

            <label for="description">Содержание:</label><br>
            <textarea id="description" name="description" required>${goal.getDescription()}</textarea><br><br>

            <button type="submit" class="save-button">Сохранить изменения</button>
        </div>
    </form>

    <form action="/goal" method="POST" class="delete-form">
        <input type="hidden" name="_method" value="DELETE">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="goalId" value="${goalId}">

        <button type="submit" class="delete-button">Удалить цель</button>
    </form>
</div>

</body>
</html>
