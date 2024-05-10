package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Ping;

public interface PingRepository {
    Ping getServerTime();
}
