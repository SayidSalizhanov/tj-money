<%@ include file="/templates/_header.jsp" %>
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

    <input type="hidden" name="groupId" value="${groupId}">
    <button type="submit">Сохранить запись</button>
</form>
</body>
</html>
