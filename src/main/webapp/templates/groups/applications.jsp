<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Заявки на участие</title>
    <link rel="stylesheet" href="/css/group/applications.css">
    <script src="/js/group/adminApplications.js" defer></script>
</head>
<body>

<div class="content-wrapper">
    <div class="content">
        <h2>Заявки на вступление</h2>
        <div class="applications-list">
            <c:forEach var="application" items="${applications}">
                <div class="application">
                    <p><strong>Пользователь:</strong> ${application.getUsername()}</p>
                    <p><strong>Дата:</strong> ${application.getSendAt()}</p>
                    <form action="/group/applications" method="post" class="action-form" id="approve-form-${application.getApplicationId()}">
                        <input type="hidden" name="username" value="${application.getUsername()}">
                        <input type="hidden" name="applicationId" value="${application.getApplicationId()}">
                        <input type="hidden" name="applicationStatus" value="Одобрено">
                        <input type="hidden" name="groupId" value="${groupId}">
                        <button type="submit" class="approve-button" id="approve-button-${application.getApplicationId()}">Принять</button>
                    </form>
                    <form action="/group/applications" method="post" class="action-form" id="reject-form-${application.getApplicationId()}">
                        <input type="hidden" name="username" value="${application.getUsername()}">
                        <input type="hidden" name="applicationId" value="${application.getApplicationId()}">
                        <input type="hidden" name="applicationStatus" value="Отклонено">
                        <input type="hidden" name="groupId" value="${groupId}">
                        <button type="submit" class="reject-button" id="reject-button-${application.getApplicationId()}">Отклонить</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
