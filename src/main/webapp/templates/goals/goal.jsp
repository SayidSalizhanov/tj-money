<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goal</title>
</head>
<body>

<form action="/goal" method="POST">
    <div class="form">
        <input type="hidden" name="_method" value="PUT">

        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="goalId" value="${goalId}">

        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${goal.getTitle()}" required><br><br>

        <label for="progress">Прогресс:</label><br>
        <input type="text" id="progress" name="progress" value="${goal.getProgress()}" required><br><br>

        <label for="description">Содержание:</label><br>
        <textarea id="description" name="description" required>${goal.getDescription()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/goal" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="goalId" value="${goalId}">

    <button type="submit">Удалить цель</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
