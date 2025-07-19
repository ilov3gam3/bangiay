<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../layout/Head.jsp" %>
    <style>
        .d-none {
            display: none;
        }
    </style>
</head>
<body>
<%@include file="../layout/Menu.jsp" %>
<div class="container mt-4">
    <h2>Quản lý Tài khoản</h2>

    <!-- Nút toggle -->
    <div class="mb-3">
        <button class="btn btn-primary" onclick="showTable('user')">Tài khoản</button>
        <button class="btn btn-info" onclick="showTable('customer')">Khách hàng</button>
        <button class="btn btn-success float-right" data-toggle="modal" data-target="#addUserModal"
                onclick="openAddForm()">Thêm mới
        </button>
    </div>

    <!-- Bảng User -->
    <div id="userTable">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                for (User u : users) {
                    Customer temp = null;
                    if (u.getRole() == Role.CUSTOMER){
                        for (int i = 0; i < customers.size(); i++) {
                            if (Objects.equals(customers.get(i).getId(), u.getId())){
                                temp = customers.get(i);
                                break;
                            }
                        }
                    }
            %>
            <tr>
                <td><%= u.getId() %>
                </td>
                <td><%= u.getUsername() %>
                </td>
                <td><%= u.getRole() %>
                </td>
                <td>
                    <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#editUserModal"
                            onclick="populateEditForm(
                                    '<%= u.getId() %>',
                                    '<%= u.getUsername() %>',
                                    '<%= u.getRole() %>',
                                    <%= u.getRole() == Role.CUSTOMER %>,
                                    '<%= temp != null ? temp.getFullname() : "" %>',
                                    '<%= temp != null ? temp.getEmail() : "" %>',
                                    '<%= temp != null ? temp.getPhone() : "" %>',
                                    '<%= temp != null ? temp.getAddress() : "" %>'
                                    )">
                        Sửa
                    </button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- Bảng Customer -->
    <div id="customerTable" class="d-none">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>SĐT</th>
                <th>Địa chỉ</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Customer c : customers) {
            %>
            <tr>
                <td><%= c.getUser().getId() %>
                </td>
                <td><%= c.getFullname() %>
                </td>
                <td><%= c.getEmail() %>
                </td>
                <td><%= c.getPhone() %>
                </td>
                <td><%= c.getAddress() %>
                </td>
                <td>
                    <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#editUserModal"
                            onclick="populateEditForm(
                                    '<%= c.getUser().getId() %>',
                                    '<%= c.getUser().getUsername() %>',
                                    '<%= c.getUser().getRole() %>',
                                    <%= c.getUser().getRole() == Role.CUSTOMER %>,
                                    '<%= c.getFullname() %>',
                                    '<%= c.getEmail() %>',
                                    '<%= c.getPhone() %>',
                                    '<%= c.getAddress() %>'
                                    )">
                        Sửa
                    </button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal thêm -->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <form action="<%= request.getContextPath() %>/admin/users" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm tài khoản</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Tên đăng nhập</label>
                        <input type="text" class="form-control" name="user" required>
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu</label>
                        <input type="password" class="form-control" name="pass" required value="">
                    </div>
                    <div class="form-group">
                        <label>Vai trò</label>
                        <select class="form-control" name="role" id="addRole" onchange="toggleAddCustomerFields()">
                            <option value="ADMIN">Admin</option>
                            <option value="CUSTOMER">Customer</option>
                        </select>
                    </div>
                    <div id="addCustomerFields" class="d-none">
                        <div class="form-group">
                            <label>Họ tên</label>
                            <input type="text" class="form-control" name="fullname">
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" name="email">
                        </div>
                        <div class="form-group">
                            <label>Điện thoại</label>
                            <input type="text" class="form-control" name="phone">
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ</label>
                            <input type="text" class="form-control" name="address">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success">Lưu</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Modal sửa -->
<div class="modal fade" id="editUserModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <form action="<%= request.getContextPath() %>/admin/edit-user" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Cập nhật tài khoản</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="id" id="editId">
                    <div class="form-group">
                        <label>Tên đăng nhập</label>
                        <input type="text" class="form-control" name="user" id="editUser" required>
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu</label>
                        <input type="password" class="form-control" name="pass" id="editPass" value="">
                    </div>
                    <div id="editCustomerFields" class="d-none">
                        <div class="form-group">
                            <label>Họ tên</label>
                            <input type="text" class="form-control" name="fullname" id="editFullname">
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" name="email" id="editEmail">
                        </div>
                        <div class="form-group">
                            <label>Điện thoại</label>
                            <input type="text" class="form-control" name="phone" id="editPhone">
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ</label>
                            <input type="text" class="form-control" name="address" id="editAddress">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success">Cập nhật</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function showTable(type) {
        document.getElementById("userTable").classList.add("d-none");
        document.getElementById("customerTable").classList.add("d-none");
        document.getElementById(type + "Table").classList.remove("d-none");
    }

    function toggleAddCustomerFields() {
        document.getElementById("addCustomerFields").classList.toggle("d-none", document.getElementById("addRole").value !== "CUSTOMER");
    }

    function populateEditForm(id, user, role, isCustomer, editFullname, editEmail, editPhone, editAddress) {
        document.getElementById("editId").value = id;
        document.getElementById("editUser").value = user;
        document.getElementById("editCustomerFields").classList.toggle("d-none", !isCustomer);
        if (isCustomer){
            document.getElementById("editFullname").value = editFullname
            document.getElementById("editEmail").value = editEmail
            document.getElementById("editPhone").value = editPhone
            document.getElementById("editAddress").value = editAddress
        }
    }
</script>

<%@include file="../layout/Footer.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
<%@include file="../layout/Foot.jsp"%>
</body>
</html>
