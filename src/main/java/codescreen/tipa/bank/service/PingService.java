package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.Ping;
import codescreen.tipa.bank.repositories.PingRepository;
import org.springframework.stereotype.Service;

@Service
public class PingService {
   private final PingRepository repository;

    public PingService(PingRepository repository) {
        this.repository = repository;
    }

    public Result<Ping> getPing(){
        Result<Ping> result = new Result<>();

        result.setPayload(repository.getServerTime());

        return result;
    }
}
