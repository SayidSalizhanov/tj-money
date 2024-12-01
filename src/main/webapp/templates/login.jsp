<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Добро пожаловать!</h1>
<h2>Вход</h2>

<form action="/login" method="post">
    <label for="email">Почта:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br><br>
    <button type="submit">Войти</button>
</form>

<c:if test="${not empty errorMessage}">
    <span style="color:red;">${errorMessage}</span><br>
</c:if>

<p>Ещё не зарегистрированы? <a href="/register">Регистрация</a></p>
</body>
</html>
