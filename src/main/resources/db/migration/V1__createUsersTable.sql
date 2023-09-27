create table users (
     id bigint not null auto_increment,
     name varchar(255) not null,
     role varchar(100) not null,
     login varchar(100) not null,
     password varchar(255) not null,
     token_email varchar(255),
     token_expiration datetime,

     primary key (id)
 )