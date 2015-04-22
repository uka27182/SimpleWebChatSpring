<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat</title>

        <spring:url value="/resources/css" var="resources_path" htmlEscape="false"/>
        <%--<link href="<spring:url value="${resources_path}/bootstrap/css/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet">--%>
        <link href="${resources_path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${resources_path}/styles.css" rel="stylesheet">

    </head>
    <body>
        <jsp:include page="masthead.jsp" />

        <div class="container" id="content">
            <div class="row">
                <div class="col-sm-4">
                    <div class="well well-sm">
                        <h3><small>Форма</small></h3>
                        <form id="formChat">
                            <div class="form-group">
                                <label for="userNikname">Nikname</label>
                                    <input type="text" class="form-control" id="userNikname" name="userNikName" placeholder="Введите Nikname" maxlength="25">
                            </div>
                            <div class="form-group">
                                <label for="textMessage">Текст сообщения</label>
                                <input type="text" class="form-control" id="textMessage" name="textMessage" placeholder="Напишите свой текст" maxlength="250" required="required">
                            </div>
                            <button type="submit" class="btn btn-primary" name="btnSend">Отправить</button>
                        </form>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="well well-sm">
                        <h3><small>Чат</small></h3>
                        <div class="scroll" id="chatList">
                            <!-- list -->
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.container -->        

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${resources_path}/jquery/jquery-1.11.1.min.js"></script>
        <script src="${resources_path}/bootstrap/js/bootstrap.min.js"></script>
        <script src="${resources_path}/chatscripts.js"></script>
    </body>
</html>
