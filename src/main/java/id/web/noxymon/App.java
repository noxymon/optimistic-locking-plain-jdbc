package id.web.noxymon;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static void main(String[] args) throws SQLException {
        for (int i = 1; i < 300; i++) {
            try {
                Thread t = new Thread(new MyRun());
                log.info(LocalDateTime.now() + " Start thread " + i);
                t.start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
