<%@ page import="model.Comment" %>
<%@ page import="model.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    Product detail = (Product) request.getAttribute("detail");
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    boolean canComment = request.getAttribute("canComment") != null && (Boolean) request.getAttribute("canComment");
    String commentMessage = (String) request.getAttribute("commentMessage");
    double averageRating = (double) request.getAttribute("averageRating");
    int totalReviews = (int) request.getAttribute("totalReviews");
%>
<!DOCTYPE html>
<html>

<head>
    <title>${detail.name} - Product Details</title>
    <%@include file="../layout/Head.jsp"%>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Arial, sans-serif;
        }

        .container {
            max-width: 1200px;
        }

        .card {
            border: none;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        .gallery-wrap .img-big-wrap img {
            width: 100%;
            height: auto;
            max-height: 450px;
            object-fit: cover;
            border-radius: 10px 0 0 10px;
        }

        .card-body {
            padding: 30px;
        }

        .title {
            font-size: 28px;
            font-weight: bold;
            color: #333;
        }

        .price-detail-wrap .price {
            font-size: 24px;
            color: #e67e22;
        }

        .star-rating {
            font-size: 20px;
            display: inline-block;
        }

        .star-rating .fas,
        .star-rating .far {
            color: gold;
        }

        .star-rating span {
            margin-left: 10px;
            font-size: 16px;
            color: #555;
        }

        .form-group label {
            font-weight: 600;
            color: #333;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
            transition: background-color 0.3s;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-outline-primary {
            border-color: #007bff;
            color: #007bff;
        }

        .btn-outline-primary:hover {
            background-color: #007bff;
            color: white;
        }

        .media-body h5 {
            font-size: 1.1rem;
            color: #333;
            font-weight: 600;
        }

        .media-body p {
            font-size: 0.95rem;
            color: #555;
        }

        .star-rating .fa-star {
            color: #ddd;
            margin-right: 5px;
            cursor: pointer;
            font-size: 24px;
            transition: color 0.2s;
        }

        .star-rating .fa-star.checked {
            color: gold;
        }

        .star-rating .fa-star:hover,
        .star-rating .fa-star.hover {
            color: #FFD700;
        }
    </style>
</head>

<body>
<%@include file="../layout/Menu.jsp"%>
<div class="container">
    <div class="row">
        <%@include file="../layout/Left.jsp"%>
        <div class="col-sm-9">
            <div class="card mb-4">
                <div class="row no-gutters">
                    <aside class="col-sm-5">
                        <article class="gallery-wrap">
                            <div class="img-big-wrap">
                                <a href="#"><img src="<%= detail.getImage() %>" alt="<%= detail.getName() %>"></a>
                            </div>
                        </article>
                    </aside>
                    <aside class="col-sm-7">
                        <article class="card-body">
                            <h3 class="title mb-3"><%= detail.getName() %></h3>
                            <div class="product-rating mb-3">
                                <div class="star-rating">
                                    <%
                                        for (int i = 1; i <= 5; i++) {
                                            if (averageRating >= i) {
                                    %>
                                    <i class="fas fa-star"></i>
                                    <%
                                    } else if (averageRating >= i - 0.5) {
                                    %>
                                    <i class="fas fa-star-half-alt"></i>
                                    <%
                                    } else {
                                    %>
                                    <i class="far fa-star"></i>
                                    <%
                                            }
                                        }
                                    %>
                                    <span><%= averageRating %> (<%= totalReviews %> đánh giá)</span>
                                </div>
                            </div>
                            <p class="price-detail-wrap">
                        <span class="price h3 text-warning">
                            <span class="currency">US $</span><span class="num"><%= detail.getPrice() %></span>
                        </span>
                            </p>
                            <dl class="item-property">
                                <dt>Description</dt>
                                <dd><p><%= detail.getDescription() %></p></dd>
                            </dl>
                            <hr>
                            <div class="row align-items-center">
                                <div class="col-sm-5">
                                    <dl class="param param-inline">
                                        <dt>Quantity:</dt>
                                        <dd>
                                            <select onchange="document.getElementById('quantity').value = this.value" class="form-control form-control-sm" name="quantity" style="width:70px;">
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                            </select>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                            <hr>
                            <div class="d-flex">
                                <a href="#" class="btn btn-lg btn-primary text-uppercase mr-2">Buy now</a>
                                <form action="<%=request.getContextPath()%>/cart" method="post">
                                    <input type="hidden" name="ProductID" value="<%= detail.getId() %>">
                                    <input type="hidden" name="quantity" id="quantity" value="1">
                                    <button type="submit" class="btn btn-lg btn-outline-primary text-uppercase">Add to cart</button>
                                </form>
                            </div>
                        </article>
                    </aside>
                </div>
            </div>

            <!-- Comment Section -->
            <div class="card">
                <div class="card-header bg-light text-dark">
                    <h4 class="mb-0">Customer Reviews</h4>
                </div>
                <div class="card-body">
                    <%
                        if (comments != null && !comments.isEmpty()) {
                            for (Comment comment : comments) {
                    %>
                    <div class="media mb-4 p-3 border rounded bg-light">
                        <img src="https://cdn2.iconfinder.com/data/icons/universal-simple-1/288/Simple-64-128.png"
                             class="mr-3 rounded-circle" alt="User Avatar"
                             style="width: 50px; height: 50px;">
                        <div class="media-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="mt-0">User #<%= comment.getCustomer().getId() %></h5>
                                <div class="text-warning">
                                    <%
                                        for (int i = 1; i <= comment.getRating(); i++) {
                                    %>
                                    <i class="fas fa-star"></i>
                                    <%
                                        }
                                        for (int i = comment.getRating() + 1; i <= 5; i++) {
                                    %>
                                    <i class="far fa-star"></i>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                            <p class="text-muted small mb-2">
                                <%= new java.text.SimpleDateFormat("MMMM d, yyyy").format(comment.getCreatedAt()) %>
                            </p>
                            <p class="mb-0"><%= comment.getText() %></p>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p class="text-center text-muted">No reviews yet. Be the first to leave a review!</p>
                    <%
                        }
                    %>

                    <% if (user != null) {
                        if (canComment) {
                    %>
                    <div class="card my-4 border-0 shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Leave a Review</h5>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/comment" method="post">
                                <input type="hidden" name="productId" value="<%= detail.getId() %>">
                                <div class="form-group">
                                    <label>Rating:</label>
                                    <div class="star-rating">
                                        <i class="fa fa-star" data-rating="1"></i>
                                        <i class="fa fa-star" data-rating="2"></i>
                                        <i class="fa fa-star" data-rating="3"></i>
                                        <i class="fa fa-star" data-rating="4"></i>
                                        <i class="fa fa-star" data-rating="5"></i>
                                    </div>
                                    <input type="hidden" name="rating" id="rating" value="5">
                                    <small id="rating-display" class="form-text text-muted">5 stars</small>
                                </div>
                                <div class="form-group">
                                    <label for="commentText">Your Review:</label>
                                    <textarea class="form-control" id="commentText" name="commentText"
                                              rows="3" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block">Submit Review</button>
                            </form>
                        </div>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle mr-2"></i>
                        <%= commentMessage != null ? commentMessage : "You can only review product you've purchased and received." %>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <div class="alert alert-info text-center">
                        Please <a href="<%=request.getContextPath()%>/login" class="alert-link">login</a> to leave a review.
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../layout/Footer.jsp"%>
<%@include file="../layout/Foot.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
<script>
    $(document).ready(function () {

        $('.star-rating .fa-star').addClass('checked');
        $('#rating-display').text('5 stars');


        $('.star-rating .fa-star').hover(function () {
            var currentRating = $(this).data('rating');


            $('.star-rating .fa-star').removeClass('hover');


            $('.star-rating .fa-star').each(function () {
                if ($(this).data('rating') <= currentRating) {
                    $(this).addClass('hover');
                }
            });
        }, function () {

            $('.star-rating .fa-star').removeClass('hover');
        });

        $('.star-rating .fa-star').click(function () {
            var selectedRating = $(this).data('rating');


            $('.star-rating .fa-star').removeClass('checked');


            $('.star-rating .fa-star').each(function () {
                if ($(this).data('rating') <= selectedRating) {
                    $(this).addClass('checked');
                }
            });


            $('#rating').val(selectedRating);


            $('#rating-display').text(selectedRating + ' star' + (selectedRating > 1 ? 's' : ''));
        });
    });
</script>
</body>

</html>