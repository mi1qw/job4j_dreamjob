<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mib1
  Date: 15.12.2020
  Time: 5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isErrorPage="true" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img style="align-content: center" src="images/error404.jpg" alt="1"><br>
Error 404
${pageContext.exception}
</body>
</html>
