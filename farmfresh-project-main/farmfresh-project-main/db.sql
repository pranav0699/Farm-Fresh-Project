create table user(
user_id int primary key,
address varchar(200),
email varchar(20),
firstname varchar(20),
lastname varchar(20),
password varchar(20),
is_admin bit,
phone_no varchar(15)
);

create table orders(
order_id int primary key,
delivery_status bit,
payment_status bit,
order_placed_date date,
delivery_date date,
user_id int,
constraint f1_user_id foreign key (user_id) references user (user_id)
on delete set null
on update cascade
);

create table farmer(
farmer_id int primary_key,
address varchar(200),
email varchar(20),
firstname varchar(20),
lastname varchar(20),
phone_no varchar(15)
);

create table order_details(
id int primary key,
amount double(9,2),
order_item varchar(45),
quantity int,
farmer_id int,
order_id int,
constraint f1_farmer_id foreign key (farmer_id) references farmer (farmer_id)
constraint f1_order_id foreign key (order_id) references order (order_id)
on delete set null
on update cascade
);

create table category(
category_id int,
category_name  varchar(50)
);

create table stock_details(
product_id int primary key,
price_per_unit float(9,2),
quantity int,
stock_item varchar(45),
farmer_id int,
category_id int,
constraint f2_farmer_id foreign key (farmer_id) references farmer (farmer_id)
constraint f1_category_id foreign key (category_id) references category (category_id)
on delete set null
on update cascade
);





















