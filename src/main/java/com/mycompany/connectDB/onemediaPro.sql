create database onemediapro;
use onemediapro;

create table `user` (
	`id` int not null auto_increment,
    `userName` VARCHAR(50) not null,
    `passWord` varchar(255) not null,
    `tenkhachhang` varchar(20) not null,
    `sodienthoai` varchar(11) not null,
    `diachi` varchar(45) not null,
    `chucnang` boolean not null,
    constraint PK_admin primary key (id)
);

insert into `user` (`userName`,`passWord`,`tenkhachhang`,`sodienthoai`,`diachi`,`chucnang`) values 
					('vinh.pn@mail.com','admin','phung ngoc vinh', '0867630856', 'quoc oai - ha noi', 1),
					('dieu.nth@mail.com','admin','ngo thi huyen dieu', '0375384273', 'tien du - bac ninh', 1),
                    ('1','1','phung ngoc vinh', '0867630856', 'quoc oai - ha noi', 1),
                    ('2','2','phung ngoc vinh', '0867630856', 'quoc oai - ha noi', 0),
                    ('admin@mail.com','admin','admin', '0867630878', 'quoc oai - ha noi', 0);
create table `sach` (
	`masanpham` int auto_increment,
    `tensanpham` varchar(45) not null,
    `nhaxuatban` varchar(45) not null,
    `tacgia` varchar(20) not null,
    `theloai` varchar(20) not null,
    `soluong` int not null,
    `giamua` decimal(18,2) not null,
    `giaban` decimal(18,2) not null,
    `image` text not null,
    constraint PK_sach primary key (masanpham)
);

insert into `sach` (`masanpham`,`tensanpham`,`nhaxuatban`,`tacgia`,`theloai`,`soluong`,`giamua`,`giaban`,`image`) values
					(31,"It","Viking Press", "Stephen King","horror",100,25000,30000,"it.png"),
					(32,"The Shining","Doubleday", "Stephen King","horror",150,25000,30000,"shining.jpg"),
                    (33,"Dragon","TSR", "Lovecraft","horror",100,25000,30000,"dragon.jpg"),
                    (34,"De men phieu luu ky","Nha xuat Kim Dong", "To Hoai","Truyen tranh",100,25000,30000,"demen.jpg");

create table `dianhac` (
	`masanpham` int auto_increment,
    `tensanpham` varchar(45) not null,
    `nhasanxuat` varchar(45) not null,
    `nghesi` varchar(20) not null,
    `thoiluong` int not null,
    `theloai` varchar(20) not null,
    `soluong` int not null,
    `giamua` decimal(18,2) not null,
    `giaban` decimal(18,2) not null,
    `image` text not null,
    constraint PK_sach primary key (masanpham)
);

insert into `dianhac` (`masanpham`,`tensanpham`,`nhasanxuat`,`nghesi`,`thoiluong`,`theloai`,`soluong`,`giamua`,`giaban`,`image`) values
						(61,"Mint Jams","Alfa Records","Casiopea",150,"jazz fusion",50,15000.4,20000,"mint.jpg"),
						(62,"The Beatles","George Martin","The Beatles",120,"pop & rock",50,15000,20000,"beatles.png");

create table `diaphim` (
	`masanpham` int auto_increment,
    `tensanpham` varchar(45) not null,
    `nhasanxuat` varchar(45) not null,
    `daodien` varchar(20) not null,
    `thoiluong` int not null,
    `theloai` varchar(20) not null,
    `soluong` int not null,
    `giamua` decimal(18,2) not null,
    `giaban` decimal(18,2) not null,
    `image` text not null,
    constraint PK_sach primary key (masanpham)
);

insert into `diaphim` (`masanpham`,`tensanpham`,`nhasanxuat`,`daodien`,`thoiluong`,`theloai`, `soluong`,`giamua`,`giaban`,`image`) values
						(1,"The Lion King","Walt Disney","Roger Allers",87,"animation",50,20000,25000,"lionking.png"),
                        (2,"Justice League","DC Comics","Zach Synder",240,"superheroes",50,20000,25000,"justice.png"),
                        (3,"Up","Pixar","Pete Docter",90,"animation",50,20000,25000,"up.png"),
                        (4,"The Incredibles","Pixar","Brad Bird",115,"animation",50,20000,25000,"increibles.png");

create table `hoadon` (
	`idHD` int auto_increment,
    `id` int,
    `ngaytaodon` date not null,
    `chietkhau` double not null,
    `thanhtien` double not null,
    foreign key (id) references `user`(id),
    constraint PK_HD primary key (idHD)
);

insert into `hoadon` (`id`,`ngaytaodon`,`chietkhau`,`thanhtien`) values 
	(1,"2022-07-15",0.15,573750),
    (2,"2022-07-15",0.15,255000);


create table `chitiethd`(
	`idHD` int not null,
	`masanpham` int,
    `soluong` int not null,
    `giatri` double not null,
    foreign key (idHD) references `hoadon`(idHD)
);

insert into `chitietHD` (`idHD`,`masanpham`,`soluong`,`giatri`) values
	(1,1,5,125000),
    (1,2,10,250000),
    (1,31,10,300000),
    (2,31,10,300000);
    
select  masanpham, SUM(soluong) from chitiethd  inner join hoadon on chitiethd.idHD  = hoadon.idHD where hoadon.ngaytaodon between "2022-07-15" and "2022-07-18" GROUP BY masanpham ORDER by SUM(soluong) DESC;

create table `chiphikhac`(
	`idCPK` int auto_increment not null,
    `tenchiphi` varchar(255) not null,
    `chiphi` double not null,
    `thoigiantao` datetime,
    constraint PK_CPK primary key(idCPK)
);
