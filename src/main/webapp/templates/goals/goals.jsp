<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 08.11.2024
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goals</title>
</head>
<body>

<h3>Цели</h3>

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
    <c:forEach var="goal" items="${goals}">
        <div class="goal">
            <a href="/goals/${goal.getId()}">
                <div>
                    <p>Название: ${goal.getTitle()}</p>
                    <p>Статус: ${goal.getProgress()}</p>
                </div>
            </a>
        </div>
    </c:forEach>
</div>

<form action="/goals/new" method="GET">
    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Создать запись</button>
</form>

</body>
</html>
