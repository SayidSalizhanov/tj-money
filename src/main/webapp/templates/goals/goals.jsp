<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goals</title>
</head>
<body>

<%--<div class="sort">--%>
<%--    <label>Сортировать:</label>--%>
<%--    <input type="text" placeholder="Поиск...">--%>
<%--    <select>--%>
<%--        <option>Название</option>--%>
<%--        <option>По алфавиту вверх</option>--%>
<%--        <!-- Добавьте другие заглушки по мере необходимости -->--%>
<%--    </select>--%>
<%--    <button>Применить</button> <!-- Заглушка для применения сортировки -->--%>
<%--</div>--%>

<div>
    <c:forEach var="goal" items="${goals}">
        <form action="/goal" method="get">
            <div class="form">
                <input type="hidden" name="goalId" value="${goal.getId()}">
                <input type="hidden" name="groupId" value="${groupId}">

                <button type="submit">
                    <div>
                        <p>Название: ${goal.getTitle()}</p>
                        <p>Статус: ${goal.getProgress()}</p>
                    </div>
                </button>
            </div>
        </form>
    </c:forEach>
</div>

<form action="/goals/new" method="GET">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Создать цель</button>
</form>

</body>
</html>
