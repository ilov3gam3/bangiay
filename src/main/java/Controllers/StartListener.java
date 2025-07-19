package Controllers;

import dal.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.*;
import model.Constant.Role;
import model.Constant.Status;

import java.util.List;

@WebListener
public class StartListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = new UserDao();
        CustomerDao customerDao = new CustomerDao();
        CategoryDao categoryDao = new CategoryDao();
        ProductDao productDao = new ProductDao();
        OrderDao orderDao = new OrderDao();
        CartDao cartDao = new CartDao();
        CommentDao commentDao = new CommentDao();
        if (userDao.getAll().isEmpty() && productDao.getAll().isEmpty() && categoryDao.getAll().isEmpty()) {
            User admin1 = new User("admin", "1", Role.ADMIN);
            User admin2 = new User("admin2", "1", Role.ADMIN);
            User user1 = new User("user1", "1", Role.CUSTOMER);
            User user2 = new User("user2", "1", Role.CUSTOMER);
            User user3 = new User("user3", "1", Role.CUSTOMER);
            User user4 = new User("user4", "1", Role.CUSTOMER);
            User user5 = new User("user5", "1", Role.CUSTOMER);
            Customer customer1 = new Customer(user1, "Trần Văn A", "tranvana@gmail.com", "0123456789", "da nang viet nam");
            Customer customer2 = new Customer(user2, "Trần Văn B", "tranvanb@gmail.com", "0123456788", "da nang viet nam");
            Customer customer3 = new Customer(user3, "Trần Văn C", "tranvanc@gmail.com", "0123456787", "da nang viet nam");
            Customer customer4 = new Customer(user4, "Trần Văn D", "tranvand@gmail.com", "0123456786", "da nang viet nam");
            Customer customer5 = new Customer(user5, "Trần Văn E", "tranvane@gmail.com", "0123456785", "da nang viet nam");
            userDao.saveAll(List.of(admin1, admin2, user1, user2, user1, user4, user5));
            customerDao.saveAll(List.of(customer1, customer2, customer3, customer4, customer5));
            Category category1 = new Category("Giày Lining");
            Category category2 = new Category("Giày trẻ em");
            Category category3 = new Category("Giày thể thao");
            Category category4 = new Category("Giày thời trang");
            Category category5 = new Category("Giày Yonex");
            Category category6 = new Category("Giày Addidas");
            categoryDao.saveAll(List.of(category1, category2, category3, category4, category5, category6));
            Product product1 = new Product("Nike Air Max 90", "Nike", category3, 120.00, "Giày thể thao nam cao cấp", "https://static.nike.com/a/images/t_default/yo8q4g51so6rylfl6w14/AIR+MAX+90.png", 10);
            Product product2 = new Product("Adidas Ultraboost 22", "Adidas", category3, 160.00, "Giày chạy bộ hiệu suất cao", "https://product.hstatic.net/1000391653/product/gx5459_1115db15ed644ed592e6e6ad637d2ac9_master.jpg", 20);
            Product product3 = new Product("Puma RS-X3", "Puma", category3, 115.00, "Phong cách hiện đại và năng động", "https://ktsneaker.com/upload/product/117-4201.jpg", 30);
            Product product4 = new Product("New Balance 990v5", "New Balance", category4, 180.00, "Dòng giày cổ điển, thoải mái", "https://authentic-shoes.com/wp-content/uploads/2023/04/1613109025729_newbalance2_473f9b5a36d44d3a8880d5c227ca1b90.jpeg", 40);
            Product product5 = new Product("Asics Gel-Kayano 28", "Asics", category3, 140.00, "Giày chạy bộ hỗ trợ cao", "https://www.jordan1.vn/wp-content/uploads/2023/09/1011b189_002_sb_fr_glb_d9b352565_6d2d34a05f564e31aedf5a3335fd67b9.png", 50);
            Product product6 = new Product("Reebok Nano X2", "Reebok", category3, 130.00, "Giày tập gym đa dụng", "https://www.runningshoesguru.com/wp-content/uploads/2022/05/Reebok-Nano-X2-pic-03.jpeg", 60);
            Product product7 = new Product("Under Armour HOVR Phantom", "Under Armour", category3, 150.00, "Giày thể thao công nghệ HOVR", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqR66MQR3KzJajDeFNOxv_y1_VTsHQDxjm-A&s", 70);
            Product product8 = new Product("Vans Old Skool", "Vans", category4, 80.00, "Giày sneaker thời trang cổ điển", "https://bizweb.dktcdn.net/100/140/774/files/vans-forgotten-bones-old-skool-vn0a4bv5v8v-2.jpg?v=1569426630840", 10);
            Product product9 = new Product("Converse Chuck Taylor", "Converse", category4, 75.00, "Giày vải kinh điển", "https://www.converse.vn/media/catalog/product/0/8/0882-CON162050C000005-1.jpg", 20);
            Product product10 = new Product("Balenciaga Triple S", "Balenciaga", category4, 950.00, "Giày sneaker cao cấp thời trang", "https://www.jordan1.vn/wp-content/uploads/2023/09/485995_01.jpg_296b9ddc6a2a4ffb902adb9b6d9dc268_01d2fb670eec4afe84c74815bd8460f4.png", 30);
            Product product11 = new Product("Nike Air Force 1", "Nike", category4, 110.00, "Thiết kế đơn giản nhưng huyền thoại", "https://sneakerdaily.vn/wp-content/uploads/2023/08/giay-nike-air-force-1-low-athletic-department-fn7439-133.jpg", 25);
            Product product12 = new Product("Adidas Stan Smith", "Adidas", category4, 100.00, "Biểu tượng thời trang tối giản", "https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/f6bfb2c064a64c498e57af1700593332_9366/Giay_Stan_Smith_Lux_trang_HQ6785_HM1.jpg", 15);
            Product product13 = new Product("Nike ZoomX Vaporfly NEXT%", "Nike", category3, 250.00, "Giày chạy bộ siêu nhẹ và hiệu suất cao", "https://sneakerdaily.vn/wp-content/uploads/2024/12/Giay-Nike-ZoomX-Vaporfly-Next-3-White-DV4129-103.jpg", 10);
            Product product14 = new Product("Hoka One One Bondi 8", "Hoka", category3, 160.00, "Đệm dày, êm ái cho chạy bộ đường dài", "https://bizweb.dktcdn.net/100/494/688/products/396ffdcc-fded-4e4b-917f-1c6510825a25-jpeg-1692955057160.jpg?v=1722329878393", 12);
            Product product15 = new Product("Yonex Power Cushion Eclipsion Z", "Yonex", category5, 145.00, "Giày cầu lông cao cấp dành cho thi đấu", "https://laz-img-sg.alicdn.com/p/bef3d50ffe2cc88b7a4dbf2d32dc88d0.jpg", 20);
            Product product16 = new Product("Lining Saga", "Lining", category1, 110.00, "Giày cầu lông chính hãng với công nghệ giảm chấn", "https://saigonbadminton.vn/wp-content/uploads/2024/04/kiotviet_e21ef06b8aeb3b6f4ebf482424908788.png", 18);
            List<Product> products = List.of(
                    product1,
                    product2,
                    product3,
                    product4,
                    product5,
                    product6,
                    product7,
                    product8,
                    product9,
                    product10,
                    product11,
                    product12,
                    product13,
                    product14,
                    product15,
                    product16
            );
            productDao.saveAll(products);
            List<OrderDetail> orderDetails1 = List.of(
                new OrderDetail(product1, 2, product1.getPrice()),
                new OrderDetail(product3, 1, product3.getPrice())
            );
            Order order1 = new Order(customer1, 350.00, Status.PENDING, orderDetails1);
            List<OrderDetail> orderDetails2 = List.of(
                    new OrderDetail(product2, 1, product2.getPrice())
            );
            Order order2 = new Order(customer2, 160.00, Status.PENDING, orderDetails2);
            orderDao.saveAll(List.of(order1, order2));
            Cart cart1 = new Cart(customer1, product1, 1);
            Cart cart2 = new Cart(customer2, product2, 2);
            cartDao.saveAll(List.of(cart1, cart2));
            Comment comment1 = new Comment(customer1, product1, "Giày rất thoải mái, đáng giá tiền!", 5);
            Comment comment2 = new Comment(customer1, product3, "Đẹp nhưng hơi chật ở phần mũi giày.", 3);
            Comment comment3 = new Comment(customer2, product2, "Chất lượng tuyệt vời, chạy bộ rất êm.", 4);
            Comment comment4 = new Comment(customer3, product4, "Thích thiết kế cổ điển, nhưng giá hơi cao.", 4);
            commentDao.saveAll(List.of(comment1, comment2, comment3, comment4));
        }
        System.out.println("Seed done!!!");
    }
}
