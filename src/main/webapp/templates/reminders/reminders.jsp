<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 11.11.2024
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reminders</title>
</head>
<body>

<h3>Напоминания</h3>

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
    <c:forEach var="reminder" items="${reminders}">
        <div class="reminder">
            <a href="/reminders/${record.getId()}">
                <div>
                    <p>Название: ${transaction.getTitle()}</p>
                    <p>Статус: ${transaction.getStatus()}</p>
                </div>
            </a>
        </div>
    </c:forEach>
</div>

<button onclick="window.location.href='/reminders/new?userId=${userId}&groupId=${groupId}'">Создать запись</button>

</body>
</html>
