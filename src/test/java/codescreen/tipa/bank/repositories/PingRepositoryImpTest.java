package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Ping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PingRepositoryImpTest {
    @Autowired
    PingRepositoryImp repository;

    @Test
    void checkNotNullOrBlank(){
        Ping ping = repository.getServerTime();
        assertNotNull(ping);
        assertNotNull(ping.getServerTime());
        assertNotEquals(ping.getServerTime(), "");
    }

}
