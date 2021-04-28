<%-- 
    Document   : menu
    Created on : Nov 28, 2020, 11:52:39 PM
    Author     : sasuk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:set var="user" value="${sessionScope.RESULTLOGIN}"/>
<c:if test="${not empty user}">
    <c:if test="${user.role==0}">
        <c:redirect url="../index.jsp"/>
    </c:if>
</c:if>
<c:if test="${empty user}">
    <c:redirect url="signin.html"/>
</c:if>
<div id="layoutSidenav_nav">

    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">
                <div class="nav-tabs"><div class="sb-sidenav-menu-heading"><font color="red">Welcome ${user.fullName}</font></div></div>

                <div class="sb-sidenav-menu-heading">Category Manage</div>
                <a class="nav-link" href="category.jsp">
                    <div class="sb-nav-link-icon"></div>
                    Category
                </a>

                <div class="sb-sidenav-menu-heading">Product Manage</div>
                <a class="nav-link" href="createproduct.jsp">
                    <div class="sb-nav-link-icon"></div>
                    Create product
                </a>
                <a class="nav-link" href="product.jsp">
                    <div class="sb-nav-link-icon"></div>
                    Product
                </a>
                <div class="sb-sidenav-menu-heading">Setting</div>
                <a class="nav-link" href="LogoutServlet">
                    <div class="sb-nav-link-icon"></div>
                    Log out
                </a>
            </div>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">${sessionScope.RESULTLOGIN.fullName}</div>
        </div>
    </nav>
</div>