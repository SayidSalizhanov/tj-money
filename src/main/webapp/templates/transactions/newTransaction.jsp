<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>newTransaction</title>
</head>
<body>

<form action="/transactions/new" method="post">
    <div class="form">
        <label for="amount">Стоимость:</label><br>
        <input type="text" id="amount" name="amount" required><br><br>

        <label for="category">Категория:</label><br>
        <input type="text" id="category" name="category" required><br><br>

        <label for="type">Тип:</label><br>
        <input type="text" id="type" name="type" required><br><br>

        <label for="datetime">Дата и время:</label><br>
        <input type="datetime-local" id="datetime" name="datetime" required><br><br>

        <label for="description">Описание:</label><br>
        <textarea id="description" name="description" required></textarea><br><br>

        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit" style="background-color: green; color: white;">Сохранить</button>
    </div>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
