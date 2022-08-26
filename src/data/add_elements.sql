
insert into shop (name, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time)
values ('conad', 'via dei pazzi', 'roma', '', '', '', 1, 10,11);

insert into shop (name, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time)
values ('todis', 'via dei pazzi', 'roma', '', '', '', 1, 10,11);

insert into shop (name, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time)
values ('lidl', 'via dei pazzi', 'campobasso', '', '', '', 1, 10,11);

insert into shop (name, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time)
values ('eurospin', 'via mazzini', 'frosinone', '', '', '', 1, 10,11);

insert into shop (name, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time)
values ('conad', 'via ciao ciao ciao', 'roma', 'src/images/shop_images/conad.png', '', '', 1, 10, 11);

insert into department (name, logo_imagepath)
values ('Bakery','bakery.png');
insert into department (name, logo_imagepath)
values ('Frozen','frozen.png');
insert into department (name, logo_imagepath)
values ('Butchery','butchery.png');
insert into department (name, logo_imagepath)
values ('Fishmonger','fishmonger.png');
insert into department (name, logo_imagepath)
values ('Fresh','fresh.png');
insert into department (name, logo_imagepath)
values ('Drinks','drinks.png');
insert into department (name, logo_imagepath)
values ('Care','care.png');
insert into department (name, logo_imagepath)
values ('Fruits&Vegetables','fruits&vegetables.png');
insert into department (name, logo_imagepath)
values ('Pasta','biscuits&pasta.png');
insert into department (name, logo_imagepath)
values ('Biscuits', 'biscuits.png');

insert into shop_department (shop_id, department_id)
values (1,1);
insert into shop_department (shop_id, department_id)
values (1,3);
insert into shop_department (shop_id, department_id)
values (1,4);
insert into shop_department (shop_id, department_id)
values (1,6);
insert into shop_department (shop_id, department_id)
values (1,8);
insert into shop_department (shop_id, department_id)
values (1,9);

insert into shop_department (shop_id, department_id)
values (6,1);
insert into shop_department (shop_id, department_id)
values (6,3);
insert into shop_department (shop_id, department_id)
values (6,4);
insert into shop_department (shop_id, department_id)
values (6,6);
insert into shop_department (shop_id, department_id)
values (6,8);
insert into shop_department (shop_id, department_id)
values (6,9);



insert into userx (username, pass, email, date_of_birth, gender)
values ('gtom', 'gtom','giuseppe.tomassi@gmail.com', '1996-11-06', 'M');


insert into product(sku, name, description, type, weight, logo_imagepath)
values (010102, 'nutella', '500g', 0, 500, '');
insert into product_shop (sku, shop_id, location, amount, qty_on_stock, discounted_amount, available_quantity, number_of_purchase, currency)
values (010102, 1, 'c', 10.02, 1, 10, 1, 1, 'eur');

insert into product(sku, name, description, type, weight, logo_imagepath)
values (1, 'nutkao', '500g', 0, 500, '');
insert into product_shop (sku, shop_id, location, amount, qty_on_stock, discounted_amount, available_quantity, number_of_purchase, currency)
values (1, 1, 'c', 1.00, 1, 1.00, 1, 1, 'eur');


insert into orders(username, payment, total_amount, total_quantity, currency, status)
values ('abc', 'visa', 1.00, 1, 'usd', 'In progress');