<%-- 
    Document   : createcategory
    Created on : Jan 8, 2021, 10:32:34 AM
    Author     : sasuk
--%>

<%-- 
    Document   : category
    Created on : Jan 7, 2021, 9:44:53 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - Category</title>
        <link href="${applicationScope.PATHADMIN}/css/styles.css" rel="stylesheet" />
        <link href="${applicationScope.PATHADMIN}/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="${applicationScope.PATHADMIN}/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.RESULTLOGIN}"/>
        <c:if test="${not empty user}">
            <c:if test="${user.role==0}">
                <c:redirect url="../index.jsp"/>
            </c:if>
        </c:if>
        <c:if test="${empty user}">
            <c:redirect url="signin.html"/>
        </c:if>

        <jsp:include page="header.html"/>
        <div id="layoutSidenav">
            <jsp:include page="menu.jsp"/>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Category/Category</li>
                        </ol>

                        <div class="row">
                            <div class="col-xl-4">

                                <form action="DispatcherServlet" method="POST" id="createCategory" >
                                    <small class="text-dark">Name</small><br>
                                    <input type="text" name="txtCatName" placeholder="Category Name" class="form-control form-control-sm" value="${param.txtCatName}">
                                    <br>
                                    <small class="text-dark">Description</small><br>
                                    <textarea class="form-control" name="txtDescription" placeholder="Description" rows="3" >${param.txtDescription}</textarea><br>
                                    <button name="btAction" type="submit" value="CREATECATEGORY" class="btn btn-primary">Create</button>

                                </form>
                                    <c:set var="success" value="${requestScope.CREATESUCCESS}"/>
                                <br>
                                <c:if test="${not empty success}">
                                    <div class="card bg-success text-white mb-4">
                                        <div class="card-body">${success}</div>

                                    </div>
                                </c:if>
                                <c:set var="fail" value="${requestScope.CREATEFAIL}"/>
                                <br>
                                <c:if test="${not empty fail}">
                                    <div class="card bg-danger text-white mb-4">
                                        <div class="card-body">${fail}</div>

                                    </div>
                                </c:if>
                            </div>
                            <div class="col-xl-8">
                                <table class="table" >
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Describe</th>
                                            <th scope="col">Time Create</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>
                </main>
                <jsp:include page="footer.jsp"/>
            </div>
        </div>
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.validate.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/additional-methods.min.js"></script>
        <script type="text/javascript">
            $.ajax({
                type: "GET",
                url: "${applicationScope.PATHAPI}/ShowAllCategory",
                async: true,
                success: function (data) {
                    var count = 0;
                    console.log(data);
                    if (data != null) {
                        data.forEach(function (item) {
                            var catName = item.catName;
                            var description = item.description;
                            var timeCreate = item.dateCreate;
                            $('tbody').append('<tr>')
                                    .append('<th scope="row">' + count + '</th>')
                                    .append('<td>' + catName + '</td>')
                                    .append('<td>' + description + '</td>')
                                    .append('<td>' + timeCreate + '</td>')
                                    .append('</tr>')
                                    ;
                        });
                    }
                }
            })

        </script>
        <script type="text/javascript">
            $().ready((function () {
                $('#createCategory').validate({
                    rules: {
                        txtCatName: {
                            required: true,
                            rangelength: [3, 20]
                        },
                    }
                })
            })
                    )
        </script>
    </body>
</html>
