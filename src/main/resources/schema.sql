create table if not exists product (id SERIAL PRIMARY KEY, product varchar(250) NOT NULL, price int, quantity int,  
CONSTRAINT product_unique UNIQUE (product));
create unique index if not exists ppp on product (price, product, quantity);
create table if not exists client (id SERIAL PRIMARY KEY, name varchar(150) NOT NULL);
create unique index if not exists ccc on client (name);
create table if not exists order1 (id_product int, id_client int, purch_date date, quantity int,
CONSTRAINT fk_product FOREIGN KEY(id_product) REFERENCES product(id), 
CONSTRAINT fk_client FOREIGN KEY(id_client) REFERENCES client(id));

insert into product (price, product, quantity) values (50, 'картофельные очистки', 160) 
on conflict (price, product, quantity) DO UPDATE set price=50, product='картофельные очистки', quantity=160;
insert into product (price, product, quantity) values (70, 'булки', 40)
on conflict (price, product, quantity) DO UPDATE set price=70, product='булки', quantity=40;
insert into product (price, product, quantity) values (40, 'шкурки от бананов', 200) 
on conflict (price, product, quantity) DO UPDATE set price=40, product='шкурки от бананов', quantity=200;
insert into client (name) values ('Геннадий') 
on conflict (name) DO UPDATE set name='Геннадий';
insert into client (name) values ('Аделаида') 
on conflict (name) DO UPDATE set name='Аделаида';
insert into client (name) values ('Семён') 
on conflict (name) DO UPDATE set name='Семён';
create unique index if not exists ord on order1 (id_product, id_client, purch_date, quantity);
insert into order1 (id_product, id_client, purch_date, quantity) values (1, 2, '1-2-2015', 120)
on conflict (id_product, id_client, purch_date, quantity) DO NOTHING;
insert into order1 (id_product, id_client, purch_date, quantity) values (2, 3, '3-5-2017', 100)
on conflict (id_product, id_client, purch_date, quantity) DO NOTHING;
insert into order1 (id_product, id_client, purch_date, quantity) values (3, 2, '8-10-2015', 150)
on conflict (id_product, id_client, purch_date, quantity) DO NOTHING;
