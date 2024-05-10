package codescreen.tipa.bank.controllers;

import codescreen.tipa.bank.model.Ping;
import codescreen.tipa.bank.service.PingService;
import codescreen.tipa.bank.service.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
    private final PingService service;

    public PingController(PingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getPing(){
        Result<Ping> result = service.getPing();

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getError(), HttpStatus.BAD_REQUEST);
    }
}
