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
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/rememberfiels.js">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/ajax.js"></script>
    <title>Работа мечты</title>
</head>
<body onload="writeFields()">
<div class="container">
    <my:Header/>
    <div class="container pt-3">
        <div class="row">
            <div class="card border-white mb-3" style="width: 100%">
                <div class="card-header fst-italic">
                    <c:choose>
                        <c:when test="${sessionScope.post.id==0}">
                            Новая вакансия
                        </c:when>
                        <c:otherwise>
                            Редактирование вакансии
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <table align="center" border="0" cellpadding="25" cellspacing="1"
                               style="height: 200px; width: 700px">
                            <tbody>
                            <tr>
                                <td style="text-align:center;">
                                    <img src="<c:url
                                value='/download?name=${sessionScope.photo.name}'/>"
                                         alt="photo"
                                         width="200px"
                                         height="200px"/>
                                    <div class="nav-item">
                                        <br>
                                        <p>
                                            <a class="nav-link" onclick="getFields()"
                                               href="${pageContext.servletContext.contextPath}/addphoto.do?photo=${sessionScope.photo.name}">добавить
                                                фото</a>
                                        </p>
                                    </div>
                                </td>
                                <td>
                                    <form id="reset" action="<c:url value="/newpost.do"/>"
                                          method="post">
                                    </form>
                                    <form action="<%=request.getContextPath()%>/post.do?id=${requestScope.post.id}"
                                          method="post" accept-charset="ISO-8859-1">
                                        <div class="form-group"><label>Имя
                                            <input class="form-control" name="name" type="text"
                                                   id="name"
                                                   value="${post.name}"></label></div>
                                        <div class="form-group"><label>Описание
                                            <input class="form-control" name="description"
                                                   type="text"
                                                   id="description"
                                                   value="${post.description}"></label>
                                        </div>
                                        <div class="form-group" id="citydiv"><label>Город
                                            <select class="form-select" id="city" name="city"
                                                    aria-label="Default select example">
                                                <option selected id="selected" value=${post.cityId}>
                                                    Укажите город
                                                </option>
                                            </select>
                                        </label>
                                        </div>
                                        <br>
                                        <button type="submit" class="btn btn-primary" id="submit"
                                                disabled>
                                            Сохранить
                                        </button>
                                        <button form="reset" type="submit" class="btn btn-light">
                                            Отмена
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <form style="text-align:center;" method="post"
                          action="<c:url value="/post.do?id=${requestScope.post.id}"/>">
                        <button class="btn btn-danger" type="submit" name="delete" value="delete">
                            Delete
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
