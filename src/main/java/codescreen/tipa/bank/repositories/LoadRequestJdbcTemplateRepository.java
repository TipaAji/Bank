package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.LoadRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoadRequestJdbcTemplateRepository implements LoadRequestRepository{
    JdbcTemplate jdbcTemplate;

    public LoadRequestJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean updateBalance(LoadRequest request) {
        final String sql = "update Account set " +
                "currentBalance = ? " +
                "where userId = ?;";

        return jdbcTemplate.update(sql, request.getTransactionAmount().getAmount(), request.getUserId()) > 0;
    }
}
