package Controllers;

import dal.CategoryDao;
import dal.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

import java.io.IOException;
import java.util.List;

public class CategoryController {
    @WebServlet("/category")
    public static class CategoryServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            long CategoryID = Long.parseLong(request.getParameter("CategoryID"));
            ProductDao productDao = new ProductDao();
            CategoryDao categoryDao = new CategoryDao();
            Category category = categoryDao.getById(CategoryID);
            List<Product> list = productDao.getByCategoryId(category.getId());
            List<Category> listC = categoryDao.getAll();
            Product last = productDao.getLast();
            request.setAttribute("listP", list);
            request.setAttribute("listC", listC);
            request.setAttribute("p", last);
            request.setAttribute("tag", CategoryID);
            request.getRequestDispatcher("/views/public/Home.jsp").forward(request, response);
        }
    }

    @WebServlet("/admin/categories")
    public static class CategoryAdminServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<Category> categories = new CategoryDao().getAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/Category.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String CategoryName = req.getParameter("CategoryName");
            new CategoryDao().save(new Category(CategoryName));
            req.getSession().setAttribute("flash_success", "Thêm loại sản phẩm thành công.");
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @WebServlet("/admin/edit-category")
    public static class EditCategoryAdminServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            CategoryDao categoryDao = new CategoryDao();
            String action = req.getParameter("action");
            long CategoryID = Long.parseLong(req.getParameter("CategoryID"));
            switch (action) {
                case "edit":
                    Category category = categoryDao.getById(CategoryID);
                    String CategoryName = req.getParameter("CategoryName");
                    category.setName(CategoryName);
                    categoryDao.save(category);
                    req.getSession().setAttribute("flash_success", "Cập nhật loại sản phẩm thành công.");
                    break;
                case "delete":
                    categoryDao.delete(categoryDao.getById(CategoryID));
                    req.getSession().setAttribute("flash_success", "Xóa loại sản phẩm thành công.");
                    break;
                default:
                    req.getSession().setAttribute("flash_error", "Hành động không phù hợp.");
                    break;
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }
}
