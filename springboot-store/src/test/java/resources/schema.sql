
CREATE TABLE IF NOT EXISTS product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128) NOT NULL,
    category           VARCHAR(256)  NOT NULL,
    imag          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ,
    del_flag      varchar(30) DEFAULT '0' ,
    remark        varchar(256)	NULL
);



CREATE TABLE IF NOT EXISTS user
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE KEY,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    roles              varchar(30)  NULL ,
    accesstoken        varchar(256)	NULL ,
    del_flag           varchar(30) DEFAULT '0' ,
    remark             varchar(256)	NULL

);




CREATE TABLE IF NOT EXISTS ordersummary
(
    order_id           INT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id            INT       NOT NULL,
    total_amount       INT       NOT NULL, -- 訂單總花費
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    del_flag           varchar(30) DEFAULT '0' ,
    remark             varchar(256)	NULL
);






CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL, -- 商品數量
    amount        INT NOT NULL , -- 商品花費
    del_flag      varchar(30) DEFAULT '0' ,
    remark        varchar(256)	NULL

);




