<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/group/settings" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="hidden" name="groupId" value="${groupId}">

    <label for="name">Group name:</label>
    <input type="text" id="name" name="name" value="${group.getName()}"><br><br>

    <label for="description">Описание:</label>
    <textarea type="text" id="description" name="description">${group.getDescription()}</textarea><br><br>

    <button type="submit">Сохранить изменения</button>
</form>

<form action="/group/settings" method="POST">
    <input type="hidden" name="_method" value="DELETE">
    <input type="hidden" name="groupId" value="${groupId}">

    <button type="submit">Удалить группу</button>
</form>

</body>
</html>
