<%@ include file="_header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MainPage</title>
</head>
<body>
<h1>Articles</h1>
<c:forEach var="article" items="${articles}">
    <div>
        <h2>${article.getTitle()}</h2>
        <p>${article.getContent()}</p>
        <p><strong>Author:</strong> ${article.getAuthor()}</p>
        <p><strong>Published:</strong> ${article.getPublishedAt()}</p>
    </div>
</c:forEach>
</body>
</html>
