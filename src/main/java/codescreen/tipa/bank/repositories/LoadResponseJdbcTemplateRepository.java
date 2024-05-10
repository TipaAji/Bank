package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.LoadResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class LoadResponseJdbcTemplateRepository implements LoadResponseRepository{
    JdbcTemplate jdbcTemplate;

    public LoadResponseJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public LoadResponse addToHistory(LoadResponse response) {
        final String sql = "insert into TransactionHistory(userId, type, messageId, status, balance) " +
                "values(?, ?, ?, ?, ?);";
        //KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, response.getUserId());
            preparedStatement.setString(2, "LOAD");
            preparedStatement.setString(3, response.getMessageId());
            preparedStatement.setString(4, "APPROVED");
            preparedStatement.setString(5, response.getBalance().getAmount());

            return preparedStatement;
        });

        if(rowsAffected <= 0){
            return null;
        }
        //response.setAuthorizationResponseId(keyHolder.getKey().intValue());

        return response;
    }
}
