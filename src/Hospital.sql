alter table chief drop column isWorking;
alter table chief add isWorking int default 0;

create table Department (ID int auto_increment,
                         Name varchar(255) DEFAULT NULL,
                         CONSTRAINT PRIMARY KEY (ID)
);

insert into Department(ID,Name) value (1,'骨科'),(2,'儿科'),(3,'内科'),(4,'外科');

ALTER TABLE chief ADD CONSTRAINT  FOREIGN KEY(Work) REFERENCES Department(ID);


insert into Department(ID,Name) value (5,'其他');

insert into chief(ID, UserName, Password, Age, Sex, RealName, WorkTime, Work, isWorking) VALUES
(7,'ap','123456',34,0,'李子','周三下午',5,0),
(8,'abp','123456',35,1,'克哦','周六',3,1),
(9,'abcp','123456',45,0,'松江','周三',2,0),
(10,'abcdp','123456',67,0,'李逵','周五',1,0),
(11,'abcd1p','123456',44,0,'武松','周四',1,0),
(12,'a1p','123456',45,0,'孙悟空','周二',2,0),
(13,'a2p','123456',76,0,'猪八戒','周三',3,0),
(14,'a3p','123456',56,0,'唐僧','周一',4,0),
(15,'a4p','123456',55,0,'韩寒','周六',4,0),
(16,'a5p','123456',56,0,'李白','周五',3,0),
(17,'a6p','123456',57,0,'杜甫','周二',2,0),
(18,'a7p','123456',45,0,'任晗','周三',2,0),
(19,'a8p','123456',48,0,'鹿晗','周一',5,0);


create table Information (ID int auto_increment,
                          Text_Type int DEFAULT 0,
                          Text varchar(1000) DEFAULT NULL,
                          CONSTRAINT PRIMARY KEY (ID),
                          CONSTRAINT FOREIGN KEY(Text_Type) REFERENCES user(ID)
);

insert into Information(ID, Text_Type, Text) VALUES (1,0,'尊敬的用户，因疫情防控需求医院内请带好口罩，保持安全距离。');
insert into Information(ID,Text_Type,Text) VALUES (2,1,'尊敬的用户，您预约的骨科门诊已过期！');
alter table Information DROP Time;
alter table Information ADD Time DATETIME DEFAULT NOW();

select * from Information where Text_Type=?;


create table Admin(
                      ID int auto_increment,
                      UserName varchar(255),
                      PassWord varchar(255),
                      RealName varchar(255),
                      WorkType int default 0,
                      PRIMARY KEY (ID)
);

insert into Admin (ID, UserName, PassWord, RealName, WorkType) VALUES (1,'admin','123','x医院','0');

insert into Information ( Text_Type, Text) VALUES (0,'a');

select Text_Type from Information where Text_Type = 1;

select ID from User where ID = ?;

insert into User(UserName, Password, Age, Sex, IDCARD, RealName) value(?,?,?,?,?,?);

ALTER TABLE user ADD UNIQUE (UserName);