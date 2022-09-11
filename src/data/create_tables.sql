CREATE SEQUENCE shop_id_sequence;

CREATE TABLE shop(
    shop_id                     NUMERIC(10) PRIMARY KEY DEFAULT nextval('shop_id_sequence'),
    name                        VARCHAR(100),
    phone                       VARCHAR(40),
    address                     VARCHAR(200),
    city                        VARCHAR(200),
    logo_imagepath              VARCHAR(200),
    interior_photos_imagepath   VARCHAR(200),
    planimetry_imagepath        VARCHAR(200),
    status                      VARCHAR(1),
    opening_time                VARCHAR(10),
    closing_time                VARCHAR(10),
    pdf_path                    VARCHAR(200),
    franchising                 VARCHAR(100),
    latitude                    FLOAT(10),
    longitude                   FLOAT(10),
    gmaps_string                VARCHAR(500),
    type                        VARCHAR(30)
);

CREATE SEQUENCE department_id_sequence;

CREATE TABLE department(
    department_id   NUMERIC(10) PRIMARY KEY DEFAULT nextval('department_id_sequence'),
    name            VARCHAR(50),
    logo_imagepath  VARCHAR(50)
);

CREATE TABLE shop_department(
    shop_id         NUMERIC(10),
    department_id   NUMERIC(10),
    position_X      NUMERIC(10) DEFAULT 0,
    position_Y      NUMERIC(10) DEFAULT 0
);

CREATE TABLE user_favoriteshop(
    username        VARCHAR(100)    REFERENCES userx(username),
    shop_id         NUMERIC(10)     REFERENCES shop(shop_id)
);

CREATE TABLE user_favoriteproduct(
    username                    VARCHAR(100)    REFERENCES userx(username),
    sku                         NUMERIC(10)     REFERENCES product(sku)
);

CREATE SEQUENCE orders_id_sequence;

CREATE TABLE orders(
    order_id                    NUMERIC(10)     PRIMARY KEY DEFAULT nextval('orders_id_sequence'),
    shop_id                     NUMERIC(10)     REFERENCES shop(shop_id),
    username                    VARCHAR(100)    REFERENCES userx(username),
    payment_id                  NUMERIC(10)     REFERENCES payment(payment_id),
    order_timestamp             TIMESTAMP       DEFAULT current_timestamp,
    total_price                 FLOAT(10),
    total_quantity              NUMERIC(10),
    currency                    VARCHAR(5),
    status                      VARCHAR(100)    DEFAULT 'Created',
    collection_order_timestamp  TIMESTAMP
);

CREATE SEQUENCE payment_id_sequence;

CREATE TABLE payment(
    payment_id              NUMERIC(10)     PRIMARY KEY DEFAULT nextval('payment_id_sequence'),
    last_4_digits           VARCHAR(4),
    mm                      VARCHAR(2),
    yy                      VARCHAR(2),
    payment_method          VARCHAR(20),
    cardholder              VARCHAR(100),
    total_price             FLOAT(10),
    currency                VARCHAR(10),
    payment_timestamp       TIMESTAMP       DEFAULT current_timestamp,
    status                  VARCHAR(100)    DEFAULT 'Created'
);

CREATE TABLE order_items(
    order_id                NUMERIC(10) REFERENCES orders(order_id),
    items                   VARCHAR(10485760)
);

CREATE SEQUENCE sku_id_sequence;

CREATE TABLE product(
    sku                     NUMERIC(10) PRIMARY KEY DEFAULT nextval('sku_id_sequence'),
    name                    VARCHAR(100),
    brand                   VARCHAR(40),
    description             VARCHAR(100),
    size                    FLOAT(10),
    unit_of_measure         VARCHAR(10),
    logo_imagepath          VARCHAR(200)
);

CREATE TABLE product_shop(
    sku                     NUMERIC(10) REFERENCES product(sku),
    shop_id                 NUMERIC(10) REFERENCES shop(shop_id),
    department_id           NUMERIC(10) REFERENCES department(department_id),
    location                VARCHAR(100),
    price                   FLOAT(10),
    discounted_price        FLOAT(10),
    currency                VARCHAR(5)
);

/*
era stata creata una table con percent_of_discount nel dbms. inutile creare questa field, ma meglio calcolarla a runtime.
'location like scaffale 4 ripiano 5'
*/

CREATE TABLE product_userx(
    sku                     NUMERIC(10) REFERENCES product(sku),
    username                VARCHAR(100) REFERENCES userx(username)
);

CREATE SEQUENCE user_id_sequence;

CREATE TABLE userx(
    username                VARCHAR(100) PRIMARY KEY,
    pass                    VARCHAR(100),
    email                   VARCHAR(200),
    user_id                 NUMERIC(10) DEFAULT nextval('user_id_sequence'),
    date_of_birth           DATE,
    gender                  VARCHAR(1),
    role                    VARCHAR(1),
    name                    VARCHAR(50),
    surname                 VARCHAR(50),
    billing_street          VARCHAR(255),
    billing_city            VARCHAR(50),
    billing_country         VARCHAR(50),
    billing_zip             VARCHAR(10),
    phone                   VARCHAR(20),
    profile_imagepath       VARCHAR(200) DEFAULT 'icons8-customer-100.png'
);

CREATE TABLE shopholder_shop(
    username                VARCHAR(100),
    shop_id                 NUMERIC(10)
);