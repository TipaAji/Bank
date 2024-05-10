package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.*;
import codescreen.tipa.bank.repositories.AmountRepository;
import codescreen.tipa.bank.repositories.AuthorizationRequestRepository;
import codescreen.tipa.bank.repositories.AuthorizationResponseRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AuthorizationRequestServiceTest {
    @Autowired
    AuthorizationRequestService service;

    @Autowired
    AmountRepository amountRepository;

    @Autowired
    AuthorizationRequestRepository authorizationRequestRepository;

    @Autowired
    AuthorizationResponseRepository authorizationResponseRepository;

    @Test
    void shouldAddRequest(){
        AuthorizationRequest request = makeRequest();
        Amount amount = makeAmount();
        amount.setAmount("600");

        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }
    @Test
    void shouldNotAddNullUserId(){
        AuthorizationRequest request = makeRequest();
        request.setUserId(null);
        Amount amount = makeAmount();
        amount.setAmount("600");


        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User ID not entered", result.getError().getMessage());
    }
    @Test
    void shouldNotAddBlankUserId(){
        AuthorizationRequest request = makeRequest();
        request.setUserId("");


        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User ID not entered", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNonNumericAmount(){
        AuthorizationRequest request = makeRequest();
        request.getTransactionAmount().setAmount("Hey");


        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is not numeric", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNullAmount(){
        AuthorizationRequest request = makeRequest();
        request.getTransactionAmount().setAmount(null);



        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is missing", result.getError().getMessage());
    }
    @Test
    void shouldNotAddBlankAmount(){
        AuthorizationRequest request = makeRequest();
        request.getTransactionAmount().setAmount("");


        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is missing", result.getError().getMessage());
    }
    @Test
    void shouldNotAddWhenAmountIsGreaterThanBalance(){
        AuthorizationRequest request = makeRequest();
        request.getTransactionAmount().setAmount("900000");

        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("You do not have enough to withdraw", result.getError().getMessage());
    }
    @Test
    void shouldNotAddWhenUserDoesNotExist(){
        AuthorizationRequest request = makeRequest();
        request.setUserId("100");
        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User not found", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNegativeAmount(){
        AuthorizationRequest request = makeRequest();
        request.getTransactionAmount().setAmount("-100");

        Result<AuthorizationResponse> result = service.authorizationRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount cannot be negative", result.getError().getMessage());
    }
    AuthorizationRequest makeRequest(){
        AuthorizationRequest request = new AuthorizationRequest();
        Amount amount = makeAmount();
        request.setUserId("4");
        request.setTransactionAmount(amount);

        return request;
    }

    Amount makeAmount(){
        Amount amount = new Amount();
        amount.setAmount("100");
        amount.setDebitOrCredit(DebitOrCredit.DEBIT);
        amount.setCurrency("USD");

        return amount;
    }

}
