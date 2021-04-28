<%-- 
    Document   : signup
    Created on : Jan 6, 2021, 1:18:10 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <link href="${applicationScope.PATHHOME}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <c:set var="errors" value="${requestScope.errors}"/>
        <jsp:include page="header.jsp"/>
        <div class="container">
            <div class="col-md-6 text-center">
                <div class="header-title">
                    <h1 class="wv-heading--title">
                        Sign Up
                    </h1>

                </div>
            </div>
            <div class="row h-100 justify-content-center align-items-center">
                <form action="DispatcherServlet" method="POST" id="signupForm" class="col-10 col-md-8 col-lg-6">

                    <div class="form-group ">
                        <small class="text-dark">Username</small>
                        <input type="text" name="txtUsername"  class="form-control my-input" value="${param.txtUsername}">
                    </div>
                    <div class="form-group ">
                        <small class="text-dark">Full name</small>
                        <input type="text" name="txtFullName"  class="form-control my-input" value="${param.txtFullName}" >
                    </div>

                    <div class="form-group">
                        <small class="text-dark">Password</small>
                        <input id="txtPassword" type="password" name="txtPassword" class="form-control my-input" >
                    </div>
                    <div class="form-group ">
                        <small class="text-dark">Confirm Password</small>
                        <input id="txtConfirm" type="password" name="txtConfirm" class="form-control my-input">
                    </div>

                    <br>
                    <div class="form-group">
                        <button id="submit" type="submit" name="btAction" value="SIGNUP" class="btn btn-block btn-outline-primary">Create Your Free Account</button>
                    </div>
                    <c:set var="error" value="${requestScope.ERRORSIGNUP}"/>
                    <c:if test="${not empty error}">
                        <div id="message" class="p-3 mb-2 bg-danger text-white">${error}</div>
                    </c:if>
                   
                </form>
            </div>
        </div>

        <script src="${applicationScope.PATHHOME}/vendor/jquery/jquery.min.js"></script>
        <script src="${applicationScope.PATHHOME}/vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery-1.12.0.min.js" crossorigin="anonymous"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/jquery.validate.min.js"></script>
        <script src="${applicationScope.PATHRESOURCE}/js/additional-methods.js"></script>
        <script type="text/javascript">
            $().ready((function () {
                $('#signupForm').validate({
                    rules: {
                        txtEmail: {
                            required: true,
                            email: true
                        },
                        txtFullName: {
                            required: true,
                            rangelength: [6, 50]
                        },
                        txtPassword: {
                            required: true,
                            rangelength: [6, 50]
                        },
                        txtConfirm: {
                            equalTo: "#txtPassword"
                        }
                    }
                })
            })
                    );
            $('#submit').click(function () {
                if ($('#txtPassword').val() != "" && !$('#txtPassword').valid()) {
                    $('#txtPassword').val("");
                    $('#txtConfirm').val("");
                }

            });

        </script>
    </body>
    <jsp:include page="footer.html"/>
</html>
