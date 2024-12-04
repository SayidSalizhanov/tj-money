<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserGroups</title>
</head>
<body>

<div>
    <form action="/user/applications" method="GET">
        <input type="hidden" name="userId" value="${userId}">

        <button type="submit">Мои заявки</button>
    </form>

    <form action="/groups/new" method="GET">
        <input type="hidden" name="userId" value="${userId}">

        <button type="submit">Создать группу</button>
    </form>

    <form action="/groups" method="GET">
        <input type="hidden" name="userId" value="${userId}">

        <button type="submit">Найти группу</button>
    </form>
</div>
<div class="placeholder">Placeholder для сортировки и выборов</div>
<div>
    <c:forEach var="group" items="${userGroupsDTOs}">
        <form action="/group" method="get">
            <div class="form">
                <input type="hidden" name="groupId" value="${group.getGroupId()}">
                <input type="hidden" name="userId" value="${userId}">

                <button type="submit">
                    <div>
                        <p>Название: ${group.getGroupName()}</p>
                        <p>Описание: ${group.getDescription()}</p>
                        <p>Роль: ${group.getRole()}</p>
                    </div>
                </button>
            </div>
        </form>
    </c:forEach>
</div>

</body>
</html>
