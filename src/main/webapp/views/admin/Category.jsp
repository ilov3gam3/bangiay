<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../layout/Head.jsp"%>
</head>
<body>
<%@include file="../layout/Menu.jsp"%>

<div class="container mt-4">
    <h2>Quản lý Loại sản phẩm</h2>

    <!-- Thêm mới -->
    <form action="${pageContext.request.contextPath}/admin/categories" method="post" class="mb-4">
        <div class="form-row align-items-end">
            <div class="col">
                <label for="CategoryName">Tên loại</label>
                <input type="text" class="form-control" name="CategoryName" id="CategoryName" required>
            </div>
            <div class="col">
                <button type="submit" class="btn btn-primary">Thêm loại</button>
            </div>
        </div>
    </form>

    <!-- Bảng danh sách -->
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Tên loại</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null) {
                for (Category c : categories) {
        %>
        <tr>
            <td><%= c.getId() %></td>
            <td><%= c.getName() %></td>
            <td>
                <!-- Nút sửa -->
                <button class="btn btn-warning btn-sm" data-toggle="modal"
                        data-target="#editModal"
                        onclick="populateEditModal('<%= c.getId() %>', '<%= c.getName() %>')">
                    Sửa
                </button>

                <!-- Nút xóa -->
                <form action="${pageContext.request.contextPath}/admin/edit-category" method="post"
                      style="display:inline;" onsubmit="return confirm('Bạn có chắc muốn xóa không?')">
                    <input type="hidden" name="CategoryID" value="<%= c.getId() %>">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<!-- Modal chỉnh sửa -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form action="${pageContext.request.contextPath}/admin/edit-category" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Chỉnh sửa loại sản phẩm</h5>
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="edit">
                    <div class="form-group">
                        <label for="editCategoryID">Mã loại</label>
                        <input type="number" class="form-control" id="editCategoryID" name="CategoryID" readonly>
                    </div>
                    <div class="form-group">
                        <label for="editCategoryName">Tên loại</label>
                        <input type="text" class="form-control" id="editCategoryName" name="CategoryName" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success">Cập nhật</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function populateEditModal(id, name) {
        document.getElementById("editCategoryID").value = id;
        document.getElementById("editCategoryName").value = name;
    }
</script>

<%@include file="../layout/Foot.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
</body>
</html>
