<%@ include file="/templates/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ChangePassword</title>
</head>
<body>

<form method="post" action="/user/changePassword">
    <label for="oldPassword">Старый пароль:</label>
    <input type="password" id="oldPassword" name="oldPassword"><br><br>

    <label for="newPassword">Новый пароль:</label>
    <input type="password" id="newPassword" name="newPassword"><br><br>

    <label for="repeatPassword">Повторите пароль:</label>
    <input type="password" id="repeatPassword" name="repeatPassword"><br><br>

    <c:if test="${not empty errorMessage}">
        <span style="color:red;">${errorMessage}</span><br>
    </c:if>

    <button type="submit">Сменить пароль</button><br><br>
</form>

</body>
</html>
