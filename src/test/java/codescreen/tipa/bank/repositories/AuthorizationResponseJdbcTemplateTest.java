package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import codescreen.tipa.bank.model.AuthorizationResponse;
import codescreen.tipa.bank.model.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthorizationResponseJdbcTemplateTest {
    @Autowired
    AuthorizationResponseJdbcTemplateRepository repository;

    @Test
    void shouldAddToHistory(){
        AuthorizationResponse response = new AuthorizationResponse();

        Amount amount = new Amount();
        amount.setAmount("400");

        response.setBalance(amount);
        response.setUserId("1");
        response.setMessageId("3");
        response.setResponseCode(ResponseCode.APPROVED);

        AuthorizationResponse actual = repository.addToHistory(response);

        assertNotNull(actual);

        assertEquals(response.getResponseCode(), actual.getResponseCode());
        assertEquals(response.getMessageId(), actual.getMessageId());
        assertEquals(response.getUserId(),actual.getUserId());
        assertEquals(response.getBalance(), actual.getBalance());
    }
}
