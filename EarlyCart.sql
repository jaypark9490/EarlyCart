CREATE DATABASE earlycart;

USE earlycart;

CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY,
    pw VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    birth VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    session VARCHAR(255)
);

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL
);

INSERT INTO categories (name, image) VALUES
('채소_과일', 'https://i.imgur.com/jFH07an.png'),
('곡물_견과', 'https://i.imgur.com/w1ZUsEL.png'),
('정육_축산', 'https://i.imgur.com/cNmCj7g.png'),
('해산_수산', 'https://i.imgur.com/YTmfYCW.png'),
('스낵_가공', 'https://i.imgur.com/c1hEYGO.png'),
('주류_음료', 'https://i.imgur.com/rowP4bI.png'),
('생활_가전', 'https://i.imgur.com/Y3Sj6uv.png'),
('사무_문구', 'https://i.imgur.com/GYGMU2k.png');

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    barcode VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    cart_id INT NOT NULL,
    price INT NOT NULL,
    status INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    item_name VARCHAR(255),
    quantity INT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);