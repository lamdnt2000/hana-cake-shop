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
                            <li class="breadcrumb-item active">Product/ Product</li>
                        </ol>

                        <div class="row">
                            <div class="col-md-12">
                                <form action="DispatcherServlet" method="GET">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <button name="btAction" type="submit" value="DELETEPRODUCT" class="btn btn-danger" data-toggle="modal" data-target="delete">Delete</button>
                                        </div>
                                        <div class="col-md-4">
                                            <select class="custom-select" name="catId" id="category">
                                                <option value="0">Show All</option>
                                            </select>
                                        </div>
                                        <div class="col-md-2">
                                            <select class="custom-select" name="status" id="status">
                                                <option value="1"
                                                        <c:if test="${param.status.equals('1')}">selected</c:if>
                                                            >Active</option>
                                                        <option value="0" 
                                                        <c:if test="${param.status.equals('0')}">selected</c:if>
                                                            >Disable</option>

                                                </select>                                       
                                            </div>
                                            <div class="col-md-3 justify-content-end">
                                                <button name="btAction" type="submit" value="SEARCHPRODUCT" class="btn btn-primary">Search</button>
                                            </div>
                                        </div>

                                        <div class="row"></br></div>
                                        <div class="row">

                                            <div class="table-responsive">


                                                <table id="mytable" class="table table-bordred table-striped">

                                                    <thead>

                                                    <th><input type="checkbox" id="checkall" /></th>
                                                    <th>Name</th>
                                                    <th>Image</th>
                                                    <th>Category</th>
                                                    <th>Amount</th>
                                                    <th>Price</th>
                                                    <th>Time Created</th>
                                                    <th>Edit</th>
                                                    </thead>
                                                    <tbody>


                                                    </tbody>

                                                </table>



                                            </div>
                                        </div>
                                    </form> 
                                    <div class="row">
                                        <div class="col-sm-12 col-md-7">
                                            <input type="hidden" id="page" name="page" value="1"/>
                                            <div id="paper">
                                                <ul id="pagination" class="paginaion-sm">
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </main>

                <jsp:include page="footer.jsp"/>
            </div>

        </div>
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.twbsPagination.min.js" crossorigin="anonymous"></script>
        <Script>
            $(document).ready(function () {
                $("#mytable #checkall").click(function () {
                    if ($("#mytable #checkall").is(':checked')) {
                        $("#mytable input[type=checkbox]").each(function () {
                            $(this).prop("checked", true);
                        });

                    } else {
                        $("#mytable input[type=checkbox]").each(function () {
                            $(this).prop("checked", false);
                        });
                    }
                });
            });
            function getParam(param) {
                var url = new URL(window.location.href);
                var search = new URLSearchParams(url.search);
                return search.get(param);
            }
        </script>

        <script type="text/javascript">


        </script>
        <script type="text/javascript">
            $.ajax({
                type: "GET",
                url: "${applicationScope.PATHAPI}/ShowAllCategory",
                async: true,
                success: function (data) {

                    data.forEach(function (item) {
                        var catId = item.catId;
                        var catName = item.catName;
                        $('#category').append('<option value="' + catId + '" ' + ((catId == getParam('catId')) ? 'selected' : '') + ' >' + catName + '</option>');
                    });

                }
            });

        </script>

        <script>
            var catId;
            var status;
            var rowCount;
            $.ajax({
                type: "GET",
                url: "${applicationScope.PATHAPI}/GetDataForPaging",
                data: {
                    catId: getParam('catId'), status: getParam('status'), txtSearchName: getParam('txtSearchName'),
                    minPrice: getParam('minPrice'), maxPrice: getParam('maxPrice')
                },
                async: false,
                success: function (data) {
                    catId = data.catId;
                    status = data.status;
                    rowCount = data.totalProduct;
                }
            });
            if (rowCount > 0) {
                var startPage = $('#page').val();
                var rowPerPage = 20;
                var totalPage = Math.ceil(rowCount / rowPerPage);
                $('#pagination').twbsPagination({
                    startPage: parseInt(startPage),
                    totalPages: totalPage,
                    visiblePages: rowPerPage,

                    hideOnlyOnePage: true,
                    prev: '<span aria-hidden="true">&laquo;</span>',
                    next: '<span aria-hidden="true">&raquo;</span>',
                    onPageClick: function (event, page) {
                        totalPage = 50,
                                $("tbody").children().remove(),
                                loadAjax(page),
                                $('#page').val(page)
                    }

                });
            } else {
                $('tbody').append('<p>Product not found</p>');
            }
            function loadAjax(page) {
                var data = {};
                data['catId'] = getParam('catId');
                data['status'] = getParam('status');
                data['minPrice'] = getParam('minPrice');
                data['maxPrice'] = getParam('maxPrice');
                data['page'] = page;
                data['txtSearchName'] = getParam('txtSearchName');
                $.ajax({
                    type: "GET",
                    url: "${applicationScope.PATHAPI}/ShowAllProduct",
                    data: data,
                    async: true,
                    success: function (data) {
                        var searchCatId = $('#category').val();
                        var searchStatus = $('#status').val();
                        console.log(data);
                        if (data !== null) {
                            data.forEach(function (item) {
                                var productId = item.productId;
                                var productName = item.productName;
                                var image = item.image;
                                var category = item.catName;
                                var amount = item.amount;
                                var price = item.price;
                                var createDate = item.createDate;
                                $('tbody').append('<tr>')
                                        .append('<td><input type="checkbox" class="checkthis" name="productId" value="' + productId + '" /></td>')
                                        .append('<td>' + productName + '</td>')
                                        .append('<td class="w-25"><img src="${applicationScope.PATHMEDIA}/' + image + '" class="img-fluid img-thumbnail"/></td>')
                                        .append('<td>' + category + '</td>')
                                        .append('<td>' + amount + '</td>')
                                        .append('<td>' + price + '</td>')
                                        .append('<td>' + createDate + '</td>')
                                        .append('<td><a class="btn btn-primary" href="DispatcherServlet?btAction=GETDETAILPRODUCT&id=' + productId + '&catId=' + searchCatId + '&status=' + searchStatus + '">Update</a></td>')
                                        .append('</tr>');
                            });
                        }

                    }
                });
            }
        </script>
    </body>
</html>
