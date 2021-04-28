<%-- 
    Document   : login
    Created on : Jan 6, 2021, 1:03:29 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign In</title>
        <link href="${applicationScope.PATHHOME}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <!------ Include the above in your HEAD tag ---------->

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">


    <br><br><br>
    <jsp:include page="header.jsp"/>
    <div class="container">

        <div class="row justify-content-md-center">

            <aside class="col-sm-5 ">


                <div class="card">
                    <article class="card-body">
                        <a href="signup.jsp" class="float-right btn btn-outline-primary">Sign up</a>
                        <h4 class="card-title mb-5 mt-2 float-left">Sign in</h4>


                        <form action="DispatcherServlet" method="POST">
                            <div class="form-group">
                                <input name="txtUsername" class="form-control" placeholder="Username" type="text">
                            </div> <!-- form-group// -->
                            <div class="form-group">
                                <input name="txtPassword" class="form-control" placeholder="******" type="password">
                            </div> <!-- form-group// -->   
                            <div class="form-group row">
                                <div class="col-md-6 ">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="remember"> Remember Me
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <c:set var="errorLogin" value="${requestScope.ERRORlOGIN}"/>
                            <c:if test="${not empty errorLogin}">
                                <div class="alert alert-danger" role="alert">
                                    ${errorLogin}
                                </div>
                            </c:if>
                            <hr>
                            <p>

                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/hanashop/login_google&response_type=code
                                   &client_id=900436680092-hi4vui9lv9unoj80o8ak3qnqd3d0kimg.apps.googleusercontent.com&approval_prompt=force" class="btn btn-block btn-outline-primary"> <i class="fa fa-google"></i>Sign up with Google</a>
                            </p>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <button name="btAction" type="submit" class="btn btn-primary btn-block" value="LOGIN">Login</button>
                                    </div> <!-- form-group// -->
                                </div>


                            </div> <!-- .row// -->                                                                  
                        </form>
                    </article>
                </div> <!-- card.// -->

            </aside> <!-- col.// -->

        </div> <!-- row.// -->

    </div> 
    <!--container end.//-->

    <br><br><br>

    <script src="${applicationScope.PATHHOME}/vendor/jquery/jquery.min.js"></script>
    <script src="${applicationScope.PATHHOME}/vendor/bootstrap/js/bootstrap.min.js"></script>

</html>
