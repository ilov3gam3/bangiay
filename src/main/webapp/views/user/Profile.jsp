<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Customer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../layout/Head.jsp"%>
</head>
<body>
<%@include file="../layout/Menu.jsp"%>

<div class="container mt-5">
    <h2 class="mb-4">Thông tin cá nhân</h2>

    <%
        Customer customer = (Customer) request.getAttribute("customer");
    %>

    <!-- Form cập nhật thông tin -->
    <form action="${pageContext.request.contextPath}/customer/profile" method="post" class="mb-5">
        <div class="form-group">
            <label for="FullName">Họ và tên</label>
            <input type="text" class="form-control" name="FullName" id="FullName" value="<%= customer.getFullname() %>" required>
        </div>
        <div class="form-group">
            <label for="Email">Email</label>
            <input type="email" class="form-control" name="Email" id="Email" value="<%= customer.getEmail() %>" required>
        </div>
        <div class="form-group">
            <label for="PhoneNumber">Số điện thoại</label>
            <input type="text" class="form-control" name="PhoneNumber" id="PhoneNumber" value="<%= customer.getPhone() %>" required>
        </div>
        <div class="form-group">
            <label for="Address">Địa chỉ</label>
            <input type="text" class="form-control" name="Address" id="Address" value="<%= customer.getAddress() %>">
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
    </form>

    <h2 class="mb-4">Đổi mật khẩu</h2>

    <!-- Form đổi mật khẩu -->
    <form action="${pageContext.request.contextPath}/customer/change-password" method="post">
        <div class="form-group">
            <label for="password">Mật khẩu cũ</label>
            <input type="password" class="form-control" name="password" id="password" required>
        </div>
        <div class="form-group">
            <label for="newPassword">Mật khẩu mới</label>
            <input type="password" class="form-control" name="newPassword" id="newPassword" required>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Nhập lại mật khẩu mới</label>
            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" required>
        </div>
        <button type="submit" class="btn btn-warning">Đổi mật khẩu</button>
    </form>
</div>

<%@include file="../layout/Footer.jsp"%>
<%@include file="../layout/Foot.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
</body>
</html>
