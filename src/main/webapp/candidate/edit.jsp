<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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

    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <my:Header/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:choose>
                    <c:when test="${sessionScope.candidate.id==0}">
                        Новая вакансия.
                    </c:when>
                    <c:otherwise>
                        Редактирование вакансии.
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-body">

                <%--                TODO поменять на EL--%>
                <%--                FIXME--%>
                <form action="<%=request.getContextPath()%>/candidate.do?id=${requestScope.candidate.id}"
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
                                            <%--                                               href="${pageContext.servletContext.contextPath}/addphoto.do?id=${candidate.id}">Добавить--%>
                                               href="${pageContext.servletContext.contextPath}/addphoto.do?photo=${requestScope.photo}">Добавить
                                                фото</a>
                                        </p>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group"><label>Имя<input
                                            class="form-control" name="name" type="text"
                                            value="${candidate.name}"></label></div>
                                    <div class="form-group"><label>Описание<input
                                            class="form-control" name="description" type="text"
                                            value="${candidate.description}"></label>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Сохранить</button>
                                </td>
                            </tr>
                            </tbody>

                        </table>
                    </div>
                </form>
            </div>


            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidate.do?id=${requestScope.candidate.id}"
                      method="post">
                    <div class="form-group">
                        <label>Имя
                            <input type="text" class="form-control" name="name"
                                   value="${requestScope.candidate.name}">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>Описание
                            <input type="text" class="form-control" name="description"
                                   value="${requestScope.candidate.description}">
                        </label>
                    </div>


                    Добавить фото
                    ${requestScope.keySet()}<br>
                    ${requestScope.values()}<br>
                    ${sessionScope.candidateSS}<br>

                    <div class="card-body">

                        <label>Фото <br>
                            <img src="<c:url value='/download?name=${requestScope.photo}'/>"
                                 alt="photo"
                                 width="100px"
                                 height="100px"/>
                        </label>
                        <p><input name="фото" type="button" value="кнопка значение">
                            <input name="имя" type="submit" value="кнопка значение"></p>
                        <%--                                <jsp:include page="/photos/upload.jsp"/>--%>
                        <%--                                <jsp:forward page="/photos/upload.jsp"/>--%>
                    </div>
                </form>
            </div>


            <form id="rendered-form">
                <div class="rendered-form">
                    <div class=""><h1 access="false" id="control-5948300">Header</h1></div>
                    <div class=""><p access="false" id="control-256395">Paragraph</p></div>
                    <div class="formbuilder-file form-group field-file-1606997307640">
                        <label for="file-1606997307640" class="formbuilder-file-label">
                            File Upload
                        </label>
                        <input type="file" class="form-control"
                               name="file-1606997307640" access="false"
                               multiple="false" id="file-1606997307640">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
