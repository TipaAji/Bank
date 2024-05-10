package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.AuthorizationRequest;

public interface AuthorizationRequestRepository{
    //For requests, we want to save every request that was made to our transaction history table
    //We also then take this request and use a get by userId method in our Account table then update their account
    //balance with an update method
    /*AuthorizationRequest addToHistory(AuthorizationRequest authorizationRequest);*/

    boolean updateBalance(AuthorizationRequest authorizationRequest);
}
