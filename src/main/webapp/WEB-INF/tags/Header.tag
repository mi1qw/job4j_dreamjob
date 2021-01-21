<%@ tag pageEncoding="UTF-8" description="some description" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="disable" %>

<c:choose>
    <c:when test="${disable == 'true'}">
        ${pageContext.setAttribute('dsbl', 'nav-link disabled')}
    </c:when>
    <c:otherwise>
        ${pageContext.setAttribute('dsbl', 'nav-link text-white')}
    </c:otherwise>
</c:choose>

<img src="images/DyT.png" alt="1"><br>
<div class="mt-sm-3 d-flex flex-row bd-highlight" style="background-color: #879C97">
    <div class="p-2 bd-highlight align-self-center"><a
            class="${pageContext.getAttribute('dsbl')}"
            href="<c:url value="/post.do"/>">Вакансии</a></div>
    <div class="p-2 bd-highlight align-self-center"><a
            class="${pageContext.getAttribute('dsbl')}"
            href="<c:url value="/candidate.do"/>">Кандидаты</a></div>
    <div class="p-2 bd-highlight align-self-center"><a
            class="${pageContext.getAttribute('dsbl')}"
            href="<c:url value="/newpost.do"/>">Добавить вакансию</a></div>
    <div class="p-2 bd-highlight align-self-center"><a
            class="${pageContext.getAttribute('dsbl')}"
            href="<c:url value="/newcandidate.do"/>">Добавить кандидата</a>
    </div>
    <div class="p-2 bd-highlight align-self-center"><a
            class="${pageContext.getAttribute('dsbl')}"
            href="<c:url value="/imag.do"/>">Карта</a>
    </div>
    <div class="ms-auto p-2 bd-highlight align-self-center">
        <a class="${pageContext.getAttribute('dsbl')}"
                <c:choose>
                    <c:when test="${empty sessionScope.user.name}">
                        href="<c:url value="/login.jsp"/>" >
                        Гость | Войти &ensp;&ensp;
                    </c:when>
                    <c:otherwise>
                        href="<c:url value="/auth.do"/>" >
                        <c:out value="${sessionScope.user.name}"/> | Выйти &ensp;&ensp;
                    </c:otherwise>
                </c:choose>
        </a>
    </div>
</div>
