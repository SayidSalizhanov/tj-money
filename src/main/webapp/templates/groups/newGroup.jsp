<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Форма</title>
</head>
<body>
    <form action="/groups/new" method="post">
        <div class="form">
            <label for="groupName">Название группы:</label><br>
            <input type="text" id="groupName" name="name" required>

            <label for="description">Описание:</label><br>
            <textarea id="description" name="description" required></textarea>

            <button type="button" onclick="window.history.back();">← Назад</button>
            <input type="submit" value="Создать группу">
        </div>
    </form>
</body>
</html>