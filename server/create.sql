create table appointment (appointment_id integer not null auto_increment, appointment_description varchar(255), appointment_end_date_time datetime(6), appointment_location varchar(255), appointment_start_date_time datetime(6), appointment_title varchar(255), appointment_type varchar(255), contact_id integer, customer_id integer, user_id integer, primary key (appointment_id)) engine=InnoDB;
create table contact (contact_id integer not null auto_increment, contact_name varchar(255), primary key (contact_id)) engine=InnoDB;
create table country (country_id integer not null auto_increment, country_name varchar(255), primary key (country_id)) engine=InnoDB;
create table customer (customer_id integer not null auto_increment, address varchar(255), customer_name varchar(255), phone varchar(255), division_id integer, primary key (customer_id)) engine=InnoDB;
create table first_level_division (first_level_division_id integer not null auto_increment, first_level_division_name varchar(255), country_id integer, primary key (first_level_division_id)) engine=InnoDB;
create table user (user_id integer not null auto_increment, password varchar(255), username varchar(255), primary key (user_id)) engine=InnoDB;
alter table appointment add constraint FK5s2tivc4ecs0wrb4r0iinfyos foreign key (contact_id) references contact (contact_id);
alter table appointment add constraint FKmyowslj1th8d9j6j3wlbwrtoe foreign key (customer_id) references customer (customer_id);
alter table appointment add constraint FKa8m1smlfsc8kkjn2t6wpdmysk foreign key (user_id) references user (user_id);
alter table customer add constraint FKal8onrvcgbgjfsgpy1ffq9q3n foreign key (division_id) references first_level_division (first_level_division_id);
alter table first_level_division add constraint FKqahljapofw1ertehd5yetgoun foreign key (country_id) references country (country_id);
