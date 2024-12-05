<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Applications</title>
</head>
<body>

<%--<form action="" method="post">--%>
<%--    <input type="text" name="search" placeholder="Поиск...">--%>
<%--    <button type="submit">Поиск</button>--%>
<%--</form>--%>

<hr>

<c:forEach var="application" items="${applicationsDTOs}">
    <div class="application">
        <div class="application-info">
            <span>Название: ${application.getGroupName()}</span>
            <span>Дата: ${application.getSendAt()}</span>
            <span>Статус: ${application.getStatus()}</span>
        </div>
        <form action="/user/applications" method="post" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="applicationId" value="${application.getId()}">

            <button type="submit">Удалить</button>
        </form>
    </div>
</c:forEach>

</body>
</html>
