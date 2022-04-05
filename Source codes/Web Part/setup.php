<?php
require 'cresidentials1235.php';

$conne->query("create table manager(connection_id varchar(255) not null PRIMARY KEY,other text,power int default 0,username text,direction text)");

$conne->query("alter table manager add column client text");

$conne->query("create table flag(num bigint not null auto_increment primary key,connection_id varchar(800) not null,direction text not null,file text,downloaded int default 0)");

$conne->query("ALTER TABLE `flag` CHANGE `file` `file` TEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL");


echo $conne->error;
?>