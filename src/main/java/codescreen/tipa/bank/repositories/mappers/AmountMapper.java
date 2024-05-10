package codescreen.tipa.bank.repositories.mappers;

import codescreen.tipa.bank.model.Amount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AmountMapper implements RowMapper<Amount> {
    @Override
    public Amount mapRow(ResultSet rs, int rowNum) throws SQLException {
        Amount amount = new Amount();

        amount.setAmount(rs.getString("currentBalance"));

        return amount;
    }
}
