package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.AuthorizationResponse;

public interface AuthorizationResponseRepository{
    //WE MIGHT NOT NEED THIS In our response we want to save the user's current balance in our Account table
    AuthorizationResponse addToHistory(AuthorizationResponse response);
}
