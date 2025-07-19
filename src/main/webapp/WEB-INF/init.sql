-- DROP các bảng cũ nếu cần
-- DROP TABLE IF EXISTS OrderDetails, Orders, Cart, Comments, Products, Category, Customers, Users;

-- Bảng Users
CREATE TABLE Users
(
    UserID   INT IDENTITY (1,1) PRIMARY KEY,
    UserName NVARCHAR(50) UNIQUE NOT NULL,
    Password NVARCHAR(255)       NOT NULL,
    Role     NVARCHAR(10)        NOT NULL DEFAULT 'Customer',
    CONSTRAINT CK_Users_Role CHECK (Role IN ('Admin', 'Customer'))
);

-- Bảng Customers
CREATE TABLE Customers
(
    CustomerID       INT IDENTITY (1,1) PRIMARY KEY,
    FullName         NVARCHAR(100)        NOT NULL,
    Email            NVARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber      NVARCHAR(15) UNIQUE  NOT NULL,
    Address          NVARCHAR(255),
    RegistrationDate DATETIME DEFAULT GETDATE(),
    UserID           INT,
    CONSTRAINT FK_Customers_Users FOREIGN KEY (UserID)
        REFERENCES Users (UserID) ON DELETE CASCADE
);

-- Bảng Category
CREATE TABLE Category
(
    CategoryID   INT IDENTITY (1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL UNIQUE
);

-- Bảng Products
CREATE TABLE Products
(
    ProductID   INT IDENTITY (1,1) PRIMARY KEY,
    ProductName NVARCHAR(100)  NOT NULL,
    Brand       NVARCHAR(50)   NOT NULL,
    Price       DECIMAL(10, 2) NOT NULL CHECK (Price >= 0),
    Description NVARCHAR(500),
    ImageURL    NVARCHAR(255),
    DateAdded   DATE DEFAULT GETDATE(),
    CategoryID  INT,
    Stock       INT,
    CONSTRAINT FK_Products_Category FOREIGN KEY (CategoryID)
        REFERENCES Category (CategoryID) ON DELETE CASCADE
);

-- Bảng Cart
CREATE TABLE Cart
(
    CartID     INT IDENTITY (1,1) PRIMARY KEY,
    CustomerID INT NOT NULL,
    ProductID  INT NOT NULL,
    Quantity   INT NOT NULL CHECK (Quantity > 0),
    DateAdded  DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Cart_Customers FOREIGN KEY (CustomerID)
        REFERENCES Customers (CustomerID) ON DELETE CASCADE,
    CONSTRAINT FK_Cart_Products FOREIGN KEY (ProductID)
        REFERENCES Products (ProductID) ON DELETE CASCADE
);

-- Bảng Orders
CREATE TABLE Orders
(
    OrderID     INT IDENTITY (1,1) PRIMARY KEY,
    CustomerID  INT NOT NULL,
    OrderDate   DATETIME     DEFAULT GETDATE(),
    TotalAmount DECIMAL(10, 2) CHECK (TotalAmount >= 0),
    Status      NVARCHAR(20) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Shipped', 'Delivered', 'Cancelled')),
    CONSTRAINT FK_Orders_Customers FOREIGN KEY (CustomerID)
        REFERENCES Customers (CustomerID) ON DELETE CASCADE
);

-- Bảng OrderDetails
CREATE TABLE OrderDetails
(
    OrderDetailID INT IDENTITY (1,1) PRIMARY KEY,
    OrderID       INT            NOT NULL,
    ProductID     INT            NOT NULL,
    Quantity      INT            NOT NULL CHECK (Quantity > 0),
    Price         DECIMAL(10, 2) NOT NULL CHECK (Price >= 0),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderID)
        REFERENCES Orders (OrderID) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductID)
        REFERENCES Products (ProductID) ON DELETE CASCADE
);

-- Bảng Comments
CREATE TABLE Comments
(
    CommentID   INT IDENTITY (1,1) PRIMARY KEY,
    CustomerID  INT           NOT NULL,
    ProductID   INT           NOT NULL,
    CommentText NVARCHAR(500) NOT NULL,
    Rating      INT CHECK (Rating BETWEEN 1 AND 5),
    CommentDate DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Comments_Customers FOREIGN KEY (CustomerID)
        REFERENCES Customers (CustomerID) ON DELETE CASCADE,
    CONSTRAINT FK_Comments_Products FOREIGN KEY (ProductID)
        REFERENCES Products (ProductID) ON DELETE CASCADE
);


-- Chèn dữ liệu vào bảng Users
INSERT INTO Users (UserName, Password, Role)
VALUES ('admin', '1', 'Admin'),
       ('user1', '1', 'Customer'),
       ('user2', '1', 'Customer');

-- Chèn dữ liệu vào bảng Customers
INSERT INTO Customers (FullName, Email, PhoneNumber, Address, UserID)
VALUES (N'Nguyễn Văn A', 'nguyenvana@example.com', '0987654321', N'Hà Nội', 2),
       (N'Trần Thị B', 'tranthib@example.com', '0978123456', N'TP.HCM', 3);

-- Chèn dữ liệu vào bảng Category
INSERT INTO Category (CategoryName)
VALUES (N'Giày Lining chính hãng'),
       (N'Giày trẻ em'),
       (N'Giày thể thao'),
       (N'Giày thời trang');

-- Chèn dữ liệu vào bảng Products
INSERT INTO Products (ProductName, Brand, Price, [Description], ImageURL, CategoryID, Stock)
VALUES ('Nike Air Max 90', 'Nike', 120.00, N'Giày thể thao nam cao cấp',
        'https://static.nike.com/a/images/t_default/yo8q4g51so6rylfl6w14/AIR+MAX+90.png', 3, 10),
       ('Adidas Ultraboost 22', 'Adidas', 160.00, N'Giày chạy bộ hiệu suất cao',
        'https://product.hstatic.net/1000391653/product/gx5459_1115db15ed644ed592e6e6ad637d2ac9_master.jpg', 3, 20),
       ('Puma RS-X3', 'Puma', 115.00, N'Phong cách hiện đại và năng động',
        'https://ktsneaker.com/upload/product/117-4201.jpg', 3, 30),
       ('New Balance 990v5', 'New Balance', 180.00, N'Dòng giày cổ điển, thoải mái',
        'https://authentic-shoes.com/wp-content/uploads/2023/04/1613109025729_newbalance2_473f9b5a36d44d3a8880d5c227ca1b90.jpeg',
        4, 40),
       ('Asics Gel-Kayano 28', 'Asics', 140.00, N'Giày chạy bộ hỗ trợ cao',
        'https://www.jordan1.vn/wp-content/uploads/2023/09/1011b189_002_sb_fr_glb_d9b352565_6d2d34a05f564e31aedf5a3335fd67b9.png',
        3, 50),
       ('Reebok Nano X2', 'Reebok', 130.00, N'Giày tập gym đa dụng',
        'https://www.runningshoesguru.com/wp-content/uploads/2022/05/Reebok-Nano-X2-pic-03.jpeg', 3, 60),
       ('Under Armour HOVR Phantom', 'Under Armour', 150.00, N'Giày thể thao công nghệ HOVR',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqR66MQR3KzJajDeFNOxv_y1_VTsHQDxjm-A&s', 4, 70),
       ('Vans Old Skool', 'Vans', 80.00, N'Giày sneaker thời trang cổ điển',
        'https://bizweb.dktcdn.net/100/140/774/files/vans-forgotten-bones-old-skool-vn0a4bv5v8v-2.jpg?v=1569426630840',
        4, 10),
       ('Converse Chuck Taylor', 'Converse', 75.00, N'Giày vải kinh điển',
        'https://www.converse.vn/media/catalog/product/0/8/0882-CON162050C000005-1.jpg', 4, 20),
       ('Balenciaga Triple S', 'Balenciaga', 950.00, N'Giày sneaker cao cấp thời trang',
        'https://www.jordan1.vn/wp-content/uploads/2023/09/485995_01.jpg_296b9ddc6a2a4ffb902adb9b6d9dc268_01d2fb670eec4afe84c74815bd8460f4.png',
        4, 30);

-- Chèn dữ liệu vào bảng Orders
INSERT INTO Orders (CustomerID, OrderDate, TotalAmount, Status)
VALUES (1, GETDATE(), 350.00, 'Pending'), -- Tổng tiền: 2 Nike (240) + 1 Puma (110)
       (2, GETDATE(), 160.00, 'Shipped');
-- Tổng tiền: 1 Adidas (160)

-- Chèn dữ liệu vào bảng OrderDetails
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price)
VALUES (1, 1, 2, 120.00), -- Đơn hàng 1: 2 đôi Nike Air Max
       (1, 3, 1, 110.00), -- Đơn hàng 1: 1 đôi Puma RS-X
       (2, 2, 1, 160.00);
-- Đơn hàng 2: 1 đôi Adidas Ultraboost

-- Chèn dữ liệu vào bảng Cart
INSERT INTO Cart (CustomerID, ProductID, Quantity)
VALUES (1, 1, 1), -- Nguyễn Văn A thêm 1 Nike Air Max vào giỏ
       (2, 2, 2);
-- Trần Thị B thêm 2 Adidas Ultraboost vào giỏ

-- Chèn dữ liệu mẫu vào bảng Comments
INSERT INTO Comments (CustomerID, ProductID, CommentText, Rating, CommentDate)
VALUES (1, 1, N'Giày rất thoải mái, đáng giá tiền!', 5, GETDATE()),
       (1, 3, N'Đẹp nhưng hơi chật ở phần mũi giày.', 3, GETDATE()),
       (2, 2, N'Chất lượng tuyệt vời, chạy bộ rất êm.', 4, GETDATE()),
       (2, 4, N'Thích thiết kế cổ điển, nhưng giá hơi cao.', 4, GETDATE());