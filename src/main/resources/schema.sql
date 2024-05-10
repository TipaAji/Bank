create table Account(
    userId VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY,
    currentBalance VARCHAR(100) NOT NULL
);
create table TransactionHistory(
    transactionId INT auto_increment UNIQUE NOT NULL PRIMARY KEY,
    userId VARCHAR(100) NOT NULL,
    type ENUM('AUTHORIZATION', 'LOAD') NOT NULL,
    messageId VARCHAR(100),
    status ENUM('APPROVED', 'DECLINED'),
    balance VARCHAR(100) NOT NULL
);