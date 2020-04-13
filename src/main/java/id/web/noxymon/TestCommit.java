package id.web.noxymon;

import id.web.noxymon.db.Manager;
import id.web.noxymon.db.TransactionDetailRepository;

import java.sql.SQLException;

public class TestCommit {
    final Manager manager = Manager.getInstance();
    final TransactionDetailRepository detailRepository = new TransactionDetailRepository(manager);

    public TestCommit() throws SQLException {
    }

    public void testFailed() throws SQLException {
        manager.setCommit(false);
        detailRepository.insertData("failed !!");
        manager.commit(false);
    }

    public void testSucceed() throws SQLException {
        manager.setCommit(false);
        detailRepository.insertData("Success !!");
        manager.commit(true);
    }
}
