package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AmountJdbcTemplateRepositoryTest {
    @Autowired
    AmountJdbcTemplateRepository repository;

    @Test
    void shouldFindAmount(){
        Amount amount = repository.getBalanceByUser("5");
        assertNotNull(amount);
        assertNotEquals(amount.getAmount(), "");
        assertEquals(amount.getAmount(), "90900");
    }

    @Test
    void shouldNotFindAmount(){
        Amount amount = repository.getBalanceByUser("100");

        assertNull(amount);
    }
}
