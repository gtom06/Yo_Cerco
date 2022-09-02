

insert into shop (name, phone, address, city, logo_imagepath, interior_photos_imagepath, planimetry_imagepath, status, opening_time, closing_time, franchising, latitude, longitude, gmaps_string)
values
    ('conad le pigne', '+393331234567','ss per fiuggi', 'alatri', 'conad_superstore.png', '', '', 1, 9, 20, 'conad superstore', 41.670086,13.3464945, '/CONAD+SUPERSTORE/@41.670082,13.3486832,15z/data=!4m5!3m4!1s0x0:0x57c47ba95c7a5ba3!8m2!3d41.670082!4d13.3486832'),
    ('todis madonna della neve', '+393401234567','via madonna della neve', 'frosinone', 'todis.png', '', '', 1, 9, 21, 'todis', 41.6522526,13.3417693, '/Todis+-+Supermercato+(Frosinone+-+Via+Madonna+della+Neve)/@41.6522566,13.3395806,17z/data=!3m1!4b1!4m5!3m4!1s0x13255a898a8a983f:0x99e0e55bd6c684c3!8m2!3d41.6522592!4d13.3417496'),
    ('lidl maria', '+393881234567','via Maria, 85', 'frosinone', 'lidl.png', '', '', 1, 8, 20, 'lidl', 41.6517691,13.3473583, '/Todis+-+Supermercato+(Frosinone+-+Via+Madonna+della+Neve)/@41.6522566,13.3395806,17z/data=!3m1!4b1!4m5!3m4!1s0x13255a898a8a983f:0x99e0e55bd6c684c3!8m2!3d41.6522592!4d13.3417496'),
    ('conad v. frosinone', '+393401234567','via per frosinone', 'ceccano', 'conad.png', '', '', 1, 8, 20, 'conad', 41.5834142, 13.2649291, '/CONAD/@41.5834414,13.3309611,17z/data=!3m1!4b1!4m5!3m4!1s0x132545a88900734f:0xa3da76bf3d1547b0!8m2!3d41.5834162!4d13.3308459'),
    ('fresco market armando fabi', '+393391234567','via armando fabi', 'frosinone', 'fresco_market.png', '', '', 1, 8, 20, 'fresco market', 41.6358224, 13.3218078, '/CONAD/@41.5834414,13.3309611,17z/data=!3m1!4b1!4m5!3m4!1s0x132545a88900734f:0xa3da76bf3d1547b0!8m2!3d41.5834162!4d13.3308459'),
    ('conad ripi', '+393381234567', 'via casilina 50', 'ripi', 'conad.png', '', '', 1, 8, 20, 'conad', 41.6052828,13.4098633, '/CONAD/@41.6052828,13.4098633,17z/data=!3m1!4b1!4m5!3m4!1s0x13254e29c333045f:0xb1c79b2bad6e9443!8m2!3d41.6052644!4d13.4121517'),
    ('conad city ripi', '+393451234567', 'via meringo alto 19/A', 'ripi', 'conad_city.png', '', '', 1, 8, 20, 'conad city', 41.6100291, 13.4215996, '/CONAD+CITY/@41.6100251,13.4237883,17z/data=!4m5!3m4!1s0x132551dc19293e7f:0xf8df93f76155f515!8m2!3d41.6100323!4d13.4237077'),
    ('conad torrice', '+393311234567', 'via casilina km 90', 'torrice', 'conad.png', '', '', 1, 8, 20, 'conad', 41.6243219, 13.3823068, '/CONAD/@41.6243219,13.3823068,17z/data=!3m1!4b1!4m5!3m4!1s0x13255b22c9544bed:0xf95056b59d31bd0!8m2!3d41.6243179!4d13.3844955'),
    ('conad roma decemviri', '+393330123456', 'piazza dei decemviri 6', 'roma', 'conad.png', '', '', 1, 8, 21, 'conad', 41.8592646, 12.5686261, '/CONAD/@41.8592686,12.5664374,17z/data=!3m2!4b1!5s0x132589d61fa5d5fd:0xb00d72177a05ad3b!4m5!3m4!1s0x132589002acab7dd:0xc20ee8caef38d3d7!8m2!3d41.8592646!4d12.5686261'),
    ('eurospin roma ciamarra', '+393400123456', 'viale antonio ciamarra, 211', 'roma', 'eurospin.png', '', '', 1, 9, 20, 'eurospin', 41.8540214, 12.5873707, '/Eurospin/@41.8540174,12.5895594,17z/data=!3m2!4b1!5s0x13258827acba62ff:0x70c7eacb3356bf9d!4m5!3m4!1s0x1325882650b7ac39:0x8f596e6e8cf84efb!8m2!3d41.8540174!4d12.5895594'),
    ('eurospin roma peikov', '+393880123456', 'piazza ilia peikov', 'roma', 'eurospin.png', '', '', 1, 9, 21, 'eurospin', 41.8315352, 12.5827329, '/Eurospin/@41.8315392,12.5805442,17z/data=!4m5!3m4!1s0x1325884e91813ceb:0x30adcdd3b135c851!8m2!3d41.8316001!4d12.5826743');

insert into department (name, logo_imagepath)
values
        ('Bakery','bakery.png')
        ('Frozen','frozen.png')
        ('Butchery','butchery.png')
        ('Fishmonger','fishmonger.png')
        ('Fresh','fresh.png')
        ('Drinks','drinks.png')
        ('Care','care.png')
        ('Fruits&Vegetables','fruits&vegetables.png')
        ('Pasta','biscuits&pasta.png')
        ('Biscuits', 'biscuits.png');

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