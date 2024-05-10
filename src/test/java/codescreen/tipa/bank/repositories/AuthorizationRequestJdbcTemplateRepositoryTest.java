package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import codescreen.tipa.bank.model.AuthorizationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AuthorizationRequestJdbcTemplateRepositoryTest {
    @Autowired
    AuthorizationRequestJdbcTemplateRepository repository;

    @Autowired
    AmountJdbcTemplateRepository amountJdbcTemplateRepository;

    @Test
    void shouldUpdateBalance(){
        AuthorizationRequest request = new AuthorizationRequest();
        Amount amount = new Amount();
        amount.setAmount("110");
        request.setUserId("1");
        request.setTransactionAmount(amount);

        assertTrue(repository.updateBalance(request));

        assertEquals(amountJdbcTemplateRepository.getBalanceByUser("1").getAmount(),"110");
    }

    @Test
    void shouldNotUpdate(){
        AuthorizationRequest request = new AuthorizationRequest();
        Amount amount = new Amount();
        amount.setAmount("110");
        request.setUserId("100");
        request.setTransactionAmount(amount);

        assertFalse(repository.updateBalance(request));
    }
}
