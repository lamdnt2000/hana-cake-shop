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
        <title>Dashboard - product</title>
        <link href="${applicationScope.PATHADMIN}/css/styles.css" rel="stylesheet" />
        <script src="${applicationScope.PATHADMIN}/js/all.min.js" crossorigin="anonymous"></script>
        <style>
            #productForm label.error{
                display: block;
                width: 100%;
                color:red;
            }

        </style>
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
        <c:set var="result" value="${requestScope.PRODUCTDETAIL}"/>
        <c:if test="${empty result}">
            <c:redirect url="product.jsp?catId=${param.catId}&status=${param.status}"/>
        </c:if>
        <div id="layoutSidenav">
            <jsp:include page="menu.jsp"/>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Product/${result.productName}</li>
                        </ol>

                        <div class="row">
                            <div class="col-xl-10">
                                <c:set var="error" value="${requestScope.CREATEPRODUCTERR}"/>
                                <c:set var="resultParams" value="${requestScope.PARAMS}"/>
                                <form action="UpdateProductServlet" method="POST" enctype="multipart/form-data" id="productForm">
                                    <small class="text-dark">Name</small><br>
                                    <input type="text" name="txtProductName" placeholder="Product Name" class="form-control form-control-sm" value="${result.productName}">
                                    <input type="hidden" name="id" value="${param.id}"/>
                                    <input type="hidden" name="catIdSearch" value="${param.catId}"/>
                                    <input type="hidden" name="statusSearch" value="${param.status}"/>
                                    <small class="text-dark">Description</small><br>
                                    <textarea class="form-control" name="txtDescription" placeholder="Description" rows="3" >${result.description}</textarea><br>

                                    <small class="text-dark">Price</small><br>
                                    <input type="number" name="productPrice" placeholder="Product Price" class="form-control form-control-sm" value="${result.price}" step="0.001">

                                    <small class="text-dark">Amount</small><br>
                                    <input type="number" name="productAmount" placeholder="Product Amount" class="form-control form-control-sm" value="${result.amount}" step="1">

                                    <small class="text-dark">Category</small>
                                    <select class="custom-select" name="catId" id="category">
                                    </select>
                                    <small class="text-dark">Image</small>
                                    <div class="custom-file">
                                        <input name="image" type="file" class="custom-file-input" accept="image/x-png,image/gif,image/jpeg">
                                        <label class="custom-file-label" >Choose Image</label>
                                    </div>
                                    <input type="hidden" name="filename" value="${result.image}"/> 
                                    <br>

                                    <img src="${applicationScope.PATHMEDIA}/${result.image}" class="img-thumbnail"/>
                                    <br>
                                    <small class="text-dark">Status</small><br>
                                    <input type="checkbox" name="status"
                                           <c:if test="${result.status == 1}">
                                               checked="true"
                                           </c:if>
                                           id="status"/>
                                    <label for="status">Active</label><br><br>

                                    <button name="btAction" type="submit" value="UPDATEPRODUCT" class="btn btn-primary">Update</button>
                                </form>
                                <c:set var="fail" value="${requestScope.CREATEFAILED}"/>
                                <br>
                                <c:if test="${not empty fail}">
                                    <div class="card bg-danger text-white mb-4">
                                        <div class="card-body">${fail}</div>

                                    </div>
                                </c:if>
                            </div>

                        </div>

                    </div>
                </main>
                <jsp:include page="footer.jsp"/>
            </div>
        </div>
        <script src="${applicationScope.PATHADMIN}/js/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHADMIN}/js/scripts.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.validate.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/additional-methods.min.js"></script>
        <script type="text/javascript">
            $.ajax({
                type: "GET",
                url: "${applicationScope.PATHAPI}/ShowAllCategory",
                async: true,
                success: function (data) {
                    data.forEach(function (item) {
                        var catId = item.catId;
                        var catName = item.catName;
                        var selected = (catId == ${result.catId}) ? 'selected' : '';
                        $('#category').append('<option value="' + catId + '"' + selected + '>' + catName + '</option>');
                    });

                }
            });

        </script>
        <script type="text/javascript">
            $().ready((function () {
                $('#productForm').validate({
                    rules: {
                        txtProductName: {
                            required: true,
                            rangelength: [6, 30]
                        },
                        txtDescription: {
                            maxlength: 250
                        },
                        productPrice: {
                            required: true,
                            number: true,
                            range: [0, 1000000000]
                        },
                        productAmount: {
                            required: true,
                            range: [0, 10000]
                        },
                        catId: {
                            required: true,
                            digits: true
                        }
                    }
                })
            })
                    )
        </script>

    </body>
</html>
