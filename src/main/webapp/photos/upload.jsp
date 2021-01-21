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
    <link rel="Stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/croppie.css">

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
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/upload.js"></script>
    <script src="${pageContext.request.contextPath}/script/croppie.js"></script>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
<div class="container">
    <my:Header disable="true"/>
    <br>
    <div class="card-body">
        <div class="row align-items-center">
            <div class="col-sm" style="text-align:center;" id="crop">
                <div id="some" class="rounded-3">
                    <div class="containerD">
                        <form action="${pageContext.request.contextPath}/addphoto.do"
                              method="post">
                            <img src="<c:url value='/download?name=${sessionScope.photo.name}'/>"
                                 class="rounded-3 mx-auto d-block"
                                 alt="photo"
                                 style="max-height: 550px;"
                            />
                            <button class="btnD" value="delete" name="delete"
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
                </div>
            </div>
            <div class="col-sm" style="text-align:center;">
                <div class="card-body" style="margin-left: 40px">
                    <h2>Upload image</h2>
                    <form action="<c:url value='/addphoto.do'/>"
                          enctype="multipart/form-data" method="post">
                        <div class="mb-3">
                            <input class="form-control" type="file" name="file">
                        </div>
                        <br>
                        <button hidden type="submit" class="btn btn-primary">Submit</button>
                        <div align="center">
                            <a id="send"
                                    <c:choose>
                                        <c:when test="${empty sessionScope.post}">
                                            href="${pageContext.request.contextPath}/newcandidate.do?id=${sessionScope.candidate.id}"
                                        </c:when>
                                        <c:otherwise>
                                            href="${pageContext.request.contextPath}/newpost.do?id=${sessionScope.post.id}"
                                        </c:otherwise>
                                    </c:choose>
                               class="btn btn-grey btn-raised">Готово</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
