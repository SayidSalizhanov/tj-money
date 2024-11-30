<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 11.11.2024
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Records</title>
</head>
<body>

<h3>Записи</h3>

<div class="sort">
    <label>Сортировать:</label>
    <input type="text" placeholder="Поиск...">
    <select>
        <option>Название</option>
        <option>По алфавиту вверх</option>
        <!-- Добавьте другие заглушки по мере необходимости -->
    </select>
    <button>Применить</button> <!-- Заглушка для применения сортировки -->
</div>

<div>
    <c:forEach var="record" items="${records}">
        <div class="record">
            <p><a href="/records/${record.getId()}">Название: ${record.getName()}</a></p>
        </div>
    </c:forEach>
</div>

<form action="/records/new" method="GET">
    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Создать запись</button>
</form>

</body>
</html>
