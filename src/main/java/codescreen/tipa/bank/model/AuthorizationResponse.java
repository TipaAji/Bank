package codescreen.tipa.bank.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResponse {

    private String userId;

    private String messageId;

    private ResponseCode responseCode;

    private Amount balance;
}
