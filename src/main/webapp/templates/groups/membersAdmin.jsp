<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Администратор участников</title>
    <link rel="stylesheet" href="/css/group/membersAdmin.css">
    <script src="/js/group/deleteMembers.js" defer></script>
</head>
<body>

<div class="content-wrapper">
    <div class="content">
        <form action="/group/applications" method="GET" class="applications-form">
            <input type="hidden" name="groupId" value="${groupId}">
            <button type="submit" class="nav-button">Заявки</button>
        </form>

        <h2>Участники группы</h2>
        <div class="members-list">
            <c:forEach var="member" items="${members}">
                <div class="member">
                    <p><strong>Пользователь:</strong> ${member.getUsername()}</p>
                    <p><strong>Дата присоединения:</strong> ${member.getJoinedAt()}</p>
                    <p><strong>Роль:</strong> ${member.getRole()}</p>
                    <c:if test="${member.getRole() == 'USER'}">
                        <form action="/group/members" method="post" class="delete-form" id="delete-form-${member.getUsername()}">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="username" value="${member.getUsername()}">
                            <input type="hidden" name="userId" value="${userId}">
                            <input type="hidden" name="groupId" value="${groupId}">
                            <button type="submit" class="delete-button" id="delete-button-${member.getUsername()}">Удалить</button>
                        </form>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
