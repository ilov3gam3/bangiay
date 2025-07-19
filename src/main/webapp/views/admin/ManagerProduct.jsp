<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../layout/Head.jsp" %>
    <style>
        img {
            width: 200px;
            height: 120px;
        }
    </style>
</head>
<body>
<%@include file="../layout/Menu.jsp" %>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Manage <b>Product</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i>
                        <span>Add New Product</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Image</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listP}" var="o">
                <tr>
                    <td>${o.id}</td>
                    <td>${o.name}</td>
                    <td>
                        <img src="${o.image}">
                    </td>
                    <td>${o.price} $</td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin/edit-product?ProductID=${o.id}" class="edit">Cập nhật</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Edit Modal HTML -->
<div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="<%=request.getContextPath()%>/admin/add-product" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Add Product</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Name</label>
                        <input name="ProductName" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Image</label>
                        <input name="ImageURL" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Price</label>
                        <input name="Price" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Brand</label>
                        <textarea name="Brand" class="form-control" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="Description" class="form-control" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Số lượng</label>
                        <input type="number" name="stock" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Category</label>
                        <select name="CategoryID" class="form-control" aria-label="Default select example">
                            <c:forEach items="${listC}" var="o">
                                <option value="${o.id}">${o.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-success" value="Add">
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="../layout/Footer.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
<%@include file="../layout/Foot.jsp"%>
</body>
</html>