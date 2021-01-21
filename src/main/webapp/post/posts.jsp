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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
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
            <div class="card border-white mb-3">
                <div class="card-header">
                    Вакансии
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col"></th>
                            <th scope="col">Объявления</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Город</th>
                            <th scope="col">Дата</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items='${requestScope.posts}' var='post'>
                            <tr>
                                <th scope="row">${post.id}
                                </th>
                                <td><img class="rounded-3 mx-auto d-block"
                                         src="<c:url
                                         value='/download?name=${requestScope.postsPhoto[post.photoId]}'/>"
                                         alt="photo"
                                         height="150px"/>
                                </td>
                                <td>
                                    <a href=${pageContext.servletContext.contextPath}/newpost.do?id=${post.id}>
                                        <em class="fa fa-edit mr-3"></em>
                                    </a> ${post.name}
                                </td>
                                <td>${post.description}
                                </td>
                                <td datatype="${post.cityId}"></td>
                                <td>
                                    <fmt:formatDate value="${post.created}" pattern="dd.MM.yyyy"/>
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
