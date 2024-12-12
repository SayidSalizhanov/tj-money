<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Groups</title>
    <link rel="stylesheet" href="/css/group/groups.css">
</head>
<body>

<div class="content">
    <div class="groups-list">
        <c:forEach var="group" items="${groups}">
            <div class="group-wrapper">
                <form action="/group/viewing" method="get" class="group-form">
                    <div class="group">
                        <input type="hidden" name="groupId" value="${group.getId()}">
                        <button type="submit" class="group-button">
                            <div class="group-details">
                                <div class="left-column">
                                    <p>Название: ${group.getName()}</p>
                                </div>
                            </div>
                        </button>
                    </div>
                </form>
                <form action="/groups" method="post" class="apply-form">
                    <input type="hidden" name="groupId" value="${group.getId()}">
                    <button type="submit" class="apply-button">Подать заявку</button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
