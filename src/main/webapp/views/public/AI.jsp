<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>AI Chat Bot</title>
    <%@include file="../layout/Head.jsp"%>
    <style>
        .chat-box {
            background: #f7f7f7;
            border-radius: 10px;
            padding: 20px;
            min-height: 400px;
            max-height: 600px;
            overflow-y: auto;
            margin-bottom: 20px;
        }
        .chat-message {
            margin-bottom: 15px;
        }
        .chat-user {
            font-weight: bold;
            color: #007bff;
        }
        .chat-bot {
            font-weight: bold;
            color: #28a745;
        }
        .product-suggestion {
            background: #ffffff;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>
<!--begin of menu-->
<%@include file="../layout/Menu.jsp"%>
<!--end of menu-->
<div class="container">
    <div class="row">
        <div class="col-sm-9">
            <h3>AI Tư vấn sản phẩm</h3>
            <div class="chat-box" id="chatBox">
                <!-- Chat messages sẽ được thêm tại đây -->
            </div>
            <form id="aiForm" class="form-inline">
                <input type="text" class="form-control mr-2" id="userInput" placeholder="Bạn muốn tìm gì?" style="width: 70%" required>
                <button type="submit" class="btn btn-primary">Hỏi AI</button>
            </form>
        </div>
    </div>
</div>

<!-- Footer -->
<%@include file="../layout/Footer.jsp"%>
<%@include file="../layout/Toastr.jsp"%>
<%@include file="../layout/Foot.jsp"%>
<script>
    document.getElementById("aiForm").addEventListener("submit", function (e) {
        e.preventDefault();
        const input = document.getElementById("userInput");
        const text = input.value.trim();
        if (!text) return;

        const chatBox = document.getElementById("chatBox");

        // Hiển thị tin nhắn người dùng
        chatBox.innerHTML += "<div class='chat-message'><span class='chat-user'>Bạn:</span> " + text + "</div>";
        input.value = "";

        fetch("${pageContext.request.contextPath}/ask-ai?text=" + encodeURIComponent(text))
            .then(response => response.json())
            .then(data => {
                if (data.products && data.products.length > 0) {
                    let responseHtml = `<div class='chat-message'><span class='chat-bot'>AI:</span><div class='ml-3'>`;
                    data.products.forEach(function(p) {
                        responseHtml += "<div class='product-suggestion'>" +
                            "<strong><a href='" + p.url + "' target='_blank'>" + p.name + "</a></strong><br>" +
                            "Giá: " + p.price + " USD<br>" +
                            "Mô tả: " + p.description +
                            "</div>";
                    });
                    responseHtml += `</div></div>`;
                    chatBox.innerHTML += responseHtml;
                } else {
                    chatBox.innerHTML += `<div class='chat-message'><span class='chat-bot'>AI:</span> Không có sản phẩm nào phù hợp trong hệ thống.</div>`;
                }
                chatBox.scrollTop = chatBox.scrollHeight;
            })
            .catch(err => {
                chatBox.innerHTML += `<div class='chat-message text-danger'><span class='chat-bot'>AI:</span> Đã xảy ra lỗi khi gọi API.</div>`;
                chatBox.scrollTop = chatBox.scrollHeight;
                console.error(err);
            });
    });
</script>
</body>
</html>
