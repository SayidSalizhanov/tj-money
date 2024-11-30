<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 11.11.2024
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Record</title>
</head>
<body>
<form action="/records/new" method="post">
    <label for="title">Название:</label>
    <input type="text" id="title" name="title" required>
    <br>
    <label for="content">Содержание:</label>
    <textarea id="content" name="content" required></textarea>
    <br>

    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="groupId" value="${groupId}">
    <button type="submit">Сохранить запись</button>
</form>
</body>
</html>
