package codescreen.tipa.bank.repositories;


import codescreen.tipa.bank.model.LoadRequest;

public interface LoadRequestRepository{
    boolean updateBalance(LoadRequest request);
}
