<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>Сортировка</h3>
<form action="sortServlet" method="post">
    <label for="sortBy">Сортировать по:</label>
    <select id="sortBy" name="sortBy">
        <option value="name">Название</option>
    </select>
    <button type="submit">По алфавиту вверх</button>
</form>

<h3>Поиск</h3>

<form action="/groups" method="post">
    <input type="text" name="search" placeholder="Поиск...">
    <button type="submit">Поиск</button>
</form>

<h3>Группы</h3>
<c:forEach var="group" items="${groups}">
    <div class="group">
        <a href="/groups/${group.getId()}/viewing" class="group-class">
            <span>Название: ${group.getName()}</span>
        </a>
        <form action="/groups" method="post" style="display:inline;">
            <input type="hidden" name="groupId" value="${group.getId()}">
            <button type="submit">Подать заявку</button>
        </form>
    </div>
</c:forEach>

</body>
</html>
