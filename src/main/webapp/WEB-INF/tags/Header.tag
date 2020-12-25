<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" description="some description" %>


<img src="images/DyT.jpg" alt="1"><br>      <%--"DyT.jpg" in root--%>
<h3 data-select-like-a-boss="1" style="text-align: center;">
        <span style="font-size: x-large; "><span style="font-family: Georgia, serif; "><span
                style="color: #B22222; "><em>Найм высококвалифицированных&quot;пинателей&quot;!</em>
        </span></span></span></h3>
<div class="row">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/post.do"/>">Вакансии</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/candidate.do"/>">Кандидаты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/newpost.do"/>">Добавить вакансию</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/newcandidate.do"/>">Добавить кандидата</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/imag.do"/>">Карта</a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/login.jsp"/>">
                <c:if test="${empty sessionScope.user.name}">
                    <c:out value="Гость "/> | Войти
                </c:if>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link"
               href="<c:url value="/auth.do"/>">
                <c:if test="${not empty sessionScope.user.name}">
                    <c:out value="${sessionScope.user.name}"/> | Выйти
                </c:if>
            </a>
        </li>
    </ul>
</div>
