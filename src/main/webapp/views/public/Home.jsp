<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <%@include file="../layout/Head.jsp"%>
    </head>
    <body>
        <!--begin of menu-->
        <%@include file="../layout/Menu.jsp"%>
        <!--end of menu-->
        <div class="container">
            <div class="row">
                <%@include file="../layout/Left.jsp"%>
                <div class="col-sm-9">
                    <div class="row">
                        <c:forEach items="${listP}" var="o">
                            <div class="col-11 col-md-5 col-lg-3">
                                <div class="card">
                                    <img class="card-img-top" style="width: 100%;height: 200px;object-fit: contain" src="${o.image}" alt="Card image cap">
                                    <div class="card-body">
                                        <h4 class="card-title show_txt"><a href="detail?ProductID=${o.id}" title="View Product">${o.name}</a></h4>
                                        <p class="card-text show_txt">${o.brand}</p>
                                        <div class="row">
                                            <div class="col">
                                                <p class="btn btn-danger btn-block">${o.price} $</p>
                                            </div>
                                            <div class="col">
                                                <form action="<%=request.getContextPath()%>/cart" method="post">
                                                    <input type="hidden" name="ProductID" value="${o.id}">
                                                    <input type="hidden" name="quantity" value="1">
                                                    <button type="submit" class="btn btn-success btn-block">Add to cart</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>

        <!-- Footer -->
        <%@include file="../layout/Footer.jsp"%>
        <%@include file="../layout/Toastr.jsp"%>
        <%@include file="../layout/Foot.jsp"%>
    </body>
</html>
