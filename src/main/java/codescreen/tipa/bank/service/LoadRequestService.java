package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.*;
import codescreen.tipa.bank.repositories.AmountRepository;
import codescreen.tipa.bank.repositories.LoadRequestRepository;
import codescreen.tipa.bank.repositories.LoadResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
public class LoadRequestService {
    private final LoadRequestRepository loadRequestRepository;
    private final LoadResponseRepository loadResponseRepository;
    private final AmountRepository amountRepository;

    public LoadRequestService(LoadRequestRepository loadRequestRepository,
                              LoadResponseRepository loadResponseRepository,
                              AmountRepository amountRepository) {
        this.loadRequestRepository = loadRequestRepository;
        this.loadResponseRepository = loadResponseRepository;
        this.amountRepository = amountRepository;
    }

    @Transactional
    public Result<LoadResponse> loadRequest(LoadRequest request){
        Amount amount = amountRepository.getBalanceByUser(request.getUserId());
        Result<LoadResponse> result = validate(request);
        if(!result.isSuccess()){
            return result;
        }

        LoadResponse response = new LoadResponse();
        response.setUserId(request.getUserId());
        response.setMessageId(request.getMessageId());
        response.setBalance(request.getTransactionAmount());


        if(amount == null){
            result.setError("User not found", "404",ResponseCode.DECLINED);
            loadResponseRepository.addToHistory(response);
            return result;
        }

        BigDecimal balance = new BigDecimal(amount.getAmount());
        BigDecimal requestAmount = new BigDecimal(request.getTransactionAmount().getAmount());

        String newBalance = balance.add(requestAmount).toString();
        response.getBalance().setAmount(newBalance);
        request.setTransactionAmount(response.getBalance());

        loadRequestRepository.updateBalance(request);

        response = loadResponseRepository.addToHistory(response);
        result.setPayload(response);
        return result;
    }

    private Result<LoadResponse> validate(LoadRequest request){
        Result<LoadResponse> result = new Result<>();
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
        if(!request.getTransactionAmount().getDebitOrCredit().toString().equals("CREDIT")){
            result.setError("You can only withdraw with credit", "400", ResponseCode.DECLINED);
        }
        if(request.getTransactionAmount().getAmount().startsWith("-")){
            result.setError("Amount cannot be negative", "400",ResponseCode.DECLINED);
        }

        return result;
    }
}
