<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>newTransaction</title>
    <script src="/js/transaction/transactionCategory.js" defer></script>
</head>
<body>

<form action="/transactions/new" method="post">
    <div class="form">
        <label for="amount">Стоимость:</label><br>
        <input type="text" id="amount" name="amount" required><br><br>

        <label for="type">Тип:</label><br>
        <select id="type" name="type" required>
            <option value="Доход">Доход</option>
            <option value="Расход">Расход</option>
        </select><br><br>

        <label for="category">Категория:</label><br>
        <select id="category" name="category" required></select><br><br>

        <label for="datetime">Дата и время:</label><br>
        <input type="datetime-local" id="datetime" name="datetime" required><br><br>

        <label for="description">Описание:</label><br>
        <textarea id="description" name="description" required></textarea><br><br>

        <input type="hidden" name="groupId" value="${groupId}">

        <button type="submit" style="background-color: green; color: white;">Сохранить</button>
    </div>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
