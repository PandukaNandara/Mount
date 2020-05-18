CREATE DATABASE IF NOT EXISTS mc_activity_mgt_sys;
use mc_activity_mgt_sys;
create table age_group
(
  GID        int(4) auto_increment
    primary key,
  group_name varchar(100) not null,
  min        int          null,
  max        int          null
)
  engine = InnoDB;

create table competition
(
  CID      int(4) auto_increment
    primary key,
  cName    varchar(200) not null,
  location varchar(100) not null,
  Date     date         null,
  desc_    varchar(500) null
)
  engine = InnoDB;

create table login
(
  logInID  int auto_increment
    primary key,
  userName varchar(100) not null,
  password varchar(400) not null,
  salt     varchar(400) not null
)
  engine = InnoDB;

create table student
(
  SID         int(4) auto_increment
    primary key,
  sName       varchar(100)             not null,
  gender      tinyint(1)               not null,
  DOB         date                     not null,
  class       char(4)                  not null,
  father_name varchar(100)             null,
  mother_name varchar(100)             null,
  note        varchar(500) default ' ' null,
  house       varchar(20)              null,
  address     varchar(400)             null
)
  engine = InnoDB;

create table teacher
(
  TID   int(4) auto_increment
    primary key,
  tName varchar(100) not null
)
  engine = InnoDB;

create table activity
(
  AID   int(4) auto_increment
    primary key,
  aName varchar(100) not null,
  TID   int(4)       not null,
  constraint activity_ibfk_1
    foreign key (TID) references teacher (TID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index activity_ibfk_1
  on activity (TID);

create table event
(
  EID    int(4) auto_increment
    primary key,
  eName  varchar(100) not null,
  gender tinyint(1)   not null,
  AID    int(4)       not null,
  constraint event_ibfk_1
    foreign key (AID) references activity (AID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index event_ibfk_1
  on event (AID);

create table event_list
(
  ELID int(4) auto_increment
    primary key,
  CID  int(4) not null,
  EID  int(4) not null,
  GID  int(4) not null,
  constraint event_list_ibfk_1
    foreign key (CID) references competition (CID)
      on update cascade
      on delete cascade,
  constraint event_list_ibfk_2
    foreign key (EID) references event (EID)
      on update cascade
      on delete cascade,
  constraint event_list_ibfk_3
    foreign key (GID) references age_group (GID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index event_list_ibfk_1
  on event_list (CID);

create index event_list_ibfk_2
  on event_list (EID);

create index event_list_ibfk_3
  on event_list (GID);

create table registration
(
  RID         int(4) auto_increment
    primary key,
  SID         int(4) not null,
  AID         int(4) not null,
  joined_date date   not null,
  constraint registration_ibfk_1
    foreign key (SID) references student (SID)
      on update cascade
      on delete cascade,
  constraint registration_ibfk_2
    foreign key (AID) references activity (AID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create table attendant_sheet
(
  ATID int(4) auto_increment
    primary key,
  RID  int(4) not null,
  date date   not null,
  TID  int(4) not null,
  constraint attendant_sheet_ibfk_1
    foreign key (RID) references registration (RID)
      on update cascade
      on delete cascade,
  constraint attendant_sheet_ibfk_2
    foreign key (TID) references teacher (TID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index attendant_sheet_ibfk_1
  on attendant_sheet (RID);

create index attendant_sheet_ibfk_2
  on attendant_sheet (TID);

create table participation
(
  PID         int(4) auto_increment
    primary key,
  RID         int(4)                  not null,
  ELID        int(4)                  not null,
  result      varchar(50)             not null,
  performance varchar(50) default ' ' not null,
  constraint participation_ibfk_1
    foreign key (RID) references registration (RID)
      on update cascade
      on delete cascade,
  constraint participation_ibfk_2
    foreign key (ELID) references event_list (ELID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index participation_ibfk_1
  on participation (RID);

create index participation_ibfk_2
  on participation (ELID);

create table payment
(
  PAYID int(4) auto_increment
    primary key,
  RID   int                 not null,
  fee   decimal default '0' null,
  Month int                 null,
  Year  int                 null,
  constraint payment__RID_fk
    foreign key (RID) references registration (RID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index payment__RID_fk
  on payment (RID);

create index registration_ibfk_1
  on registration (SID);

create index registration_ibfk_2
  on registration (AID);

create table teacher_in_charge_list
(
  TINCID int(4) auto_increment
    primary key,
  CID    int(4) not null,
  TID    int(4) not null,
  constraint teacher_in_charge_list_ibfk_1
    foreign key (CID) references competition (CID)
      on update cascade
      on delete cascade,
  constraint teacher_in_charge_list_ibfk_2
    foreign key (TID) references teacher (TID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index teacher_in_charge_list_ibfk_1
  on teacher_in_charge_list (CID);

create index teacher_in_charge_list_ibfk_2
  on teacher_in_charge_list (TID);

create table telephone_no
(
  TELID  int(4) auto_increment
    primary key,
  tel_no char(10) not null,
  SID    int(4)   null,
  constraint telephone_no_ibfk_1
    foreign key (SID) references student (SID)
      on update cascade
      on delete cascade
)
  engine = InnoDB;

create index telephone_no_ibfk_1
  on telephone_no (SID);

