<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            crossorigin="anonymous"></script>
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3 " style="align-items: center">
    <my:Header/>
    <div class="row justify-content-md-center">
        <div class="card col-auto" style="padding: 30px;margin: 50px;border: 2px solid #3767f7;">
            <div class="card-header">
                Авторизация
            </div>
            <form action="${pageContext.request.contextPath}/auth.do" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Почта</label>
                    <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                           aria-describedby="emailHelp">
                    <div id="emailHelp" class="form-text">We'll never share your email with anyone
                        else.
                    </div>
                </div>
                <div class="mb-4">
                    <label for="exampleInputPassword1" class="form-label">Пароль</label>
                    <input type="password" class="form-control" name="password"
                           id="exampleInputPassword1" aria-describedby="passwordHelpInlinea">
                    <div class="col-auto">
                    <span id="passwordHelpInlinea"
                          class="form-text">Must be 2-20 characters long.</span>
                    </div>
                    <p class="text-danger">${requestScope.error}</p>
                </div>
                <div style="text-align: center">
                    <button type="submit" class="btn btn-primary">Войти</button>
                </div>
            </form>
            <div style="align-self: center"><br>
                <a href="${pageContext.request.contextPath}/register.do">Зарегистрироваться</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>