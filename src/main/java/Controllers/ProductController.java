package Controllers;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductController {
    @WebServlet("/admin/add-product")
    public static class AddProductServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Product> list = new ProductDao().getAll();
            List<Category> listC = new CategoryDao().getAll();
            request.setAttribute("listC", listC);
            request.setAttribute("listP", list);
            request.getRequestDispatcher("/views/admin/ManagerProduct.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            CategoryDao categoryDao = new CategoryDao();
            ProductDao productDao = new ProductDao();
            String ProductName = request.getParameter("ProductName");
            String ImageURL = request.getParameter("ImageURL");
            double Price = Double.parseDouble(request.getParameter("Price"));
            String Brand = request.getParameter("Brand");
            String Description = request.getParameter("Description");
            int stock = Integer.parseInt(request.getParameter("stock"));
            if (stock < 0) {
                request.getSession().setAttribute("flash_error", "Số lượng không được nhỏ hơn 0");
                response.sendRedirect(request.getContextPath() + "/admin/add-product");
            }
            long CategoryID = Long.parseLong(request.getParameter("CategoryID"));
            Category category = categoryDao.getById(CategoryID);
            Product product = new Product(ProductName, Brand, category, Price, Description, ImageURL, stock);
            productDao.save(product);
            request.getSession().setAttribute("flash_success", "Thêm sản phẩm thành công.");
            response.sendRedirect(request.getContextPath() + "/admin/add-product");
        }
    }

    @WebServlet("/admin/edit-product")
    public static class EditProductServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            long ProductID = Long.parseLong(request.getParameter("ProductID"));
            Product p = new ProductDao().getById(ProductID);
            List<Category> listC = new CategoryDao().getAll();
            request.setAttribute("detail", p);
            request.setAttribute("listC", listC);
            request.getRequestDispatcher("/views/admin/Edit.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProductDao productDao = new ProductDao();
            CategoryDao categoryDao = new CategoryDao();
            long ProductID = Long.parseLong(request.getParameter("ProductID"));
            String ProductName = request.getParameter("ProductName");
            String ImageURL = request.getParameter("ImageURL");
            double Price = Double.parseDouble(request.getParameter("Price"));
            String Brand = request.getParameter("Brand");
            String Description = request.getParameter("Description");
            long CategoryID = Long.parseLong(request.getParameter("CategoryID"));
            Category category = categoryDao.getById(CategoryID);
            Product product = productDao.getById(ProductID);
            product.setName(ProductName);
            product.setImage(ImageURL);
            product.setPrice(Price);
            product.setBrand(Brand);
            product.setDescription(Description);
            product.setCategory(category);
            productDao.update(product);
            request.getSession().setAttribute("flash_success", "Cập nhật sản phầm thành công.");
            response.sendRedirect(request.getContextPath() + "/admin/add-product");
        }
    }

    @WebServlet("/admin/delete-product")
    public static class DeleteProductServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            long ProductID = Long.parseLong(request.getParameter("ProductID"));
            ProductDao productDao = new ProductDao();
            Product product = productDao.getById(ProductID);
            if (product != null) {
                productDao.delete(product);
                request.getSession().setAttribute("flash_success", "Xóa sản phầm thành công.");
            } else {
                request.getSession().setAttribute("flash_error", "Sản phẩm không tồn tại.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/add-product");
        }
    }

    @WebServlet("/detail")
    public static class DetailServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            long ProductID = Long.parseLong(request.getParameter("ProductID"));
            ProductDao productDao = new ProductDao();
            OrderDao orderDao = new OrderDao();
            CommentDao commentDao = new CommentDao();
            CustomerDao customerDao = new CustomerDao();
            HttpSession session = request.getSession();
            if (session.getAttribute("acc") != null) {
                User user = (User) session.getAttribute("acc");
                if (user != null) {
                    Customer customer = customerDao.getById(user.getId());
                    long customerId = customer.getId();

                    // Get counts of delivered orders and existing comments
                    int deliveredOrders = orderDao.getDeliveredOrdersCount(customerId, ProductID);
                    int existingComments = commentDao.getUserCommentsCount(customerId, ProductID);

                    // User can comment if they have more delivered orders than comments
                    boolean canComment = deliveredOrders > existingComments;
                    request.setAttribute("canComment", canComment);

                    if (!canComment && existingComments > 0) {
                        if (deliveredOrders == existingComments) {
                            request.setAttribute("commentMessage", "You have already reviewed all your purchases of this product.");
                        } else {
                            request.setAttribute("commentMessage", "You cannot leave more reviews at this time.");
                        }
                    }
                } else {
                    request.setAttribute("canComment", false);
                }
            }
            Product p = productDao.getById(ProductID);
            List<Category> listC = new CategoryDao().getAll();
            Product last = productDao.getLast();

            List<Comment> comments = p.getComments();
            request.setAttribute("comments", comments);

            double averageRating = commentDao.getAverageRatingByProductId(ProductID);
            request.setAttribute("averageRating", averageRating);

            int totalReviews = comments.size();
            request.setAttribute("totalReviews", totalReviews);

            request.setAttribute("detail", p);
            request.setAttribute("listC", listC);
            request.setAttribute("p", last);
            request.getRequestDispatcher("/views/public/Detail.jsp").forward(request, response);
        }
    }

    @WebServlet("/search")
    public static class SearchServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            ProductDao productDao = new ProductDao();
            String txtSearch = request.getParameter("txt");
            List<Product> list = productDao.searchByName(txtSearch);
            List<Category> listC = new CategoryDao().getAll();
            Product last = productDao.getLast();
            request.setAttribute("listP", list);
            request.setAttribute("listC", listC);
            request.setAttribute("p", last);
            request.getRequestDispatcher("/views/public/Home.jsp").forward(request, response);
        }
    }

    @WebServlet("/home")
    public static class HomeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProductDao productDao = new ProductDao();
            List<Product> list = productDao.getAll();
            List<Category> listC = new CategoryDao().getAll();
            Product last = productDao.getLast();

            request.setAttribute("listP", list);
            request.setAttribute("listC", listC);
            request.setAttribute("p", last);
            request.getRequestDispatcher("/views/public/Home.jsp").forward(request, response);
        }
    }

    @WebServlet("/ask-ai")
    public static class AskAi extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String text = req.getParameter("text");
            List<Product> products = new ProductDao().getAll();
            String csv = toCSV(products);
            String prompt = String.format("""
                    Đây là danh sách sản phẩm theo định dạng CSV, bao gồm các cột:
                    id,name,brand,price,description,category
                    
                    %s
                    
                    Tôi muốn bạn tư vấn 3 sản phẩm phù hợp với yêu cầu sau:
                    %s
                    
                    Yêu cầu đầu ra:
                    - Trả lời bằng tiếng Việt.
                    - Trả về đúng định dạng JSON.
                    - Mỗi sản phẩm là một object trong mảng, gồm các trường:
                      - id (số)
                      - name (tên sản phẩm)
                      - price (giá)
                      - description (mô tả ngắn)
                      - url (đường dẫn dạng: %s/detail?ProductID=<id>)
                    - Nếu không có sản phẩm nào phù hợp thì trả về: { "products": [] }
                    
                    Định dạng kết quả như sau:
                    {
                      "products": [
                        {
                          "id": 1,
                          "name": "Tên sản phẩm",
                          "price": 123.0,
                          "description": "Mô tả...",
                          "url": "/contextPath/detail?ProductID=1"
                        }
                      ]
                    }
                    """, csv, text, req.getContextPath());


            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            try {
                String result = askAI(prompt);
                resp.getWriter().write(result);
            } catch (Exception e) {
                e.printStackTrace();
                resp.getWriter().write("Lỗi khi sử dụng AI");
            }
        }

        public String askAI(String text) throws Exception {
            String apiKey = "AIzaSyAbobxuRAYZy7bfBTqgsLqUMRDsL12FDbc";
//            String apiKey = "AIzaSyAHM3svzVsDVpvocf9r7glnk8sgaP6eXLY";
            String askAI = text;
            JSONObject payload = new JSONObject();
            JSONArray contents = new JSONArray();
            JSONArray parts = new JSONArray();
            JSONObject textPart = new JSONObject();
            textPart.put("text", askAI);
            parts.put(textPart);
            JSONObject content = new JSONObject();
            content.put("parts", parts);
            contents.put(content);
            payload.put("contents", contents);

//            String urlString = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";
            String urlString = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b-latest:generateContent";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("X-goog-api-key", apiKey);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Request failed: HTTP code " + responseCode);
            }

            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }

            JSONObject responseBody = new JSONObject(responseStr.toString());
            JSONArray candidates = responseBody.getJSONArray("candidates");
            if (!candidates.isEmpty()) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONObject contentObj = firstCandidate.getJSONObject("content");
                JSONArray partsArray = contentObj.getJSONArray("parts");
                return partsArray.getJSONObject(0).getString("text").replace("json", "").replace("```", "");
            }

            return null;
        }

        public String toCSV(List<Product> products) {
            StringBuilder csvBuilder = new StringBuilder();
            csvBuilder.append("id,name,brand,price,description,category\n");
            for (Product p : products) {
                csvBuilder.append(escapeCSV(String.valueOf(p.getId()))).append(",");
                csvBuilder.append(escapeCSV(p.getName())).append(",");
                csvBuilder.append(escapeCSV(p.getBrand())).append(",");
                csvBuilder.append(p.getPrice()).append(",");
                csvBuilder.append(escapeCSV(p.getDescription())).append(",");
                csvBuilder.append(escapeCSV(p.getCategory().getName())).append("\n");
            }
            return csvBuilder.toString();
        }

        private String escapeCSV(String value) {
            if (value == null) return "";
            if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                value = value.replace("\"", "\"\""); // escape dấu "
                return "\"" + value + "\"";
            }
            return value;
        }
    }
}
