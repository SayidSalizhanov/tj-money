<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Transaction</title>
    <link rel="stylesheet" href="/css/transactions/transaction.css">
</head>
<body>

<div class="content">
    <form action="/transaction" method="post" class="transaction-form">
        <div class="form">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="groupId" value="${groupId}">
            <input type="hidden" name="transactionId" value="${transactionId}">

            <label for="amount">Стоимость:</label><br>
            <input type="text" id="amount" name="amount" value="${transaction.getAmount()}" required><br><br>

            <label for="type">Тип:</label><br>
            <p id="type">${transaction.getType()}</p><br>
            <input type="hidden" name="type" value="${transaction.getType()}">

            <label for="category">Категория:</label><br>
            <p id="category">${transaction.getCategory()}</p><br>
            <input type="hidden" name="category" value="${transaction.getCategory()}">

            <label for="dateTime">Дата и время:</label><br>
            <p id="dateTime">${transaction.getDateTime()}</p><br>

            <c:if test="${not empty ownerName}">
                <label for="ownerName">Участник:</label><br>
                <p id="ownerName">${ownerName}</p><br>
            </c:if>

            <label for="description">Описание:</label><br>
            <textarea id="description" name="description" required>${transaction.getDescription()}</textarea><br><br>

            <button type="submit" class="save-button">Сохранить изменения</button>
        </div>
    </form>

    <form action="/transaction" method="POST" class="delete-form">
        <input type="hidden" name="_method" value="DELETE">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="transactionId" value="${transactionId}">

        <button type="submit" class="delete-button">Удалить транзакцию</button>
    </form>

    <button class="back-button" onclick="window.history.back();">← Назад</button>
</div>

</body>
</html>
