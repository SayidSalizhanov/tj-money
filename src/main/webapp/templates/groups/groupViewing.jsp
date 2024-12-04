<%@ include file="/templates/_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div>
    <h1>Группа</h1>
    <p>id: ${group.getId()}</p>
    <p>${group.getName()}</p>
    <p>Создана ${group.getCreatedAt()}</p>
    <p>${group.getDescription()}</p>
    <p>Количество участников: ${membersCount}</p>
    <p>Админ: ${admin}</p>
</div>

</body>
</html>
