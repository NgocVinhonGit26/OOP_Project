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
					(5,"It","Viking Press", "Stephen King","horror",100,25000,30000,"it.png"),
					(6,"The Shining","Doubleday", "Stephen King","horror",150,25000,30000,"shining.jpg"),
                    (7,"Dragon","TSR", "Lovecraft","horror",100,25000,30000,"dragon.jpg"),
                    (8,"De men phieu luu ky","Nha xuat Kim Dong", "To Hoai","Truyen tranh",100,25000,30000,"demen.jpg");

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
						(9,"Mint Jams","Alfa Records","Casiopea",150,"jazz fusion",50,15000.4,20000,"mint.jpg"),
						(10,"The Beatles","George Martin","The Beatles",120,"pop & rock",50,15000,20000,"beatles.png");

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
	`idHD` varchar(10) not null,
    `idKH` int,
    `maspSach` int,
    `maspNhac` int,
    `maspPhim` int,
    `ngaytaodon` datetime not null,
    `dongia` double not null,
    `chietkhau` double not null,
    `thanhtien` double not null,
    foreign key (idKH) references `user`(id),
    foreign key (maspSach) references `sach`(masanpham),
    foreign key (maspNhac) references `dianhac`(masanpham),
    foreign key (maspPhim) references `diaphim`(masanpham),
    constraint PK_HD primary key (idHD)
);

create table `chiphikhac`(
	`idCPK` int auto_increment not null,
    `tenchiphi` varchar(255) not null,
    `chiphi` double not null,
    `thoigiantao` datetime,
    constraint PK_CPK primary key(idCPK)
);