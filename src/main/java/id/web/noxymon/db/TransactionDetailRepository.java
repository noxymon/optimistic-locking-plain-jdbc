package id.web.noxymon.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class TransactionDetailRepository {

    private final Connection connect;

    public TransactionDetailRepository(Manager manager) {
        connect = manager.connect();
    }

    public int insertData(String content) throws SQLException {
        final PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO public.tb_transaction_detail (content) VALUES (?);");
        preparedStatement.setString(1, content);

        log.info(preparedStatement.toString());
        final int i = preparedStatement.executeUpdate();
        return i;
    }
}
