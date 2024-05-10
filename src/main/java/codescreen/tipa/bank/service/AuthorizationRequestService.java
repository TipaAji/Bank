package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.*;
import codescreen.tipa.bank.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
public class AuthorizationRequestService {
    private final AuthorizationRequestRepository authorizationRequestRepository;
    private final AmountRepository amountRepository;
    private final AuthorizationResponseRepository authorizationResponseRepository;

    public AuthorizationRequestService(AuthorizationRequestRepository authorizationRequestRepository,
                                       AmountRepository amountRepository,
                                       AuthorizationResponseRepository authorizationResponseRepository) {
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.amountRepository = amountRepository;
        this.authorizationResponseRepository = authorizationResponseRepository;
    }

    @Transactional
    public Result<AuthorizationResponse> authorizationRequest(AuthorizationRequest request){
        Amount amount = amountRepository.getBalanceByUser(request.getUserId());
        Result<AuthorizationResponse> result = validate(request);

        if(!result.isSuccess()){
            return result;
        }
        AuthorizationResponse response = new AuthorizationResponse();
        response.setUserId(request.getUserId());
        response.setMessageId(request.getMessageId());
        response.setBalance(request.getTransactionAmount());
        response.setResponseCode(ResponseCode.DECLINED);

        if(amount == null){
            result.setError("User not found", "404", ResponseCode.DECLINED);
            authorizationResponseRepository.addToHistory(response);
            return result;
        }

        BigDecimal balance = new BigDecimal(amount.getAmount());
        BigDecimal requestAmount = new BigDecimal(request.getTransactionAmount().getAmount());

        if(balance.compareTo(requestAmount) < 0){
            result.setError("You do not have enough to withdraw", "400", ResponseCode.DECLINED);
            response.getBalance().setAmount(balance.toString());
            authorizationResponseRepository.addToHistory(response);
            return result;
        }
        String newBalance = balance.subtract(requestAmount).toString();
        response.getBalance().setAmount(newBalance);
        request.setTransactionAmount(response.getBalance());

        if(authorizationRequestRepository.updateBalance(request)){
            response.setResponseCode(ResponseCode.APPROVED);
        }
        response = authorizationResponseRepository.addToHistory(response);
        result.setPayload(response);
        return result;
    }

    private Result<AuthorizationResponse> validate(AuthorizationRequest request){
        Result<AuthorizationResponse> result = new Result<>();
        if(request.getTransactionAmount().getAmount() == null||request.getTransactionAmount().getAmount().isBlank()){
            result.setError("Amount is missing", "400", ResponseCode.DECLINED);
            return result;
        }
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(!pattern.matcher(request.getTransactionAmount().getAmount()).matches()){
            result.setError("Amount is not numeric", "400", ResponseCode.DECLINED);
            return result;
        }
        if(request.getUserId() == null || request.getUserId().isBlank()){
            result.setError("User ID not entered", "400",ResponseCode.DECLINED);
            return result;
        }
        if(!request.getTransactionAmount().getDebitOrCredit().toString().equals("DEBIT")){
            result.setError("You can only withdraw with debit", "400", ResponseCode.DECLINED);
        }
        if(request.getTransactionAmount().getAmount().startsWith("-")){
            result.setError("Amount cannot be negative", "400",ResponseCode.DECLINED);
        }
        return result;
    }
}
