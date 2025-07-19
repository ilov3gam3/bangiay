<%@ page import="java.text.DecimalFormat" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart Page</title>
        <%@include file="../layout/Head.jsp"%>
    </head>

    <body>
        <%@include file="../layout/Menu.jsp"%>
            <div class="shopping-cart">
                <div class="px-4 px-lg-0">

                    <div class="pb-5">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">

                                    <!-- Shopping cart table -->
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th scope="col" class="border-0 bg-light">
                                                        <div class="p-2 px-3 text-uppercase">Sản Phẩm</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-light">
                                                        <div class="py-2 text-uppercase">Đơn Giá</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-light">
                                                        <div class="py-2 text-uppercase">Số Lượng</div>
                                                    </th>
                                                    <th scope="col" class="border-0 bg-light">
                                                        <div class="py-2 text-uppercase">Xóa</div>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <% double totalPrice = 0; for (int i = 0; i < carts.size(); i++) { totalPrice += carts.get(i).getQuantity() * carts.get(i).getProduct().getPrice(); %>
                                                <tr>
                                                    <th scope="row">
                                                        <div class="p-2">
                                                            <img src="<%=carts.get(i).getProduct().getImage()%>" alt="" width="70" class="img-fluid rounded shadow-sm">
                                                            <div class="ml-3 d-inline-block align-middle">
                                                                <h5 class="mb-0"> <a href="#" class="text-dark d-inline-block"><%=carts.get(i).getProduct().getName()%></a></h5><span class="text-muted font-weight-normal font-italic"></span>
                                                            </div>
                                                        </div>
                                                    </th>
                                                    <td class="align-middle"><strong><%=carts.get(i).getProduct().getPrice()%></strong></td>
                                                    <td class="align-middle">
                                                        <a href="<%=request.getContextPath()%>/cart?action=minus&productId=<%=carts.get(i).getProduct().getId()%>"><button class="btn">-</button></a>
                                                        <strong><%=carts.get(i).getQuantity()%></strong>
                                                        <a href="<%=request.getContextPath()%>/cart?action=plus&productId=<%=carts.get(i).getProduct().getId()%>"><button class="btn">+</button></a>
                                                    </td>
                                                    <td class="align-middle"><a href="<%=request.getContextPath()%>/cart?action=delete&cartId=<%=carts.get(i).getId()%>&productId=<%=carts.get(i).getProduct().getId()%>" class="text-dark">
                                                        <button type="button" class="btn btn-danger">Delete</button>
                                                    </a>
                                                    </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- End -->
                            </div>
                        </div>

                        <div class="row py-5 p-4 bg-white rounded shadow-sm">
                            <div class="col-lg-6">
                                <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Thành tiền</div>
                                <div class="p-4">
                                    <ul class="list-unstyled mb-4">
                                        <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Tổng tiền hàng</strong><strong><%=totalPrice%> $</strong></li>
                                        <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Phí vận chuyển</strong><strong>Free ship</strong></li>
                                        <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">VAT</strong><strong><%=totalPrice * 0.1%> $</strong></li>
                                        <li class="d-flex justify-content-between py-3 border-bottom"><strong class="text-muted">Tổng thanh toán</strong>
                                            <h5 class="font-weight-bold"><%=new DecimalFormat("#.###").format(totalPrice * 1.1)%> $</h5>
                                        </li>
                                        </ul>
                                    <a href="<%=request.getContextPath()%>/customer/place-order" class="btn btn-dark rounded-pill py-2 btn-block">Mua hàng</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <%@include file="../layout/Footer.jsp"%>
        <%@include file="../layout/Foot.jsp"%>
        <%@include file="../layout/Toastr.jsp"%>
    </body>

</html>
</html>
