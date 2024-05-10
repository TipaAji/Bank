package codescreen.tipa.bank.repositories;


import codescreen.tipa.bank.model.Amount;

public interface AmountRepository{
    Amount getBalanceByUser(String userId);
}
