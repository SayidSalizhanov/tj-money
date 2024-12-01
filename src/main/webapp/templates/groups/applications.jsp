<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div>
    <label>Период:</label>
    <input type="radio" name="period" value="за месяц" checked> за месяц
    <input type="radio" name="period" value="за год"> за год<br><br>

    <label>Категория:</label>
    <select name="category">
        <option value="все" selected>все</option>
        <option value="категория1">Категория 1</option>
        <option value="категория2">Категория 2</option>
    </select>
    <br><br>

    <label>Тип:</label>
    <select name="type">
        <option value="все" selected>все</option>
        <option value="доход">Доход</option>
        <option value="расход">Расход</option>
    </select>
    <br><br>
</div>

<div>
    <c:forEach var="application" items="${applications}">
        <div>
            <p>Пользователь: ${application.getUsername()}</p>
            <p>Дата: ${application.getSendAt()}</p>
        </div>
        <form action="/groups/${groupId}/members/applications" method="post" style="display:inline;">
            <input type="hidden" name="username" value="${application.getUsername()}">
            <input type="hidden" name="applicationId" value="${application.getApplicationId()()}">
            <input type="hidden" name="applicationStatus" value="одобрено">
            <button type="submit">Принять</button>
        </form>
        <form action="/groups/${groupId}/members/applications" method="post" style="display:inline;">
            <input type="hidden" name="username" value="${application.getUsername()}">
            <input type="hidden" name="applicationId" value="${application.getApplicationId()()}">
            <input type="hidden" name="applicationStatus" value="отклонено">
            <button type="submit">Удалить</button>
        </form>
    </c:forEach>
</div>

</body>
</html>
