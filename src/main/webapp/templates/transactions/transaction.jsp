<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Transaction</title>
</head>
<body>

<form action="/transaction" method="post">
    <div class="form">
        <input type="hidden" name="_method" value="PUT">

        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="transactionId" value="${transactionId}">

        <label for="amount">Стоимость:</label><br>
        <input type="text" id="amount" name="amount" value="${transaction.getAmount()}" required><br><br>

        <label for="category">Категория:</label><br>
        <input type="text" id="category" name="category" value="${transaction.getCategory()}" required><br><br>

        <label for="type">Тип:</label><br>
        <input type="text" id="type" name="type" value="${transaction.getType()}" required><br><br>

        <label for="description">Описание:</label><br>
        <textarea id="description" name="description" required>${transaction.getDescription()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/transaction" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="transactionId" value="${transactionId}">

    <button type="submit">Удалить транзакцию</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
