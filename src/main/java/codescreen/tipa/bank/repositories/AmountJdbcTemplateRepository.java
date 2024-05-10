package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.Amount;
import codescreen.tipa.bank.repositories.mappers.AmountMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AmountJdbcTemplateRepository implements AmountRepository{
    JdbcTemplate jdbcTemplate;

    public AmountJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Amount getBalanceByUser(String userId) {
        final String sql = "select currentBalance " +
                "from Account " +
                "where userId = ?;";
        return jdbcTemplate.query(sql, new AmountMapper(), userId).stream().findFirst().orElse(null);
    }
}
