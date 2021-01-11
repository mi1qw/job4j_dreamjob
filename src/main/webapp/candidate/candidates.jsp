<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ page isELIgnored="false" %>--%>


<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/listAjax.js"></script>
    <title>Работа мечты</title>
</head>
<body>
<div class="container">
    <my:Header/>
    <%--    <jsp:include page="/Header.jsp"/>--%>
    <div class="container pt-3">
        <div class="row">
            <div class="card" style="width: 100%">
                <div class="card-header">
                    Кандидаты
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">Названия</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Город</th>
                            <th scope="col">Дата</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items='${requestScope.candidates}' var='candidate'>
                            <tr>
                                <th scope="row">${candidate.id}
                                </th>
                                <td><img
                                        src="<c:url
                                        value='/download?name=${requestScope.candidatesPhoto[candidate.photoId]}'/>"
                                        alt="photo"
                                        width="100px"
                                        height="100px"/>
                                </td>
                                <td>
                                    <a href=${pageContext.servletContext.contextPath}/newcandidate.do?id=${candidate.id}>
                                        <em class="fa fa-edit mr-3"></em>
                                    </a> ${candidate.name}
                                </td>
                                <td>${candidate.description}
                                </td>
                                <td datatype="${candidate.cityId}"></td>
                                <td>
                                    <fmt:formatDate value="${candidate.created}"
                                                    pattern="dd.MM.yyyy"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
