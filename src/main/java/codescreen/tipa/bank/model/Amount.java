package codescreen.tipa.bank.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Amount {

    private String amount;

    private String currency;

    private DebitOrCredit debitOrCredit;
}
