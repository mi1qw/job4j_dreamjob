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
<%--<%--%>
<%--    String id = request.getParameter("id");--%>
<%--    Candidate candidate = new Candidate(0, "", "", new Date(), "noimages.png");--%>
<%--    if (id != null) {--%>
<%--        candidate = PsqlStore.instOf().findByIdCand(Integer.parseInt(id));--%>
<%--    }--%>
<%--%>--%>
<div class="container pt-3">
    <my:Header/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:if test=""
<%--                <% if (id == null) { %>--%>
                <%--                Новая вакансия.--%>
                <%--                <% } else { %>--%>
                <%--                Редактирование вакансии.--%>
                <%--                <% } %>--%>
            </div>
            <%--            <div class="card-body">--%>
            <%--                <form action="<%=request.getContextPath()%>/candidate.do?id=<%=candidate.getId()%>"--%>
            <%--                      method="post">--%>
            <%--                    <div class="form-group">--%>
            <%--                        <label>Имя--%>
            <%--                            <input type="text" class="form-control" name="name"--%>
            <%--                                   value="<%=candidate.getName()%>">--%>
            <%--                        </label>--%>
            <%--                    </div>--%>
            <%--                    <div class="form-group">--%>
            <%--                        <label>Описание--%>
            <%--                            <input type="text" class="form-control" name="description"--%>
            <%--                                   value="<%=candidate.getDescription()%>">--%>
            <%--                        </label>--%>
            <%--                    </div>--%>

            <%--                    &lt;%&ndash;                    ${param.put("2","one")}&ndash;%&gt;--%>
            <%--                    Добавить фото--%>
            <%--                    &lt;%&ndash;                    <form action=""&ndash;%&gt;--%>


            <%--                    &lt;%&ndash;            TODO    вывести фотона экран&ndash;%&gt;--%>
            <%--                    <div class="card-body">--%>
            <%--                        <div class="form-group">--%>
            <%--                            <label>Фото--%>
            <%--                                <input class="form-control" name="imgname" type="text"--%>
            <%--                                       value="<%=candidate.getDescription()%>">--%>
            <%--                                &lt;%&ndash;                                    <img src="<c:url value='/download?name=${candidatephoto}'/>"&ndash;%&gt;--%>
            <%--                                ${requestScope.size()}<br>--%>
            <%--                                ${requestScope.values()}<br>--%>
            <%--                                <br>--%>
            <%--                                ${pageContext.request.contextPath}<br>--%>
            <%--                                !!!!!!!!!!!!!!!!!<br>--%>
            <%--                                ${pageContext.request.getAttribute("candidate")}--%>
            <%--                                getAttribute("candidate")<br>--%>
            <%--                                ${pageContext.request.getParameter("id")} getParameter<br>--%>
            <%--                                ${id} id<br>--%>
            <%--                                ${paramValues}paramValues<br>--%>
            <%--                                ${param.keySet()}param.keySet<br>--%>
            <%--                                ${param.get("id")} param.get("id")<br>--%>
            <%--                                ${candidate}candidate<br>--%>

            <%--                                <c:forEach items="${param.values()}" var="n">--%>
            <%--                                    ${n}--%>
            <%--                                </c:forEach>--%>

            <%--                                <img src="<c:url--%>
            <%--                                value='/download?name=${candidate.photo}'/>"--%>
            <%--                                     alt="photo"--%>
            <%--                                     width="100px"--%>
            <%--                                     height="100px"/>--%>
            <%--                            </label>--%>
            <%--                            <p><input name="фото" type="button" value="кнопка значение">--%>
            <%--                                <input name="имя" type="submit" value="кнопка значение"></p>--%>
            <%--                            &lt;%&ndash;                                <jsp:include page="/photos/upload.jsp"/>&ndash;%&gt;--%>
            <%--                            &lt;%&ndash;                                <jsp:forward page="/photos/upload.jsp"/>&ndash;%&gt;--%>
            <%--                        </div>--%>
            <%--                    </div>--%>
            <%--                </form>--%>
            <%--            </div>--%>


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
