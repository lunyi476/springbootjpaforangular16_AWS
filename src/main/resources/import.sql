--drop table if exists offers CASCADE 
--drop table if exists orders CASCADE 
--drop table if exists quotes CASCADE 
--drop sequence if exists offers_no_seq
--drop sequence if exists orders_no_seq
--drop sequence if exists requests_no_seq

-- create sequence offers_no_seq start with 1 increment by 50;
-- create sequence orders_no_seq start with 1 increment by 50;
--
-- create sequence requests_no_seq start with 1 increment by 50;
--
-- create table offers (offer_no integer not null, owner varchar(255) not null, request_no integer not null, buyer varchar(255), carrier varchar(255), currency varchar(255), delivery_terms varchar(255), description varchar(255), final_dest varchar(255), first_ship_date date, item varchar(255) not null, lading_point varchar(255), last_ship_date date, manufacturer varchar(255), offer_date date, origin_cntry varchar(255), payment_terms varchar(255), payment_type varchar(255), status varchar(255), supplier varchar(255), vendor_name varchar(255), primary key (offer_no, owner, request_no));
--
-- create table orders (order_no integer not null, buyer varchar(255), carrier varchar(255), currency varchar(255), delivery_terms varchar(255), description varchar(255), final_dest varchar(255), first_ship_date date, item varchar(255) not null, lading_point varchar(255), last_ship_date date, manufacturer varchar(255), modify_ts timestamp, modify_user varchar(255), offer_no integer not null, order_date date, origin_cntry varchar(255), owner varchar(255) not null, payment_terms varchar(255), payment_type varchar(255), status varchar(255), supplier varchar(255), tot_calc_cost decimal(7,2), tot_grs_value decimal(7,2), tot_grs_wgt decimal(7,2), tot_qty decimal(7,2), vendor_name varchar(255), primary key (order_no));
--
-- create table quotes (owner varchar(255) not null, request_no integer not null, allocated_qty decimal(19,2), brand varchar(255), buyer varchar(255), category varchar(255), currency varchar(255), delivery_terms varchar(255), dept varchar(255), description varchar(255), division varchar(255), hts_no varchar(255), import_cntry varchar(255), item varchar(255) not null, item_class varchar(255), modify_ts timestamp, modify_user varchar(255), request_price decimal(19,2), requested_by varchar(255), primary key (owner, request_no));
-- alter table offers add constraint FK4vynfpu4tr1281vhuvew3xqh7 foreign key (owner, request_no) references quotes;


-- for h2
INSERT INTO quotes (request_no, owner,item, description, requested_by,category,item_class,division,brand,dept,buyer,hts_no,	request_price,currency,delivery_terms,import_cntry,allocated_qty,modify_ts,modify_user) VALUES (NEXT VALUE FOR REQUESTS_NO_SEQ, 'BAMBOOROSE','Cheese','Food Ingresdient','Lun Yi','Food','first','food',	'Stop-Shop','Food',	'Vendor','AOA',100.00,	'USD',	'AIR',	'US',1000,	NULL, 'Lun Yi');
INSERT INTO offers (offer_no, owner,request_no, item, offer_date, supplier, currency,payment_type,  payment_terms,delivery_terms, origin_cntry,lading_point, first_ship_date, last_ship_date, buyer, final_dest) select NEXT VALUE FOR OFFERS_NO_SEQ, 'BAMBOOROSE', max(request_no),'Cheese', '2020-01-01', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'Boston',   NULL, '2020-11-01', 'Divid', 'China' from quotes;

INSERT INTO quotes (request_no,  owner,item, description,requested_by,category,item_class,division,brand,dept,buyer,hts_no,request_price,currency,delivery_terms,import_cntry,allocated_qty,modify_ts,modify_user) VALUES (NEXT VALUE FOR REQUESTS_NO_SEQ, 'BAMBOOROSE','Pork Shoulder','Food','Lun Yi','Food','first','food','Stop-Shop','Food','Vendor','AOA',100.00,'USD','AIR','US',1000, NULL, 'Lun Yi');

INSERT INTO offers (offer_no, owner,request_no, item, offer_date, supplier, currency,payment_type,  payment_terms,delivery_terms,origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select NEXT VALUE FOR OFFERS_NO_SEQ, 'BAMBOOROSE', max(request_no), 'Pork Shoulder', NULL, 'Stop-Shop', 'USD','Cash', 'COD', 'FCA', 'USA', 'CT', null,  '2020-11-2', 'Peter', 'India'  from quotes;

INSERT INTO orders (order_no, order_date, tot_grs_value, tot_calc_cost, tot_qty, tot_grs_wgt, offer_no, owner, item,  supplier, currency,payment_type,  payment_terms,delivery_terms, origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select NEXT VALUE FOR ORDERS_NO_SEQ, '2020-11-01', 1005, 105, 10005, 285, max(offer_no),  'BAMBOOROSE', 'Cheese', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'Boston', '2020-11-02', null, 'David','China' from offers;

INSERT INTO orders (order_no, order_date, tot_grs_value, tot_calc_cost, tot_qty, tot_grs_wgt, offer_no, owner, item,  supplier, currency,payment_type,  payment_terms,delivery_terms,origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select NEXT VALUE FOR ORDERS_NO_SEQ, '2020-11-02', 1000, 100, 10000, 280, max(offer_no),  'BAMBOOROSE',  'Pork Shoulder', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'CT','2020-11-03',  null, 'Peter','India' from offers;



-- for postgresql
--INSERT INTO quotes (request_no, owner,item, description, requested_by,category,item_class,division,brand,dept,buyer,hts_no,	request_price,currency,delivery_terms,import_cntry,allocated_qty,modify_ts,modify_user) VALUES (nextval('REQUESTS_NO_SEQ'), 'BAMBOOROSE','Cheese','Food Ingresdient','Lun Yi','Food','first','food',	'Stop-Shop','Food',	'Vendor','AOA',100.00,	'USD',	'AIR',	'US',1000,	NULL, 'Lun Yi');
--INSERT INTO offers (offer_no, owner,request_no, item, offer_date, supplier, currency,payment_type,  payment_terms,delivery_terms, origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select nextval('OFFERS_NO_SEQ'), 'BAMBOOROSE', max(request_no),'Cheese', '2020-01-01', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'Boston',   NULL, '2020-11-01', 'David', 'China' from quotes;

--INSERT INTO quotes (request_no,  owner,item, description,requested_by,category,item_class,division,brand,dept,buyer,hts_no,request_price,currency,delivery_terms,import_cntry,allocated_qty,modify_ts,modify_user) VALUES (nextval('REQUESTS_NO_SEQ'), 'BAMBOOROSE','Pork Shoulder','Food','Lun Yi','Food','first','food','Stop-Shop','Food','Vendor','AOA',100.00,'USD','AIR','US',1000, NULL, 'Lun Yi');

--INSERT INTO offers (offer_no, owner,request_no, item, offer_date, supplier, currency,payment_type,  payment_terms,delivery_terms,origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select nextval('OFFERS_NO_SEQ'), 'BAMBOOROSE', max(request_no), 'Pork Shoulder', NULL, 'Stop-Shop', 'USD','Cash', 'COD', 'FCA', 'USA', 'CT', null,  '2020-11-2', 'Peter', 'India'  from quotes;

--INSERT INTO orders (order_no, order_date, tot_grs_value, tot_calc_cost, tot_qty, tot_grs_wgt, offer_no, owner, item,  supplier, currency,payment_type,  payment_terms,delivery_terms, origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select nextval('ORDERS_NO_SEQ'), '2020-11-01', 1005, 105, 10005, 285, max(offer_no),  'BAMBOOROSE', 'Cheese', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'Boston', '2020-11-02', null, 'David', 'China' from offers;

--INSERT INTO orders (order_no, order_date, tot_grs_value, tot_calc_cost, tot_qty, tot_grs_wgt, offer_no, owner, item,  supplier, currency,payment_type,  payment_terms,delivery_terms,origin_cntry,lading_point, first_ship_date, last_ship_date, buyer,final_dest) select nextval('ORDERS_NO_SEQ'), '2020-11-02', 1000, 100, 10000, 280, max(offer_no),  'BAMBOOROSE',  'Pork Shoulder', 'Stop-Shop', 'USD','Cash', 'COD', 'FCA',  'USA', 'CT','2020-11-03',  null, 'Peter', 'India' from offers;
