use mycas;
create table student(
    id int,
    address text,
    name text,
    age int,
    height int,
    primary key(id,address,name)
);

insert into student(id,address,name,age,height) values(1,'guangdong','lixiao',32,172);

-- Only clustering key columns can be defined in CLUSTERING ORDER directive
create table teacher(
    id int,
    address text,
    name text,
    age int,
    height int,
    primary key(id,address,name)
);
insert into teacher(id,address,name,age,height) values(1,'guangdong','lixiao',32,172);
insert into teacher(id,address,name,age,height) values(1,'guangxi','linzexu',68,178);
insert into teacher(id,address,name,age,height) values(1,'guangxi','lihao',25,178);
insert into teacher(id,address,name,age,height) values(2,'guangxi','lixiaolong',32,172);
insert into teacher(id,address,name,age,height) values(2,'guangdong','lixiao',32,172);
insert into teacher(id,address,name,age,height) values(2,'guangxi','linzexu',68,178);
insert into teacher(id,address,name,age,height) values(2,'guangxi','lihao',25,178);
insert into teacher(id,address,name,age,height) values(2,'guangxi','nnd',32,172);

CREATE INDEX idx_teacher_name on teacher(name);
CREATE INDEX idx_teacher_age on teacher(age);

SELECT * FROM teacher WHERE id=1 ORDER BY address ASC;
SELECT * FROM teacher WHERE id=1 ORDER BY address ASC, name ASC;
SELECT * FROM teacher WHERE id=1 AND address='guangxi' ORDER BY address ASC;
SELECT * FROM teacher WHERE id=1 AND address='guangxi' ORDER BY address ASC, name ASC;
SELECT * FROM teacher WHERE id=1 ORDER BY address DESC;
SELECT * FROM teacher WHERE id=1 ORDER BY address DESC, name DESC;
SELECT * FROM teacher WHERE id=1 AND address='guangxi' ORDER BY address DESC;
SELECT * FROM teacher WHERE id=1 AND address='guangxi' ORDER BY address DESC, name DESC;


SELECT * FROM teacher ORDER BY address DESC;                        //没有第一主键 不行
SELECT * FROM teacher WHERE id=1 ORDER BY name DESC;                //必须以第二主键开始排序
SELECT * FROM teacher WHERE id=1 ORDER BY address DESC, name ASC;   //不是与建表时指定的排序一致或者完全相反 (默认是address ASC, name ASC)
SELECT * FROM teacher WHERE age=1 ORDER BY address DESC;            //不能有索引
SELECT * FROM tt WHERE id=1 ORDER BY address DESC, name DESC;       //不是与建表时指定的排序一致或者完全相反 (建表时指定了address DESC, name ASC)

create table tt(
    id int,
    address text,
    name text,
    age int,
    height int,
    primary key(id,address,name)
)WITH CLUSTERING ORDER BY(address DESC, name ASC);
insert into tt(id,address,name,age,height) values(1,'guangdong','lixiao',32,172);
insert into tt(id,address,name,age,height) values(1,'guangxi','linzexu',68,178);
insert into tt(id,address,name,age,height) values(1,'guangxi','lihao',25,178);
insert into tt(id,address,name,age,height) values(2,'guangxi','lixiaolong',32,172);
insert into tt(id,address,name,age,height) values(2,'guangdong','lixiao',32,172);
insert into tt(id,address,name,age,height) values(2,'guangxi','linzexu',68,178);
insert into tt(id,address,name,age,height) values(2,'guangxi','lihao',25,178);
insert into tt(id,address,name,age,height) values(2,'guangxi','nnd',32,172);

SELECT * FROM tt WHERE id=1 ORDER BY address DESC;
SELECT * FROM tt WHERE id=1 ORDER BY address DESC, name ASC;
SELECT * FROM tt WHERE id=1 AND address='guangxi' ORDER BY address DESC;
SELECT * FROM tt WHERE id=1 AND address='guangxi' ORDER BY address DESC, name ASC;
SELECT * FROM tt WHERE id=1 ORDER BY address ASC;
SELECT * FROM tt WHERE id=1 ORDER BY address ASC, name DESC;
SELECT * FROM tt WHERE id=1 AND address='guangxi' ORDER BY address ASC;
SELECT * FROM tt WHERE id=1 AND address='guangxi' ORDER BY address ASC, name DESC;

SELECT * FROM tt WHERE id=1 ORDER BY address DESC, name DESC;   //不是与建表时指定的排序一致或者完全相反 (建表时指定了address DESC, name ASC)
