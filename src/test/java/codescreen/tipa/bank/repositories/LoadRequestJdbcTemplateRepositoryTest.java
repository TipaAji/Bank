package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import codescreen.tipa.bank.model.LoadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoadRequestJdbcTemplateRepositoryTest {
    @Autowired
    LoadRequestJdbcTemplateRepository repository;

    @Autowired
    AmountJdbcTemplateRepository amountJdbcTemplateRepository;

    @Test
    void shouldUpdateBalance(){
        LoadRequest request = new LoadRequest();
        Amount amount = new Amount();
        amount.setAmount("910");
        request.setUserId("2");
        request.setTransactionAmount(amount);

        assertTrue(repository.updateBalance(request));

        assertEquals(amountJdbcTemplateRepository.getBalanceByUser("2").getAmount(),"910");
    }

    @Test
    void shouldNotUpdate(){
        LoadRequest request = new LoadRequest();
        Amount amount = new Amount();
        amount.setAmount("110");
        request.setUserId("100");
        request.setTransactionAmount(amount);

        assertFalse(repository.updateBalance(request));
    }
}
