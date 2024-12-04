<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Records</title>
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
    <c:forEach var="record" items="${records}">
        <form action="/record" method="get">
            <div class="form">
                <input type="hidden" name="recordId" value="${record.getId()}">
                <input type="hidden" name="userId" value="${userId}">
                <input type="hidden" name="groupId" value="${groupId}">

                <button type="submit">
                    <div>
                        <p>Название: ${record.getTitle()}</p>
                    </div>
                </button>
            </div>
        </form>
    </c:forEach>
</div>

<form action="/records/new" method="GET">
    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Создать запись</button>
</form>

</body>
</html>
