package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import codescreen.tipa.bank.model.LoadResponse;
import codescreen.tipa.bank.model.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LoadResponseJdbcTemplateRepositoryTest {
    @Autowired
    LoadResponseJdbcTemplateRepository repository;

    @Test
    void shouldAddToHistory(){
        LoadResponse response = new LoadResponse();

        Amount amount = new Amount();
        amount.setAmount("400");

        response.setBalance(amount);
        response.setUserId("1");
        response.setMessageId("3");

        LoadResponse actual = repository.addToHistory(response);

        assertNotNull(actual);

        assertEquals(response.getMessageId(), actual.getMessageId());
        assertEquals(response.getUserId(),actual.getUserId());
        assertEquals(response.getBalance(), actual.getBalance());
    }
}
