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
    department_id   numeric(10)
);

create table user_favoriteshop(
    username        varchar(100),
    shop_id         numeric(10) references
);

create sequence sku_id_sequence;
create table product(
    sku             numeric(10) primary key default nextval('sku_id_sequence'),
    name            varchar(100),
    description     varchar(100),
    type            numeric(1),
    weight          float(10),
    logo_imagepath  varchar(200)
);

create table product_shop(
    sku                 numeric(10) references product(sku),
    shop_id             numeric(10) references shop(shop_id),
    location            varchar(100),
    amount              float(10),
    qty_on_stock        numeric(10),
    discounted_amount   float(10),
    percent_of_discount float(10) GENERATED ALWAYS AS (100*(amount-discounted_amount)/amount) STORED,
    available_quantity  numeric(100),
    number_of_purchase  numeric(100),
    currency            varchar(5)
);

'location like scaffale 4 ripiano 5'

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