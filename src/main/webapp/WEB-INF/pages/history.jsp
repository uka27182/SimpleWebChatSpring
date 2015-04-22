<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>

        <spring:url value="/resources/css" var="resources_path"/>
        <link href="${resources_path}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
                        <h3><small>История</small></h3>
                        <form id="formHistory">
                            <div class="form-group">
                                <label for="dateFrom">Дата от</label>
                                <input type="date" class="form-control" id="dateFrom" pattern="201\d-[01]\d-[0123]\d" name="dateFrom" placeholder="дата с в формате (YYYY-MM-DD)" maxlength="25" required="required">
                            </div>
                            <div class="form-group">
                                <label for="dateTo">Дата до</label>
                                <input type="date" class="form-control" id="dateTo" pattern="201\d-[01]\d-[0123]\d" name="dateTo" placeholder="дата по в формате (YYYY-MM-DD)" maxlength="25" required="required">
                            </div>
                            <button type="submit" class="btn btn-primary" name="btnSend">Обновить</button>
                        </form>                            
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="well well-sm">    

                        <div class="well well-sm">
                            <h3><small>История</small></h3>
                            <div class="scroll" id="chatList">
                                <!-- list -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.container --> 

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${resources_path}/jquery/jquery-1.11.1.min.js"></script>
        <script src="${resources_path}/bootstrap/js/bootstrap.min.js"></script>
        <script src="${resources_path}/historyscripts.js"></script>
    </body>
</html>
