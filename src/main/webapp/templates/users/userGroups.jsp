<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserGroups</title>
</head>
<body>

<div>
    <button class="button" onclick="location.href='/users/${userId}/groups/applications'">Мои заявки</button>
    <button class="button" onclick="location.href='/groups/new'">Создать группу</button>
    <button class="button" onclick="location.href='/groups'">Найти группу</button>
</div>
<div class="placeholder">Placeholder для сортировки и выборов</div>
<div>
    <c:forEach var="group" items="${userGroupsDTOs}">
        <a href="/groups/${group.getGroupId()}" class="group-entry">
            <div>Название: ${group.getGroupName()}</div>
            <div>Описание: ${group.getDescription()}</div>
            <div>Роль: ${group.getRole()}</div>
        </a>
    </c:forEach>
</div>

</body>
</html>
