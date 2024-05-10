package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.*;
import codescreen.tipa.bank.repositories.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LoadRequestServiceTest {
    @Autowired
    LoadRequestService service;

    @Autowired
    AmountRepository amountRepository;

    @Autowired
    LoadRequestRepository LoadRequestRepository;

    @Autowired
    LoadResponseRepository LoadResponseRepository;

    @Test
    void shouldAddRequest(){
        LoadRequest request = makeRequest();
        Amount amount = makeAmount();
        amount.setAmount("600");

        Result<LoadResponse> result = service.loadRequest(request);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }
    @Test
    void shouldNotAddNullUserId(){
        LoadRequest request = makeRequest();
        request.setUserId(null);
        Amount amount = makeAmount();
        amount.setAmount("600");


        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User ID not entered", result.getError().getMessage());
    }
    @Test
    void shouldNotAddBlankUserId(){
        LoadRequest request = makeRequest();
        request.setUserId("");


        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User ID not entered", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNonNumericAmount(){
        LoadRequest request = makeRequest();
        request.getTransactionAmount().setAmount("Hey");


        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is not numeric", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNullAmount(){
        LoadRequest request = makeRequest();
        request.getTransactionAmount().setAmount(null);



        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is missing", result.getError().getMessage());
    }
    @Test
    void shouldNotAddBlankAmount(){
        LoadRequest request = makeRequest();
        request.getTransactionAmount().setAmount("");


        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount is missing", result.getError().getMessage());
    }

    @Test
    void shouldNotAddWhenUserDoesNotExist(){
        LoadRequest request = makeRequest();
        request.setUserId("100");
        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("User not found", result.getError().getMessage());
    }
    @Test
    void shouldNotAddNegativeAmount(){
        LoadRequest request = makeRequest();
        request.getTransactionAmount().setAmount("-100");

        Result<LoadResponse> result = service.loadRequest(request);

        assertFalse(result.isSuccess());
        assertEquals("Amount cannot be negative", result.getError().getMessage());
    }
    LoadRequest makeRequest(){
        LoadRequest request = new LoadRequest();
        Amount amount = makeAmount();
        request.setUserId("6");
        request.setTransactionAmount(amount);

        return request;
    }

    Amount makeAmount(){
        Amount amount = new Amount();
        amount.setAmount("100");
        amount.setDebitOrCredit(DebitOrCredit.CREDIT);
        amount.setCurrency("USD");

        return amount;
    }
}
