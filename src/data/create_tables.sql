create sequence shop_id_sequence;
create table shop(
    shop_id                     numeric(10) primary key default nextval('shop_id_sequence'),
    name                        varchar(100),
    address                     varchar(200),
    city                        varchar(200),
    logo_imagepath              varchar(200),
    interior_photos_imagepath   varchar(200),
    planimetry_imagepath        varchar(200),
    status                      numeric(1),
    opening_time                varchar(10),
    closing_time                varchar(10),
    pdf_path                    varchar(200)
);
create sequence department_id_sequence;
create table department(
    department_id   numeric(10) primary key default nextval('department_id_sequence'),
    name            varchar(50),
    logo_imagepath  varchar(200)
);
create table shop_department(
    shop_id         numeric(10),
    department_id   numeric(10),
    position_X      numeric(10),
    position_Y      numeric(10)
);
create table user_favoriteshop(
    username        varchar(100)    references userx(username),
    shop_id         numeric(10)     references shop(shop_id)
);
create table user_favoriteproduct(
    username        varchar(100)    references userx(username),
    sku             numeric(10)     references product(sku)
);
create sequence orders_id_sequence;
create table orders(
    order_id                    numeric(10)     primary key default nextval('orders_id_sequence'),
    username                    varchar(100)    references userx(username),
    payment                     varchar(100),
    order_timestamp             timestamp       DEFAULT current_timestamp,
    total_amount                float(10),
    total_quantity              numeric(10),
    currency                    varchar(5),
    status                      varchar(100),
    collection_order_timestamp  timestamp
);

/*
json deve contenere
item_amount
currency
sku
quantity_ordered
*/

create table order_items(
    order_id    NUMERIC(10) REFERENCES orders(order_id),
    items       json NOT NULL
);

create table product(
    sku                 NUMERIC(10) primary key default nextval('sku_id_sequence'),
    name                VARCHAR(100),
    description         VARCHAR(100),
    type                NUMERIC(1),
    weight              FLOAT(10),
    logo_imagepath      VARCHAR(200)
);

create table product_shop(
    sku                     NUMERIC(10) REFERENCES product(sku),
    shop_id                 NUMERIC(10) REFERENCES shop(shop_id),
    location                VARCHAR(100),
    amount                  FLOAT(10),
    qty_on_stock            NUMERIC(10),
    discounted_amount       FLOAT(10),
    available_quantity      NUMERIC(100),
    number_of_purchase      NUMERIC(100),
    currency                VARCHAR(5)
);

/*
    aggiungere department_id refences department(department_id)
*/

/*
era stata creata una table con percent_of_discount nel dbms. inutile creare questa field, ma meglio calcolarla a runtime.
'location like scaffale 4 ripiano 5'
*/

create product_userx(
    sku             numeric(10) references product(sku),
    userx           varchar(100) references username
);

create sequence user_id_sequence;
create table userx(
    username        varchar(100) primary key,
    pass            varchar(100),
    email           varchar(200),
    user_id         numeric(10) default nextval('user_id_sequence'),
    date_of_birth   date,
    gender          varchar(1),
    role            varchar(1)
);

create table shopholder_shop(
    username        varchar(100),
    shop_id         numeric(10)
);