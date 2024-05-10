package codescreen.tipa.bank.controllers;

import codescreen.tipa.bank.model.AuthorizationRequest;
import codescreen.tipa.bank.model.AuthorizationResponse;
import codescreen.tipa.bank.model.LoadRequest;
import codescreen.tipa.bank.model.LoadResponse;
import codescreen.tipa.bank.service.LoadRequestService;
import codescreen.tipa.bank.service.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadController {
    private final LoadRequestService service;

    public LoadController(LoadRequestService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<Object> load(@RequestBody LoadRequest request){

        Result<LoadResponse> result = service.loadRequest(request);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(result.getError(), HttpStatus.BAD_REQUEST);
    }
}
