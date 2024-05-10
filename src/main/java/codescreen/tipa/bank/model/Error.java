package codescreen.tipa.bank.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Error {

    @Min(value = 1, message = "Message must be at least length 1")
    private String message;

    @Min(value = 1, message = "Code must be at least length 1")
    private String code;
}
