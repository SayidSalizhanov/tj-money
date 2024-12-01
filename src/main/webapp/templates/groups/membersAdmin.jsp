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
    <c:forEach var="member" items="${members}">
        <div>
            <p>Пользователь: ${member.getUsername()}</p>
            <p>Дата присоединения: ${member.getJoinedAt()}</p>
            <p>Роль: ${member.getRole()}</p>
        </div>
        <form action="/groups/${groupId}/membersAdmin" method="post" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="username" value="${member.getUsername()}">
            <button type="submit">Удалить</button>
        </form>
    </c:forEach>
</div>

</body>
</html>
