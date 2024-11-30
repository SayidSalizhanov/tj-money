<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 05.11.2024
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Applications</title>
</head>
<body>

<h3>Поиск</h3>

<form action="" method="post">
    <input type="text" name="search" placeholder="Поиск...">
    <button type="submit">Поиск</button>
</form>

<h3>Группы</h3>
<c:forEach var="application" items="${applicationsDTOs}">
    <div class="application">
        <div class="application-info">
            <span>Название: ${application.getGroupName()}</span>
            <span>Дата: ${application.getSendAt()}</span>
            <span>Статус: ${application.getStatus()}</span>
        </div>
        <form action="/users/${userId}/groups/applications" method="post" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="applicationId" value="${application.getId()}">
            <button type="submit">Удалить</button>
        </form>
    </div>
</c:forEach>

</body>
</html>
