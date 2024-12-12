<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Members</title>
</head>
<body>

<div>
    <c:forEach var="member" items="${members}">
        <div>
            <p>Пользователь: ${member.getUsername()}</p>
            <p>Дата присоединения: ${member.getJoinedAt()}</p>
            <p>Роль: ${member.getRole()}</p>
        </div>
    </c:forEach>
</div>

</body>
</html>
