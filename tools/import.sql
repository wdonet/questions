drop table if exists answer cascade;
drop table if exists question cascade;
drop table if exists question__tags cascade;
drop table if exists tag cascade;
drop sequence hibernate_sequence;

create table answer (_id int8 not null, _description varchar(255) not null, _question__id int8 not null, primary key (_id));
create table question (_id int8 not null, _description varchar(255) not null, _title varchar(255) not null, _total_answers int4 not null, primary key (_id));
create table question__tags (question__id int8 not null, _tags__id int8 not null);
alter table question__tags add constraint UK_a5a3q5xc4cwk51ywg5s3trakm unique (_tags__id);
alter table question__tags add constraint FK_a5a3q5xc4cwk51ywg5s3trakm foreign key (_tags__id) references tag;
alter table answer add constraint FK_925mn0ji18ututsrf5yr0lwb3 foreign key (_question__id) references question;
alter table question__tags add constraint FK_kq78vj03a09yb7rawf4052ls3 foreign key (question__id) references question;
create sequence hibernate_sequence;
