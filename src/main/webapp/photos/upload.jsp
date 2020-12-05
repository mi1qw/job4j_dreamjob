<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<%--<div class="container pt-3">--%>
<div class="container">
    <my:Header/>


    <div class="card-body">
        <form action="<%=request.getContextPath()%>/newcandidate.do?id=${requestScope.candidate.id}"
              method="post">
            <div class="form-group">
                <table align="center" border="1" cellpadding="25" cellspacing="1"
                       style="height: 200px; width: 700px">
                    <tbody>
                    <tr>
                        <td style="text-align:center;">
                            <img src="<c:url value='/download?name=${requestScope.photo}'/>"
                                 alt="photo"
                                 width="200px"
                                 height="200px"/>
                            <div class="nav-item">
                                <br>
                                <p>
                                    <a class="nav-link"
                                       href="${pageContext.servletContext.contextPath}/addphoto.do?id=${candidate.id}">Добавить
                                        <%--                                               href="${pageContext.servletContext.contextPath}/addphoto.do?id=${candidate}">Добавить--%>
                                        фото</a>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="form-group"><label>Загрузить фотогрфию<input
                                    class="form-control" name="name" type="text"
                                    value="${requestScope.candidate.name}"></label></div>
                            <div class="form-group"><label>Описание<input
                                    class="form-control" name="description" type="text"
                                    value="${requestScope.candidate.description}"></label>
                            </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </td>
                    </tr>
                    </tbody>

                </table>
            </div>
        </form>
    </div>


    <h2>Upload image !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!</h2>
    <div class="form-group">
        <p><input name="фото" type="button" value="кнопка значение">
            <input name="имя" type="submit" value="кнопка значение"></p>
    </div>
    <form action="<c:url value='/addphoto.do'/>" method="post"
          enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

</body>
</html>