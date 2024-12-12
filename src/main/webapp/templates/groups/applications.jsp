<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div>
    <c:forEach var="application" items="${applications}">
        <div>
            <p>Пользователь: ${application.getUsername()}</p>
            <p>Дата: ${application.getSendAt()}</p>
        </div>
        <form action="/group/applications" method="post" style="display:inline;">
            <input type="hidden" name="username" value="${application.getUsername()}">
            <input type="hidden" name="applicationId" value="${application.getApplicationId()}">
            <input type="hidden" name="applicationStatus" value="одобрено">
            <input type="hidden" name="groupId" value="${groupId}">
            <button type="submit">Принять</button>
        </form>
        <form action="/group/applications" method="post" style="display:inline;">
            <input type="hidden" name="username" value="${application.getUsername()}">
            <input type="hidden" name="applicationId" value="${application.getApplicationId()}">
            <input type="hidden" name="applicationStatus" value="отклонено">
            <input type="hidden" name="groupId" value="${groupId}">
            <button type="submit">Отклонить</button>
        </form>
    </c:forEach>
</div>

</body>
</html>
