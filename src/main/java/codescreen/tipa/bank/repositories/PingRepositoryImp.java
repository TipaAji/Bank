package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Ping;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
@Repository
public class PingRepositoryImp implements PingRepository{
    @Override
    public Ping getServerTime() {
        Ping ping = new Ping();
        String serverTime = LocalDateTime.now().toString();
        ping.setServerTime(serverTime);

        return ping;
    }
}
