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

    <link rel="stylesheet"
          href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">


</head>
<body>
<%--<div class="container pt-3">--%>
<div class="container">
    <my:Header/>


    <div class="card-body">
        <%--        <form action="<%=request.getContextPath()%>/newcandidate.do?id=${requestScope.candidate.id}"--%>
        <%--        <form action="<%=request.getContextPath()%>/addphoto.do?file="${pageContext.page}--%>
        <form action="${pageContext.request.contextPath}/addphoto.do"
              enctype="multipart/form-data"
              method="post">
            <div class="form-group">
                <table align="center" border="1" cellpadding="25" cellspacing="1"
                       style="height: 200px; width: 700px">
                    <tbody>
                    <tr>
                        <td style="text-align:center;">

                            <div class="containerD">
                                <img src="<c:url value='/download?name=${sessionScope.photo.name}'/>"
                                     alt="photo"
                                     width="200px"
                                     height="200px"
                                />
                                <button class="btnD">Delete</button>
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
                            <a class="btn btn-red btn-raised"
                               href="${pageContext.servletContext.contextPath}/newcandidate.do?photo=${requestScope.photo}">Delete</a>

                            <%--                            <button class="btn btn-red btn-raised">Кнопка</button>--%>

                            <div class="card-body">
                                <%--                                <a class="nav-link"--%>
                                <%--                                &lt;%&ndash;                                               href="${pageContext.servletContext.contextPath}/addphoto.do?id=${candidate.id}">Добавить&ndash;%&gt;--%>
                                <%--                                   href="${pageContext.servletContext.contextPath}/addphoto.do">Добавить--%>
                                <%--                                    фото</a>--%>


                                <h2>Upload image</h2>
                                <%--                                <form action="<c:url value='/addphoto.do'/>" method="post"--%>
                                <%--                                      enctype="multipart/form-data">--%>
                                <div class="checkbox">
                                    <input type="file" name="file">
                                </div>
                                <button type="submit" class="btn btn-default">Submit</button>

                                <%--                                </form>--%>


                                <%--                                <h2>Upload image</h2>--%>
                                <%--                                &lt;%&ndash;                                <form action="<c:url value='/newcandidate.do?file=${file}'/>"&ndash;%&gt;--%>
                                <%--                                <form action="<c:url value='/addphoto.do'/>"--%>
                                <%--                                      method="post"--%>
                                <%--                                      enctype="multipart/form-data">--%>
                                <%--                                    <div class="checkbox">--%>
                                <%--                                        <input type="file" name="file">--%>
                                <%--                                    </div>--%>
                                <%--                                    <button type="submit" class="btn btn-default">Submit</button>--%>
                                <%--                                </form>--%>
                            </div>


                            <%--                            <button type="submit" class="btn btn-primary">Сохранить</button>--%>
                        </td>

                    </tr>
                    </tbody>


                </table>


            </div>

        </form>
    </div>
    <div align="center">
        <%--                <form method="POST" action="${pageContext.request.contextPath}/addphoto.do">--%>
        <%--        <form action="${pageContext.request.contextPath}/newcandidate.do?id=${sessionScope.candidate.id}">--%>
        <%--        <form action="${pageContext.request.contextPath}/newcandidate.do?id=1">--%>


        <form id="someForm">
        </form>
        <button class="btn btn-primary" form="someForm" type="submit">жми сюда</button>
        <br>

        <a href="${pageContext.request.contextPath}/newcandidate.do?id=${sessionScope.candidate.id}"
           class="btn btn-primary">CANCEL</a>

    </div>

    <form action="${pageContext.request.contextPath}/newcandidate.do">
        <button class="btn btn-primary" type="submit">проба</button>
    </form>


    <%--    <form action="test1.php" method="POST">--%>
    <form action="${pageContext.request.contextPath}/newcandidate.do">
        <button class="button" value="srstrsrtsr" name="tests" type="submit"><img
                src="http://www.cyberforum.ru/images/cyberforum_logo.jpg"
                alt=""></button>
    </form>

    <%--    <h2>Upload image !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!</h2>--%>
    <%--    <div class="form-group">--%>
    <%--        <p><input name="фото" type="button" value="кнопка значение">--%>
    <%--            <input name="имя" type="submit" value="кнопка значение"></p>--%>
    <%--    </div>--%>
    <%--    ${requestScope.candidate.id}--%>
    <%--    <div class="card-body">--%>
    <%--        <form action="<c:url value='/addphoto.do?id=${requestScope.candidate.id}'/>" method="post"--%>
    <%--              enctype="multipart/form-data">--%>
    <%--            <div class="checkbox">--%>
    <%--                <input type="file" name="file">--%>
    <%--            </div>--%>
    <%--            <button type="submit" class="btn btn-default">Submit</button>--%>
    <%--        </form>--%>
    <%--    </div>--%>

    <%--    <div class="containerQ">--%>

</div>


<div class="containerD">
    <img src="<c:url value='/download?name=${sessionScope.photo.name}'/>"
         alt="photo"
         width="200px"
         height="200px"
    />
    <button class="btnD">Delete</button>
</div>

<div class="example">
    <div class="mt">
        <button class="btn btn-blue btn-raised">Кнопка</button>
        <button class="btn btn-green btn-raised">Кнопка</button>
        <button class="btn btn-orange btn-raised">Кнопка</button>
        <button class="btn btn-red btn-raised">Кнопка</button>
        <button class="btn btn-black btn-raised">Кнопка</button>
        <button class="btn btn-grey btn-raised">Кнопка</button>
        <button class="btn btn-white btn-raised">Кнопка</button>
    </div>
    <div class="mt">
        <button class="btn btn-blue btn-flat">Кнопка</button>
        <button class="btn btn-green btn-flat">Кнопка</button>
        <button class="btn btn-orange btn-flat">Кнопка</button>
        <button class="btn btn-red btn-flat">Кнопка</button>
        <button class="btn btn-black btn-flat">Кнопка</button>
        <button class="btn btn-grey btn-flat">Кнопка</button>
    </div>
    <div class="mt">
        <button class="btn btn-blue btn-fab"><em class="fa fa-plus"></em></button>
        <button class="btn btn-green btn-fab"><i class="fa fa-plus"></i></button>
        <button class="btn btn-orange btn-fab"><i class="fa fa-plus"></i></button>
        <button class="btn btn-red btn-fab"><i class="fa fa-plus"></i></button>
        <button class="btn btn-black btn-fab"><i class="fa fa-plus"></i></button>
        <button class="btn btn-grey btn-fab"><i class="fa fa-plus"></i></button>
        <button class="btn btn-white btn-fab"><i class="fa fa-plus"></i></button>
    </div>
    <div class="mt">
        <button class="btn btn-blue btn-icon"><i class="fa fa-plus"></i></button>
        <button class="btn btn-green btn-icon"><i class="fa fa-plus"></i></button>
        <button class="btn btn-orange btn-icon"><i class="fa fa-plus"></i></button>
        <button class="btn btn-red btn-icon"><i class="fa fa-plus"></i></button>
        <button class="btn btn-black btn-icon"><i class="fa fa-plus"></i></button>
        <button class="btn btn-grey btn-icon"><i class="fa fa-plus"></i></button>
    </div>
</div>


</body>
</html>