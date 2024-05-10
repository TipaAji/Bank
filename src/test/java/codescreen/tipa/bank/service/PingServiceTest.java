package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.Ping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PingServiceTest {
    @Autowired
    PingService service;

    @Test
    void checkPingReceived(){
        Result<Ping> result = service.getPing();
        assertNotNull(result.getPayload());
        assertTrue(result.isSuccess());
    }
}
