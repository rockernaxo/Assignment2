drop database if exists testDB;
create database testDB;
SET SQL_SAFE_UPDATES = 0;
use testDB;
drop table if exists learn1D, test1D, test2D, learn2D;
CREATE TABLE learn1D SELECT * FROM subtables.measurements;
delete from learn1D where name not like "%ANG";
delete from learn1D where name not like "AMHE%";
CREATE TABLE test1D SELECT * FROM learn1D;
delete from test1D order by time asc limit 75;
CREATE TABLE learn2D SELECT * FROM subtables.measurements;
delete from learn2D where name not like "AMHE%";
create table test2D select * from learn2D;
delete from test2D order by time asc limit 75;


