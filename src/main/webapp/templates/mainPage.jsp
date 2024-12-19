<%@ include file="_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>MainPage</title>
    <link rel="stylesheet" href="/css/mainPage.css">
</head>
<body>
<h1>Статьи</h1>
<c:forEach var="article" items="${articles}">
    <div class="article">
        <h2>${article.title}</h2>
        <p class="content">${article.content}</p>
        <p><strong>Автор:</strong> ${article.author}</p>
        <p><strong>Опубликована:</strong> ${article.publishedAt}</p>
    </div>
</c:forEach>
</body>
</html>
