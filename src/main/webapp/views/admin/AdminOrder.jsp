<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.*" %>
<%@ page import="model.Constant.Status" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../layout/Head.jsp"%>
</head>

<body>
<%@include file="../layout/Menu.jsp"%>
<div class="shopping-cart">
    <div class="px-4 px-lg-0">
        <div class="pb-5">
            <div class="container">
                <%
                    List<Order> orders = (List<Order>) request.getAttribute("orders");
                    if (orders != null && !orders.isEmpty()) {
                        for (Order order : orders) {
                %>
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white">
                        <strong>Đơn hàng #<%= order.getId() %>
                        </strong> - Ngày đặt:
                        <%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(order.getCreatedAt()) %> - Trạng
                        thái: <%= order.getStatus() %>
                        <form action="${pageContext.request.contextPath}/admin/order" method="post">
                            <input type="hidden" name="orderId" value="<%= order.getId() %>">
                            <% if (order.getStatus() == Status.PENDING) {%>
                                <input type="submit" name="status" value="SHIPPED" class="btn btn-success">
                                <input type="submit" name="status" value="CANCELED" class="btn btn-danger">
                            <% } else if (order.getStatus() == Status.SHIPPED){ %>
                                <input type="submit" name="status" value="DELIVERED" class="btn btn-success">
                                <input type="submit" name="status" value="CANCELED" class="btn btn-danger">
                            <% } %>
                        </form>
                    </div>
                    <div class="card-body">
                        <ul class="list-group">
                            <%
                                List<OrderDetail> orderDetails = order.getOrderDetails();
                                if (orderDetails != null && !orderDetails.isEmpty()) {
                                    for (OrderDetail detail : orderDetails) {
                                        Product product = detail.getProduct();
                            %>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <strong><%= product.getName() %>
                                    </strong> - <%= product.getBrand() %>
                                    <br> Số lượng: <%= detail.getQuantity() %> | Đơn giá: <%= detail.getPrice() %>
                                    $
                                </div>
                                <span class="badge bg-success"><%= detail.getQuantity() * detail.getPrice() %> $</span>
                            </li>
                            <%
                                }
                            } else {
                            %>
                            <li class="list-group-item text-danger">Không có sản phẩm nào trong đơn hàng này.</li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="card-footer text-end">
                        <strong>Tổng tiền: <%= order.getTotalAmount() %> $</strong>
                    </div>
                </div>
                <% }
                } else {
                %>
                <div class="alert alert-warning">Bạn chưa có đơn hàng nào.</div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
<%@include file="../layout/Foot.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
</body>
</html>
</html>
