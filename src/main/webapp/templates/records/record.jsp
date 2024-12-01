<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Record</title>
</head>
<body>

<h3>Редактирование записи</h3>

<form action="/transactions/${recordId}" method="post">
    <input type="hidden" name="_method" value="PUT">

    <div>
        <label for="title">Название:</label><br>
        <input type="text" id="title" name="title" value="${record.getTitle()}" required><br><br>

        <p>Создана: ${record.getCreatedAt().toString()} Изменена: ${record.getUpdatedDate().toString()}</p>

        <label for="content">Содержание:</label><br>
        <textarea id="content" name="content" required>${record.getContent()}</textarea><br><br>

        <button type="submit">Сохранить изменения</button>
    </div>
</form>

<form action="/transactions/${recordId}" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <button type="submit">Удалить запись</button>
</form>

<button onclick="window.history.back();">← Назад</button>

</body>
</html>
