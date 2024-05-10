package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.AuthorizationRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorizationRequestJdbcTemplateRepository implements AuthorizationRequestRepository{
    JdbcTemplate jdbcTemplate;

    public AuthorizationRequestJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean updateBalance(AuthorizationRequest authorizationRequest) {
        final String sql = "update Account set " +
                "currentBalance = ? " +
                "where userId = ?;";

        return jdbcTemplate.update(sql, authorizationRequest.getTransactionAmount().getAmount(), authorizationRequest.getUserId()) > 0;
    }
}
