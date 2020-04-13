package id.web.noxymon;

import id.web.noxymon.db.Manager;
import id.web.noxymon.db.TransactionCountRepository;
import id.web.noxymon.db.TransactionDetailRepository;
import id.web.noxymon.db.models.TransactionCountModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class MyRun implements Runnable {
    final Manager manager = Manager.getInstance();
    final TransactionDetailRepository detailRepository = new TransactionDetailRepository(manager);
    final TransactionCountRepository countRepository = new TransactionCountRepository(manager);

    public MyRun() throws SQLException {
    }

    @Override
    public void run() {
        book();
    }

    private synchronized void book() {
        book(0);
    }

    @SneakyThrows
    private synchronized void book(int i) {
        log.info("booking ke-" + i);
        try {
            manager.setCommit(false);
            TransactionCountModel countModel = countRepository.findById(1);
            if (isNull(countModel) || (countModel.getUsage() < 100)) {
                if (nonNull(countModel)) {
                    TransactionCountModel countAfterIncr = new TransactionCountModel(countModel.getId(), new AtomicInteger(countModel.getUsage()).incrementAndGet(), countModel.getVersion());
                    countRepository.save(countAfterIncr);
                } else {
                    countRepository.save(null);
                }
                detailRepository.insertData("yak !!!!");
                manager.commit(true);
            } else {
                log.info("Exceed Limit !!!");
                manager.commit(false);
            }
        } catch (Exception e) {
            log.info("Concurrent update : " + e.getMessage());
            manager.commit(false);
            if (i < 3) {
                Thread.sleep(1000);
                final int c = i + 1;
                book(c);
            } else {
                log.info("Failed After 3 times... surrender");
            }
        }
    }
}
