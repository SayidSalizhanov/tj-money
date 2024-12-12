<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MembersAdmin</title>
</head>
<body>

<form action="/group/applications" method="GET">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Заявки</button>
</form>

<div>
    <c:forEach var="member" items="${members}">
        <div>
            <p>Пользователь: ${member.getUsername()}</p>
            <p>Дата присоединения: ${member.getJoinedAt()}</p>
            <p>Роль: ${member.getRole()}</p>
        </div>
        <c:if test="${member.getRole() == 'USER'}">
            <form action="/group/members" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="DELETE">
                <input type="hidden" name="username" value="${member.getUsername()}">
                <input type="hidden" name="userId" value="${userId}">
                <input type="hidden" name="groupId" value="${groupId}">
                <button type="submit">Удалить</button>
            </form>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
