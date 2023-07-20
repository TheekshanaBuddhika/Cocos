Drop DATABASE Cocos;
CREATE DATABASE Cocos;
USE Cocos;

CREATE TABLE `Position`(
                          Pos_Id varchar(5) PRIMARY KEY,
                          Position_Name varchar(20) NOT NULL,
                          Basic_Salary DECIMAL(10,2) NOT NULL
);

CREATE TABLE employee(
                         Emp_id varchar(6) NOT NULL,
                         First_Name varchar(20) NOT NULL,
                         Last_Name varchar(20) NOT NULL,
                         address varchar(50) NOT NULL,
                         Contact_No int(20) NOT NULL,
                         Date_Of_Birth DATE NOT NULL,
                         Joined_date DATE NOT NULL,
                         Pos_Id varchar(5) NOT NULL,
                         PRIMARY KEY(Emp_id),
                         FOREIGN KEY (Pos_Id) REFERENCES `Position`(Pos_Id)
);


CREATE TABLE user(
                     UserName varchar(10)  PRIMARY KEY,
                     Password varchar(8) NOT NULL,
                     Email varchar(50) ,
                     Emp_id varchar(6) ,
                     FOREIGN KEY (Emp_id) REFERENCES employee(Emp_id)
);

CREATE TABLE loghistory(
                           UserName varchar(10) NOT NULL,
                           Date DATE NOT NULL,
                           Time_In TIME ,
                           Time_Out TIME ,
                           FOREIGN KEY (UserName) REFERENCES user(UserName) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE salary(
                       Pay_id varchar(6) NOT NULL,
                       Emp_id varchar(6) NOT NULL,
                       OT_PAY DECIMAL(10,2),
                       Attendance_Pay DECIMAL(10,2),
                       ETF DECIMAL(10,2),
                       EPF DECIMAL(10,2),
                       Total_salary DECIMAL(20,2) NOT NULL,
                       PRIMARY KEY(Pay_id),
                       FOREIGN KEY (Emp_id) REFERENCES employee(Emp_id)
);

CREATE TABLE attendance(
                           Date DATE NOT NULL,
                           Emp_id varchar(6) NOT NULL,
                           Time_In DATETIME NOT NULL,
                           Time_Out DATETIME NOT NULL,
                           Work_Hours  DOUBLE(10,2),
                           FOREIGN KEY (Emp_id) REFERENCES employee(Emp_id)
 );

CREATE TABLE fund(
                     Emp_id varchar(6) NOT NULL,
                     ETF  DOUBLE(10,2),
                     EPF DOUBLE(10,2),
                     Month varchar(20) NOT NULL,
                     Year varchar(4) NOT NULL,
                     FOREIGN KEY (Emp_id) REFERENCES employee(Emp_id)
 );

CREATE TABLE supplier(
                         Sup_id varchar(6) NOT NULL,
                         Name varchar(20) NOT NULL,
                         address varchar(50) NOT NULL,
                         Joined_date DATE NOT NULL,
                         Time_period int(4) NOT NULL,
                         Entrant varchar(10) NOT NULL,
                         PRIMARY KEY(Sup_id),
                         FOREIGN KEY (Entrant) REFERENCES user(UserName)
);

CREATE TABLE buyer(
                      Buy_id varchar(6) NOT NULL,
                      Name varchar(20) NOT NULL,
                      address varchar(50) NOT NULL,
                      Joined_date DATE NOT NULL,
                      Time_period int(4) NOT NULL,
                      Entrant varchar(10) NOT NULL,
                      PRIMARY KEY(Buy_id),
                      FOREIGN KEY (Entrant) REFERENCES user(UserName)
);



CREATE TABLE shipment(
                         Ship_id varchar(6) PRIMARY KEY,
                         DATE date not null ,
                         Time_in time not null ,
                         change DOUBLE(10,2) NOT NULL,
                         Total DOUBLE(10,2) NOT NULL,
                         Payment DECIMAL(10,2) NOT NULL,

);

CREATE TABLE item(
                     Item_id varchar(6) NOT NULL,
                     Name varchar(20) NOT NULL,
                     quantity DOUBLE(10,3) NOT NULL,
                     Unit_Price DOUBLE (20,2)NOT NULL,
                     PRIMARY KEY(Item_id)
 );

CREATE TABLE supplydetail(
                             Sup_id varchar(6) NOT NULL,
                             Item_id varchar(6) NOT NULL,
                             Date DATE NOT NULL,
                             quantity DOUBLE(10,3) NOT NULL,
                             Total DOUBLE (20,2)NOT NULL,
                             FOREIGN KEY (Sup_id) REFERENCES supplier(Sup_id),
                             FOREIGN KEY (Item_id) REFERENCES item(Item_id)
 );

CREATE TABLE buyerdetail(
                            Buy_id varchar(6) NOT NULL,
                            Item_id varchar(6) NOT NULL,
                            Date DATE NOT NULL,
                            quantity DOUBLE(10,3) NOT NULL,
                            Total DOUBLE (20,2)NOT NULL,
                            FOREIGN KEY (Buy_id) REFERENCES buyer(Buy_id),
                            FOREIGN KEY (Item_id) REFERENCES item(Item_id)
 );



CREATE TABLE product(
                        Item_id varchar(6) NOT NULL,
                        Date DATE NOT NULL,
                        quantity DOUBLE(10,3) NOT NULL,
                        FOREIGN KEY (Item_id) REFERENCES item(Item_id)
 );
