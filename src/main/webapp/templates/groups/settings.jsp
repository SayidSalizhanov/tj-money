<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/groups/${groupId}/settings" method="post">
    <input type="hidden" name="_method" value="PUT">

    <label for="name">Group name:</label>
    <input type="text" id="name" name="name" value="${group.getName()()}"><br><br>

    <label for="description">Описание:</label>
    <input type="text" id="description" name="description" value="${group.getDescription()}"><br><br>

    <button type="submit">Сохранить изменения</button>
</form>

<button type="button" onclick="window.history.back();">Назад</button>

<form action="/groups/${groupId}/settings" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <button type="submit">Удалить группу</button>
</form>

</body>
</html>
