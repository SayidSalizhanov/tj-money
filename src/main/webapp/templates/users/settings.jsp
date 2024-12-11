<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
</head>
<body>

<form action="/user/settings" method="post">
    <input type="hidden" name="_method" value="PUT">

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${user.getUsername()}"><br><br>

    <label for="telegramId">Telegram:</label>
    <input type="text" id="telegramId" name="telegramId" value="${user.getTelegramId()}"><br><br>

    <label for="sendingToTelegram">Хотите получать напоминания и другую информацию через телеграмм бота?</label>
    <input type="checkbox" id="sendingToTelegram" name="sendingToTelegram"><br>

    <label for="sendingToEmail">Хотите получать напоминания и другую информацию на почту?</label>
    <input type="checkbox" id="sendingToEmail" name="sendingToEmail"><br><br>

    <button type="submit">Сохранить изменения</button>
</form>

<form action="/user/changePassword" method="GET">
    <button type="submit">Сменить пароль</button>
</form>

<form action="/user/settings" method="POST">
    <input type="hidden" name="_method" value="DELETE">

    <button type="submit">Удалить пользователя</button>
</form>

</body>
</html>
