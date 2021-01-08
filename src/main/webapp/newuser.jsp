<%--
  Created by IntelliJ IDEA.
  User: mib1
  Date: 19.12.2020
  Time: 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Register new user</title>
    <link href="css/registr.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="login100-more"
             style="background-image: url('images/Steve-Carell.jpg');">
        </div>
        <div class="wrap-login100 p-l-50 p-r-50 p-t-72 p-b-50">
            <div class="row justify-content-md-center align-items-center"
                 style="height: 100%; width: 520px;--bs-gutter-x: 0rem;">
                <div class="card col-auto"
                     style="padding: 30px;margin: 50px;border: 2px solid #bdc78b;
                     border-radius: 30px;">
                    <p style="align-self: center">
                        РЕГИСТРАЦИЯ
                    </p>
                    <form action="${pageContext.request.contextPath}/register.do" method="post">
                        <div class="mb-2">
                            <label for="nameuser" class="form-label">Имя</label>
                            <input type="text" class="form-control" name="name"
                                   id="nameuser" required>
                        </div>
                        <div class="mb-4">
                            <label for="exampleInputEmail1" class="form-label">Почта</label>
                            <input type="email" class="form-control" name="email"
                                   id="exampleInputEmail1"
                                   aria-describedby="emailHelp" required>
                            <div id="emailHelp" class="form-text">We'll never share your email with
                                anyone
                                else.
                            </div>
                            <p class="text-danger">${requestScope.error}</p>
                        </div>
                        <div class="mb-2">
                            <label for="pwdId" class="form-label">Пароль</label>
                            <input type="password" class="form-control pwds" name="password"
                                   id="pwdId" pattern="^[a-zA-Z0-9]{2,20}$"
                                   aria-describedby="passwordHelpInlinea">
                            <div class="col-auto">
                                <span id="passwordHelpInlinea"
                                      class="form-text">Must be 2-20 characters long.</span>
                            </div>
                        </div>
                        <div class="mb-4">
                            <input type="password" class="form-control pwds" name="password"
                                   id="cPwdId" pattern="^[a-zA-Z0-9]{2,20}$"
                                   aria-describedby="passwordHelpInlinea2">
                            <div id="cPwdInvalid" class="invalid-feedback"></div>
                            <div class="col-auto">
                                <span id="passwordHelpInlinea2"
                                      class="form-text">Повторите пароль</span>
                            </div>
                        </div>
                        <div style="text-align: center">
                            <button id="submitBtn" style="background-color: #bdc78b;
                            border-color: #bdc78b"
                                    type="submit" class="btn btn-primary" disabled>Готово
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('#pwdId, #cPwdId').on('keyup', function () {
        if ($('#pwdId').val() != '' && $('#cPwdId').val() != '' && $('#pwdId').val() == $('#cPwdId').val()) {
            $("#submitBtn").attr("disabled", false);
            $('#cPwdInvalid').hide();
            $('.pwds').removeClass('is-invalid')
        } else {
            $("#submitBtn").attr("disabled", true);
            $('#cPwdInvalid').show();
            $('#cPwdInvalid').html('Не совпадает').css('color', 'red');
            $('.pwds').addClass('is-invalid')
        }
    });
</script>
</body>
</html>
