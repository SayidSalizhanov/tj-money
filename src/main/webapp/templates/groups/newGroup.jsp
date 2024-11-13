<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Форма</title>
</head>
<body>
<form method="POST" action="MyServlet">
    <input type="hidden" name="param" value="${param}"> <!-- Скрытое поле для передачи параметра -->
    <label for="data">Введите данные:</label>
    <input type="text" id="data" name="data">
    <button type="submit">Отправить</button>
</form>
</body>
</html>