<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<style>
    <%@include file='/WEB-INF/css/btn.css' %>
    <%@include file='/WEB-INF/css/del.css' %>
</style>


<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            crossorigin="anonymous"></script>


    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
<div class="container pt-3">
    <my:Header/>
    <br>
    <div class="card-body">
        <table align="center" border="1" cellpadding="25" cellspacing="1"
               style="height: 400px; width: 900px">
            <tbody>
            <tr>
                <td class="col-6" style="text-align:center;">
                    <div class="containerD">
                        <form id="some" action="${pageContext.request.contextPath}/addphoto.do"
                              method="post">
                            <img src="<c:url value='/download?name=${sessionScope.photo.name}'/>"
                            <%--                                 class="img-rounded"--%>
                            <%--                                 class="img-rounded"--%>
                            <%--                                 class="img-fluid— rounded mx-auto d-block"--%>
                            <%--                                 class="img-fluid"--%>
                                 class="rounded mx-auto d-block"
                            <%--                                 class="img-fluid img-thumbnail"--%>
                                 alt="photo"
                                 width="200px"
                                 height="200px"
                            />
                            <button form="some" class="btnD" value="delete" name="delete"
                                    type="submit">Delete
                            </button>
                        </form>
                    </div>
                    <br>
                    <div class="nav-item">
                        <p><a class="nav-link"
                              href="${pageContext.servletContext.contextPath}/download?name=${sessionScope.photo.name}">Скачать
                            фото
                        </a>
                        </p>
                    </div>
                </td>
                <td>
                    <div class="card-body" style="margin-left: 40px">
                        <h2>Upload image</h2>
                        <form action="<c:url value='/addphoto.do'/>"
                              enctype="multipart/form-data" method="post">
                            <div class="mb-3">
                                <input class="form-control" type="file" name="file">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div align="center">
        <c:choose>
            <c:when test="${empty sessionScope.post}">
                <a href="${pageContext.request.contextPath}/newcandidate.do?id=${sessionScope.candidate.id}"
                   class="btn btn-grey btn-raised">CANCEL</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/newpost.do?id=${sessionScope.post.id}"
                   class="btn btn-grey btn-raised">CANCEL</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>