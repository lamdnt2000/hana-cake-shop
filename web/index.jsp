<%-- 
    Document   : index
    Created on : Dec 1, 2020, 8:26:31 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Shop Homepage </title>

        <!-- Bootstrap core CSS -->
        <link href="${applicationScope.PATHHOME}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${applicationScope.PATHHOME}/css/shop-homepage.css" rel="stylesheet">
        <script src="${applicationScope.PATHADMIN}/js/all.min.js" crossorigin="anonymous"></script>

    </head>

    <body>
        <c:set var="resultLogin" value="${sessionScope.RESULTLOGIN}"/>
        <c:if test="${not empty resultLogin}">
            <c:if test="${resultLogin.role == 1}">
                <c:redirect url="admin/index.jsp"/>
            </c:if>
        </c:if>
        <!-- Navigation -->
        <jsp:include page="header.jsp"/>

        <!-- Page Content -->
        <div class="container">

            <div class="row">

                <div class="col-lg-12">
                    <form action="DispatcherServlet" id="searchForm" >
                        <div class="row">
                            <div class="col-md-4">
                                <h1 class="my-4">Search Option</h1>
                            </div>
                            <div class="col-md-8">

                            </div>
                            <input type="hidden" name="search" value="1"/>
                        </div>
                        <small class="text-muted">Name</small>
                        <input name="txtSearchName" id="txtSearchName" class="form-control" placeholder="Name of product" value="${param.txtSearchName}" >

                        <div class="row">
                            <div class="col-md-4">
                                <small class="text-muted">Range price</small>
                                <div class="input-group">

                                    <input id="minPrice" value="${param.minPrice}" type="number" name="minPrice" class="form-control" placeholder="0"  >

                                    <span>___</span>

                                    <input id="maxPrice" value="${param.maxPrice}" type="number" name="maxPrice" class="form-control" name="priceSecond" placeholder="1000000000"  >

                                </div>
                            </div>
                            <div class="col-md-8 justify-content-end">
                                <small class="text-muted">Category</small>
                                <select class="form-control" id="category" name="catId">
                                    <option value="0">All</option>
                                </select>
                            </div>
                        </div>


                        <hr>
                        <div class="row justify-content-around">
                            <div clas="col-md-4 ">
                                <input name="btAction" class="btn btn-primary" type="submit" value="Search Product">

                            </div>
                            <div clas="col-md-3 ">
                                <input type="reset" id="btnReset" class="btn btn-success" value="Reset"/>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- /.col-lg-3 -->

                <div class="col-lg-12">


                    <br>
                    <div class="row" id="showProduct">

                    </div>
                    <!-- /.row -->

                </div>
                <!-- /.col-lg-9 -->

            </div>
            <!-- /.row -->

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
        <!-- /.container -->

        <!-- Footer -->


        <!-- Bootstrap core JavaScript -->
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.twbsPagination.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.validate.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/additional-methods.min.js"></script>

        <script type="text/javascript">
            function getParam(param) {
                var url = new URL(window.location.href);
                var search = new URLSearchParams(url.search);
                return search.get(param);
            }
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
            var rowCount;
            $.ajax({
                type: "GET",
                url: "${applicationScope.PATHAPI}/GetDataForPaging",
                data: {
                    catId: getParam('catId'), status: 1, txtSearchName: getParam('txtSearchName'),
                    minPrice: getParam('minPrice'), maxPrice: getParam('maxPrice')
                },
                async: false,
                success: function (data) {
                    catId = data.catId;
                    rowCount = data.totalProduct;
                }
            });
            if (rowCount > 0) {
                var startPage = (getParam("page") === null) ? 1 : getParam("page");
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
                                $("#showProduct").children().remove(),
                                loadAjax(page),
                                $('#page').val(page)
                    }

                });
            } else {
                $('#showProduct').append('<p>Product not found</p>');
            }
            function loadAjax(page) {
                var data = {};
                data['catId'] = getParam('catId');
                data['status'] = 1;
                data['page'] = page;
                data['txtSearchName'] = getParam('txtSearchName');
                data['minPrice'] = getParam('minPrice');
                data['maxPrice'] = getParam('maxPrice');
                $.ajax({
                    type: "GET",
                    url: "${applicationScope.PATHAPI}/ShowAllProduct",
                    data: data,
                    async: true,
                    success: function (data) {

                        data.forEach(function (item) {
                            var productId = item.productId;
                            var productName = item.productName;
                            var image = item.image;
                            var category = item.catName;
                            var description = item.description;
                            var price = item.price;
                            var amount = item.amount;
                            var createDate = item.createDate;
                            $('#showProduct').append('<div class="col-lg-4  mb-4">\n\
                                            <div class="card h-100">\n\
                                            <img class="card-img-top rounded " src="${applicationScope.PATHMEDIA}/' + image + '" alt="" >\n\
                                            <div class="card-body"> <small class="text-muted">' + createDate + '</small>\n\
                                            <h2 class="card-title">' + productName + '</h2>\n\
                                            <h5 class="text-primary"><a href="#">' + category + '</a></h5>\n\
                                            <h6 class="text-info">$' + price + '</h6>\n\
                                            <p class="card-text">' + description + '</p>\n\
                                            <span class="text-danger">Amount: </span><span class="text-danger" id="amountProduct">' + amount + '</span></div>\n\
                                            <div class="card-footer">\n\
                                                <form action="#" id="addToCart'+productId+'">\n\
                                                <input type="hidden" value="' + productId + '" name="id"/>    \n\
                                             \n\
                                                <div class="row"><div class="col-sm-5   ">\n\
                                                    <input name="quantity" type="number" value="1" min="1" max="' + amount + '" class="form-control" size="4"/>\n\
                                                </div>\n\
                                                <div class="col-sm-7 ">\n\
                                                    <button type="button" onclick="addToCart(\'' + productId + '\')" class="btn btn-success" name="btAction" value="ADDTOCART">Add to cart</button>\n\
                                                </div>\n\
                                                 </form>\n\
                                            </div>\n\
                                            </div>\n\
                                            </div>\n\
                                            </div>');
                        });

                    }
                });
            }
        </script>

        <script type="text/javascript">
            $().ready((function () {
                $('#searchForm').validate({
                    rules: {
                        txtSearchName: {
                            rangelength: [0, 30]
                        },
                        minPrice: {
                            digits: true,
                            range: [0, 1000000]
                        },
                        maxPrice: {
                            digits: true,
                            min: function () {
                                return parseInt($('#minPrice').val());
                            },
                            max: 1000000
                        },
                        catId: {
                            required: true,
                            digits: true
                        },
                    }
                })
            })
                    )
            
             function addToCart(carId) {
                var form = $('#addToCart' + carId);
                var quantity = form.find('input[name="quantity"]').val();
                var btAction = form.find('input[name="btAction"]').val();
                $.ajax({
                    type: "POST",
                    url: "AddProductToCartServlet",
                    data: {
                        "id": carId,
                        "btAction":btAction,
                        "quantity": quantity
                    },
                    async: false,
                    success: function (data) {
                       alert(data.message);
                       return false;
                    }
                });
            }
            
            $().ready((function () {
                $('#orderForm').validate({
                    rules: {
                        orderAmount: {
                            required: true,
                            digits: true,
                            max: function () {
                                return parseInt($('#amountProduct').val());
                            }
                        }
                    }
                })
            })
                    )
            $('#btnReset').click(
                    function () {
                        
                        $('#txtSearchName').attr("value", "");
                        $('#minPrice').removeAttr("value");
                        $('#maxPrice').removeAttr("value");
                        $('#category option[selected]').removeAttr("selected").trigger('change');
                        
                        
                    })



        </script>


    </body>

</html>
