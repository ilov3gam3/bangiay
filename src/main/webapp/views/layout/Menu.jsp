<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>
<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dal.CartDao" %>
<%@ page import="model.Constant.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = request.getSession().getAttribute("acc") == null ? null : (User) request.getSession().getAttribute("acc");
%>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/home">Shoes</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
            <ul class="navbar-nav m-auto">
                <% if (user != null && user.getRole() == model.Constant.Role.ADMIN) { %>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/admin/users">Quản lý người dùng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/admin/add-product">Quản lý sản phẩm</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/admin/order">Quản lý đơn hàng</a>
                </li>
                <% } %>

                <% if (user != null) { %>
                <li class="nav-item">
                    <a class="nav-link" href="<%= user.getRole() == Role.CUSTOMER ? request.getContextPath() + "/customer/profile" : "#" %>">Hello <%= user.getUsername() %></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout">Logout</a>
                </li>
                <% } %>

                <% if (user != null && user.getRole() == model.Constant.Role.CUSTOMER) { %>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/customer/order">Đơn hàng của bạn</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/views/public/AI.jsp">AI tư vấn</a>
                </li>
                <% } %>

                <% if (user == null) { %>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/views/public/AI.jsp">AI tư vấn</a>
                </li>
                <% } %>
            </ul>

            <form action="search" method="post" class="form-inline my-2 my-lg-0">
                <div class="input-group input-group-sm">
<input value="${txtS}" name="txt" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search...">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-number">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
                    <%
                        List<Cart> carts;
                        if (user == null){
                            carts = request.getSession().getAttribute("cart") == null ? new ArrayList<>() : (ArrayList<Cart>) request.getSession().getAttribute("cart");
                        } else {
                            carts = new CartDao().getCartOfCustomer(user.getId());
                        }
                    %>
                <a class="btn btn-success btn-sm ml-3" href="<%=request.getContextPath()%>/cart">
                    <i class="fa fa-shopping-cart"></i> Cart
                    <span class="badge badge-light"><%=carts.size()%></span>
                </a>
            </form>
        </div>
    </div>
</nav>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Thanh Store</h1>
        <p class="lead text-muted mb-0">Uy tín tạo nên thương hiệu với hơn 10 năm cung cấp các sản phầm giày nhập từ Trung Quốc và USA</p>
    </div>
</section>
<!--end of menu-->