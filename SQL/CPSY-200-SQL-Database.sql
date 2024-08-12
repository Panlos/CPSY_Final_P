spool C:\cprg250s\Part-C\CPSY-200-SQL-Database_spool.txt

DROP TABLE CUSTOMER_INFORMATION;
DROP TABLE CATEGORY_LIST;
DROP TABLE RENTAL_EQUIPMENT;
DROP TABLE REMOVED_ITEMS;
DROP TABLE RENTAL_INFORMATION;

CREATE TABLE Customer_Information
(
    customerID NUMBER PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    contactPhone VARCHAR(12) NOT NULL,
    email VARCHAR(100) NOT NULL,
    canRent VARCHAR(3) NOT NULL,
    CONSTRAINT Customer_Information_canRent_ck CHECK (canRent IN ('Yes', 'No'))
);

DESCRIBE Customer_Information;

CREATE TABLE Category_List
(
    categoryID NUMBER PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

DESCRIBE Category_List

CREATE TABLE Rental_Equipment
(
    equipmentID NUMBER PRIMARY KEY,
    categoryID NUMBER NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    dailyRate NUMBER(8,2) NOT NULL,
    CONSTRAINT Rental_Equipment_categoryID_fk FOREIGN KEY (categoryID) REFERENCES Category_List(categoryID)
);

DESCRIBE Rental_Equipment

CREATE TABLE Removed_Items
(
    equipmentID NUMBER PRIMARY KEY,
    removedStatus VARCHAR(20) 
    CHECK (removedStatus IN ('ToBeSold', 'ToBeScrapped')),
    CONSTRAINT Removed_Items_equipmentID_fk FOREIGN KEY (equipmentID) REFERENCES Rental_Equipment(equipmentID)
);

DESCRIBE Removed_Items

CREATE TABLE Rental_Information
(
    rentalID NUMBER PRIMARY KEY,
    currentDate DATE DEFAULT SYSDATE NOT NULL,
    customerID NUMBER NOT NULL,
    equipmentID NUMBER NOT NULL,
    rentalDate DATE DEFAULT SYSDATE NOT NULL,
    returnDate DATE DEFAULT SYSDATE NOT NULL,
    cost NUMBER (9,2) NOT NULL,
    CONSTRAINT Rental_Information_customerID_fk FOREIGN KEY (customerID) REFERENCES Customer_Information(customerID),
    CONSTRAINT Rental_Information_equipmentID_fk FOREIGN KEY (equipmentID) REFERENCES Rental_Equipment(equipmentID)
);

DESCRIBE Rental_Information

COMMIT;
spool off