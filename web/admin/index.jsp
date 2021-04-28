<%-- 
    Document   : index.jsp
    Created on : Nov 28, 2020, 8:52:17 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
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
        <c:redirect url="product.jsp"/>

    </body>
</html>
