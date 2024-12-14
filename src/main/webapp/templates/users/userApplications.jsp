<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Applications</title>
    <link rel="stylesheet" href="/css/user/userApplications.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.5.0/font/bootstrap-icons.min.css">
    <script src="/js/user/deleteUserApplications.js" defer></script>
</head>
<body>

<div class="content">
    <div class="applications-list">
        <c:forEach var="application" items="${applicationsDTOs}">
            <div class="application">
                <div class="application-info">
                    <p>Название: ${application.getGroupName()}</p>
                    <p>Дата: ${application.getSendAt()}</p>
                    <p>Статус: ${application.getStatus()}</p>
                </div>
                <form action="/user/applications" method="post" class="delete-form">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="applicationId" value="${application.getId()}">
                    <button type="submit" class="delete-button" id="delete-button-${application.getId()}">
                        <i class="bi bi-trash"></i>
                    </button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
