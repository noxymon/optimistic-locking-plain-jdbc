package id.web.noxymon.db;

import id.web.noxymon.db.models.TransactionCountModel;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
public class TransactionCountRepository {

    private final Connection connect;

    public TransactionCountRepository(Manager manager) throws SQLException {
        connect = manager.connect();
    }

    public int save(TransactionCountModel countModel) throws Exception {
        if (isNull(countModel)) {
            TransactionCountModel countModelBaru = new TransactionCountModel(1, 1, 0);
            final Integer i = insert(countModelBaru);
            return i;
        } else {
            final int i = increment(countModel);
            return i;
        }
    }

    public int increment(TransactionCountModel countModel) throws Exception {
        synchronized (this) {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "UPDATE tb_transaction_count SET " +
                            "usage=?, " +
                            "version=version +1 " +
                            "WHERE " +
                            "id=? " +
                            "and version=?;");
            preparedStatement.setInt(1, countModel.getUsage());
            preparedStatement.setInt(2, countModel.getId());
            preparedStatement.setInt(3, countModel.getVersion());

            final int countAfterUpdate = preparedStatement.executeUpdate();
            log.info(LocalDateTime.now() + " " + preparedStatement.toString() + " Affected Row=" + countAfterUpdate);
            if (countAfterUpdate < 1) {
                throw new Exception("Concurrent Update Occured !!");
            }
            return countAfterUpdate;
        }
    }

    public Integer insert(TransactionCountModel countModel) throws Exception {
        synchronized (this) {
            PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO public.tb_transaction_count (id,\"usage\") VALUES (?,?) ON CONFLICT(\"id\") DO NOTHING;");
            preparedStatement.setInt(1, countModel.getId());
            preparedStatement.setInt(2, countModel.getUsage());

            final int countAfterUpdate = preparedStatement.executeUpdate();
            log.info(LocalDateTime.now() + " " + preparedStatement.toString() + " Affected Row=" + countAfterUpdate);
            if (countAfterUpdate < 1) {
                throw new Exception("Concurrent Update Occured !!");
            }
            return countAfterUpdate;
        }
    }

    public int isExist(int id) throws SQLException {
        final PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM public.tb_transaction_count WHERE id = ? FOR UPDATE",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setInt(1, id);

        log.info(LocalDateTime.now() + " " + preparedStatement.toString());

        final ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("version");
        } else {
            return -1;
        }
    }

    public int update(int id, int usage) throws SQLException {
        final PreparedStatement preparedStatement = connect.prepareStatement("UPDATE public.tb_transaction_count SET \"usage\"=? WHERE id=?;");
        preparedStatement.setInt(1, usage);
        preparedStatement.setInt(2, id);

        final int countAfterUpdate = preparedStatement.executeUpdate();
        log.info(LocalDateTime.now() + " " + preparedStatement.toString() + " Affected Row=" + countAfterUpdate);
        return countAfterUpdate;
    }

    private int getCurrentVersion() throws SQLException {
        final PreparedStatement preparedStatement = connect.prepareStatement("select last_value from transcation_count_version_sequence;");

        log.info(LocalDateTime.now() + " " + preparedStatement.toString());

        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 1;
    }

    private int getNextVersion() throws Exception {
        final PreparedStatement preparedStatement = connect.prepareStatement("select nextval('transcation_count_version_sequence');");


        final ResultSet resultSet = preparedStatement.executeQuery();
        log.info(LocalDateTime.now() + " " + preparedStatement.toString() + " Affected row=" + resultSet);
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new Exception("Not exist !!");
    }

    public TransactionCountModel findById(int id) throws SQLException {
        final PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM public.tb_transaction_count WHERE id = ?",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setInt(1, id);

        log.info(LocalDateTime.now() + " " + preparedStatement.toString());

        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return new TransactionCountModel(resultSet.getInt("id"),
                    resultSet.getInt("usage"),
                    resultSet.getInt("version")
            );
        }
        return null;
    }
}
