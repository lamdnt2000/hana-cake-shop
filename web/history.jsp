<%-- 
    Document   : index
    Created on : Dec 1, 2020, 8:26:31 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Hanashop History</title>

        <!-- Bootstrap core CSS -->
        <link href="${applicationScope.PATHHOME}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${applicationScope.PATHHOME}/css/shop-homepage.css" rel="stylesheet">
        <script src="${applicationScope.PATHADMIN}/js/all.min.js" crossorigin="anonymous"></script>

    </head>

    <body>

        <!-- Navigation -->
        <c:set var="user" value="${sessionScope.RESULTLOGIN}"/>      
        <c:if test="${empty user}">
            <c:redirect url="signup.html"></c:redirect>
        </c:if>
        <c:if test="${not empty user}">
            <c:if test="${user.role == 1}">
                <c:redirect url="admin/index.jsp"/>
            </c:if>
        </c:if>
        <jsp:include page="header.jsp"/>


        <!-- Page Content -->
        <div class="container">
            <br>
            <form action="" id="searchForm" >
                <div class="row">
                    <small class="text-muted">Name</small>
                    <input id="txtSearchName" name="txtSearchName" class="form-control" placeholder="Name of product" value="${param.txtSearchName}" >
                    <br>
                </div>
                <div class="row">
                    <small class="text-muted">Date Buy</small>
                    <div class="input-group">
                        <input id="dateBuy" value="${param.dateBuy}" type="date" class="form-control" name="dateBuy">
                    </div>
                </div>

                <br>
                <div class="row justify-content-around ">
                    <div clas="col-md-4">
                        <input class="btn btn-primary" type="submit" value="Search History">

                    </div>
                    <div clas="col-md-3 ">
                        <input id="reset" type="reset" class="btn btn-success" value="Reset"/>
                    </div>
                </div>
            </form>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <div class="card text-white bg-dark" id="showHistory">
                        <div class="card-header" >History</div>

                    </div>
                    <div id="message"class="text-dark"></div>
                </div>
            </div>
            <!-- /.row -->


        </div>
        <!-- /.container -->

        <!-- Footer -->


        <!-- Bootstrap core JavaScript -->
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.number.min.js" crossorigin="anonymous"></script>
        <script type="text/javascript">
            var now = new Date().getTime();
            $.ajax({
                type: "GET",
                url: "SearchHistory",
                data: {
                    "txtSearchName": $('#txtSearchName').val(),
                    "dateBuy": $('#dateBuy').val()
                },
                async: false,
                success: function (data) {
                    if (data != null) {
                        $('#message').html("");
                        data.forEach(function (item) {
                            var id = item.orderId;
                            var total = item.total;
                            var createDate = item.dateCreate;
                            var status = item.status;
                            $('#showHistory').append('<div class="card-body">\n\
                            <h5 class="card-title">Order Id: ' + id + '</a></h5>\n\
                                        <div class = "row">\n\
                                            <div class="col-md-2">\n\
                                                <p class="font-weight-bold text-success">OrderDate:</p>\n\
                                            </div>\n\
                                             <div class="col-md-6">\n\
                                                <p class="font-italic text-light">' + createDate + '</p>\n\
                                            </div>\n\
                                            <div class="col-md-2">\n\
                                                <p class="font-weight-bold text-danger">Total:</p>\n\
                                            </div>\n\
                                             <div class="col-md-2">\n\
                                                <p class="font-italic text-primary">' + total + ' VND</p>\n\
                                            </div>\n\
                                        </div>\n\  </div>\n\
                            <hr></div>');
                        });

                    } else {
                        $('#message').html("No result");
                    }
                }
            });
            $('#reset').click(function () {
                $('#txtSearchName').attr("value", "");
                $('#dateBuy').attr("value", "");
            });
        </script>


    </body>

</html>
