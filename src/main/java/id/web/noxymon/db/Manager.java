package id.web.noxymon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Manager {
    private static Manager instance;
    private final String hosts = "jdbc:postgresql://172.17.0.2:5432/postgres";
    private final String username = "postgres";
    private final String password = "12345678";
    private Connection connection;

    private Manager() throws SQLException {
        connection = DriverManager.getConnection(hosts, username, password);
    }

    public synchronized static Manager getInstance() throws SQLException {
        if (Objects.isNull(instance)) {
            instance = new Manager();
        }
        return instance;
    }

    public Connection connect() {
        return this.connection;
    }

    public void setCommit(boolean commit) throws SQLException {
        this.connection.setAutoCommit(commit);
    }

    public void commit(boolean c) throws SQLException {
        if (c) {
            this.connection.commit();
        } else {
            this.connection.rollback();
        }
    }

    public void setTranscationIsolationLevel(int value) throws SQLException {
        this.connection.setTransactionIsolation(value);
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
