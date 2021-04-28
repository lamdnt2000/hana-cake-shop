<%-- 
    Document   : orderCart
    Created on : Jan 19, 2021, 10:42:16 PM
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

        <title>Shop Homepage </title>
        <!-- Custom styles for this template -->
        <link href="${applicationScope.PATHHOME}/css/shop-homepage.css" rel="stylesheet">
        <!-- Bootstrap core CSS -->
        <link href="${applicationScope.PATHHOME}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">


        <script src="${applicationScope.PATHADMIN}/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <c:set var="resultLogin" value="${sessionScope.RESULTLOGIN}"/>
        <c:if test="${not empty resultLogin}">
            <c:if test="${resultLogin.role == 1}">
                <c:redirect url="admin/index.jsp"/>
            </c:if>
        </c:if>
        <jsp:include page="header.jsp"/>


        <br>
        <div class="container">
            <c:set var="success" value="${requestScope.SUCCESS}"/>
            <c:set var="fail" value="${requestScope.FAIL}"/>
            <c:if test="${not empty success}">
                <br>
                <div class="alert alert-success" role="alert">
                    ${success}
                </div>
            </c:if>
            <c:if test="${not empty fail}">
                <br>
                <div class="alert alert-danger" role="alert">
                    ${fail}
                </div>
            </c:if>
            <c:if test="${not empty sessionScope}">
                <c:set var="cartResult" value="${sessionScope.PRODUCTCART}"/>
                <c:if test="${not empty cartResult}">
                    <c:set var="cartItem" value="${cartResult.items}"/>
                    <c:if test="${not empty cartItem}">

                        <div class="card">
                            <table class="table table-hover shopping-cart-wrap">

                                <thead class="text-muted">
                                    <tr>
                                        <th scope="col" width="370">Product</th>
                                        <th scope="col" width="180">Quantity</th>
                                        <th scope="col" width="120">Price</th>
                                        <th scope="col" class="text-right">Action</th>
                                    </tr>
                                </thead>

                            </table>
                            <c:forEach var="dto" items="${cartItem}">

                                <form action="DispatcherServlet" method="POST">
                                    <div class="row">

                                        <div class="col-md-4">
                                            <figure class="media">
                                                <div class="img-wrap"><img src="${applicationScope.PATHMEDIA}/${dto.key.image}" class="img-thumbnail" width="150px" height="150px"></div>
                                                <figcaption class="media-body">
                                                    <h6 class="title text-truncate">${dto.key.productName}</h6>
                                                    <dl class="param param-inline small">
                                                        <dt>Category: </dt>
                                                        <dd>${dto.key.catName}</dd>
                                                    </dl>
                                                    <dl class="param param-inline small">
                                                        <dt>Price: </dt>
                                                        <dd>${dto.key.price}</dd>
                                                    </dl>
                                                </figcaption>
                                            </figure> 
                                        </div>
                                        <div class="col-md-2"> 
                                            <input type="number" name="quantity" class="form-control"  value="${dto.value}"  min="1" max="99999" size="1"/>
                                            <input type="hidden" name="pk" value="${dto.key.productId}"/>
                                        </div>
                                        <div class="col-md-2">  
                                            <div class="price-wrap"> 
                                                <font color="blue"><var class="price">USD </var> <var class="price" id="price">${dto.key.price*dto.value}</var></font>
                                            </div> <!-- price-wrap .// -->
                                        </div>
                                        <div class="col-md-4 input-group">
                                            <input type="checkbox" class="form-control" name="id" value="${dto.key.productId}"  />
                                            <button name="btAction" class="form-control btn btn-primary" type="submit" value="UPDATECART">Update</button>
                                        </div>
                                    </div>
                                </form>
                            </c:forEach>


                        </div> 

                        <br>
                        <div class="row justify-content-around">

                            <div clas="col-md-5 ">
                                <button class="btn btn-danger" type="button" value="DELETECART" id="delete" disabled="true">Delete</button>
                            </div>
                            <div class="col-md-2">
                                <input type="checkbox" class="form-control" id="checkAll" />
                            </div>
                        </div>
                        <hr>
                        <div class="row justify-content-between">
                            <div clas="col-md-4 " id="total">
                                Total: <font color="red">${cartResult.totalPrice}</font>

                            </div>
                                <form action="DispatcherServlet" method="POST">
                            <div clas="col-md-3 ">
                                <button name="btAction" class="btn btn-success" type="submit" value="CHECKOUT">Check Out</button>
                            </div>
                                    </form>
                        </div>

                    </c:if>
                </c:if>
            </c:if>
            <c:if test="${empty sessionScope}">
                <p class="font-weight-bold">No Cart.</p>
            </c:if>
        </div>
        <br>


        <!-- Bootstrap core JavaScript -->
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.validate.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/additional-methods.min.js"></script>
        <script>
            function getCheck() {
                var flag = false;
                $('input[type=checkbox]').each(function () {
                    if ($(this).is(':checked')) {
                        flag = true;   // Set flag
                        return true; // Stop iterating
                    }
                });
                return flag;
            }

            $(document).ready(function () {
                $("#checkAll").click(function () {
                    if ($("#checkAll").is(':checked')) {
                        $("input[type=checkbox]").each(function () {
                            $(this).prop("checked", true);
                        });
                        $('#delete').prop('disabled', false);
                    } else {
                        $("input[type=checkbox]").each(function () {
                            $(this).prop("checked", false);
                        });
                        $('#delete').prop('disabled', true);
                    }
                });
            });
            $("input[type=checkbox]").click(function () {

                if (getCheck()) {
                    $('#delete').prop('disabled', false);
                } else {
                    $('#delete').prop('disabled', true);
                }
            });
            $('#delete').click(
                    function () {
                        var data = [];
                        $('input[type=checkbox][name=id]:checked').each(function () {
                            data.push($(this).val());
                        });

                        $.ajax({
                            type: "POST",
                            url: "DeleteCartServlet",
                            data: {"id": data},
                            async: false,
                            success: function (data) {
                                alert(data.message);
                                location.reload();
                            }
                        });
                    });
        </script>
    </body>
</html>




