<%--
  Created by IntelliJ IDEA.
  User: sayid
  Date: 04.11.2024
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
</head>
<body>

<form action="/users/${userId}/settings" method="post">
    <input type="hidden" name="_method" value="PUT">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${user.getUsername()}"><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="${user.getPassword()}"><br><br>

    <label for="newPassword">Новый пароль:</label>
    <input type="password" id="newPassword" name="newPassword"><br><br>

    <label for="repeatPassword">Повторите пароль:</label>
    <input type="password" id="repeatPassword" name="repeatPassword"><br><br>

    <button type="button">Сменить пароль</button><br><br>

    <label for="telegramId">Telegram:</label>
    <input type="text" id="telegramId" name="telegramId" value="${user.getTelegramId()}"><br><br>

    <p>Хотите получать напоминания и другую информацию через телеграмм бота?</p>
    <input type="checkbox" id="sendingToTelegram" name="sendingToTelegram"><br>

    <p>Хотите получать напоминания и другую информацию на почту?</p>
    <input type="checkbox" id="sendingToEmail" name="sendingToEmail"><br><br>

    <button type="submit">Сохранить изменения</button>
</form>

<button type="button" onclick="window.history.back();">Назад</button>

<form action="/users/${userId}/settings" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <button type="submit">Удалить цель</button>
</form>

</body>
</html>
