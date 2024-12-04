<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transactions</title>
</head>
<body>

<%--<div>--%>
<%--    <label>Период:</label>--%>
<%--    <input type="radio" name="period" value="за месяц" checked> за месяц--%>
<%--    <input type="radio" name="period" value="за год"> за год<br><br>--%>

<%--    <label>Категория:</label>--%>
<%--    <select name="category">--%>
<%--        <option value="все" selected>все</option>--%>
<%--        <option value="категория1">Категория 1</option>--%>
<%--        <option value="категория2">Категория 2</option>--%>
<%--        <!-- Добавьте другие категории по мере необходимости -->--%>
<%--    </select>--%>
<%--    <br><br>--%>

<%--    <label>Тип:</label>--%>
<%--    <select name="type">--%>
<%--        <option value="все" selected>все</option>--%>
<%--        <option value="доход">Доход</option>--%>
<%--        <option value="расход">Расход</option>--%>
<%--        <!-- Добавьте другие типы по мере необходимости -->--%>
<%--    </select>--%>
<%--    <br>--%>
<%--</div>--%>
<hr>
<div>
    <c:forEach var="transaction" items="${transactions}">
        <form action="/transaction" method="get">
            <div class="form">
                <input type="hidden" name="transactionId" value="${transaction.getTransactionId()}">
                <input type="hidden" name="userId" value="${userId}">
                <input type="hidden" name="groupId" value="${groupId}">

                <button type="submit">
                    <div>
                        <p>Финансы: ${transaction.getAmount()}</p>
                        <p>Дата: ${transaction.getDateTime().toString()}</p>
                        <p>Категория: ${transaction.getCategory()}</p>
                        <p>Тип: ${transaction.getType()}</p>
                    </div>
                </button>
            </div>
        </form>
    </c:forEach>
</div>
<hr>
<form action="/transactions/new" method="GET">
    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Создать транзакцию</button>
</form>

</body>
</html>
