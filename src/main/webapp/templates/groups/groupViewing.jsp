<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Группа</title>
    <link rel="stylesheet" href="/css/group/groupViewing.css">
</head>
<body>
<div class="content">
    <h1>Группа</h1>
    <div class="group-details">
        <p><strong>id:</strong> ${group.id}</p>
        <p><strong>Название:</strong> ${group.name}</p>
        <p><strong>Создана:</strong> ${group.createdAt}</p>
        <p><strong>Описание:</strong> ${group.description}</p>
        <p><strong>Количество участников:</strong> ${membersCount}</p>
        <p><strong>Админ:</strong> ${admin}</p>
    </div>
</div>
</body>
</html>
