<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <%--    <link rel="stylesheet"--%>
    <%--          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"--%>
    <%--          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"--%>
    <%--          crossorigin="anonymous">--%>
    <%--    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"--%>
    <%--            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"--%>
    <%--            crossorigin="anonymous"></script>--%>
    <%--    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"--%>
    <%--            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"--%>
    <%--            crossorigin="anonymous"></script>--%>
    <%--    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"--%>
    <%--            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"--%>
    <%--            crossorigin="anonymous"></script>--%>


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
                          class="form-text">Must be 8-20 characters long.</span>
                    </div>
                </div>

                <%--            <div class="mb-3 form-check">--%>
                <%--                <input type="checkbox" class="form-check-input" id="exampleCheck1">--%>
                <%--                <label class="form-check-label" for="exampleCheck1">Check me out</label>--%>
                <%--            </div>--%>
                <div style="text-align: center">
                    <button type="submit" class="btn btn-primary">Войти</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>