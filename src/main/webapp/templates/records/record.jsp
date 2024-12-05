<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Record</title>
</head>
<body>

<form action="/record" method="post">
    <input type="hidden" name="_method" value="PUT">

    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="recordId" value="${recordId}">

    <div>
        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${record.getTitle()}" required><br><br>

        <p>Создана: ${record.getCreatedAt().toString()}</p>
        <p>Изменена: ${record.getUpdatedAt().toString()}</p>

        <label for="content">Содержание:</label><br>
        <textarea id="content" name="content" required>${record.getContent()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/record" method="post">
    <input type="hidden" name="_method" value="DELETE">

    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="recordId" value="${recordId}">

    <button type="submit">Удалить запись</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
